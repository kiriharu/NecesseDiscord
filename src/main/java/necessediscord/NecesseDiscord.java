package necessediscord;

import necesse.engine.GameEventListener;
import necesse.engine.GameEvents;
import necesse.engine.events.ServerStartEvent;
import necesse.engine.modLoader.ModSettings;
import necesse.engine.modLoader.annotations.ModEntry;
import necesse.engine.network.server.Server;
import necesse.engine.save.LoadData;
import necesse.engine.save.SaveData;
import necessediscord.events.PacketChatMessageEvent;
import necessediscord.listeners.DiscordChatListener;
import necessediscord.listeners.GameChatListener;
import necessediscord.wrappers.PacketMessageWrapper;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.requests.GatewayIntent;
import utils.DiscordUtils;

import java.util.EnumSet;

@ModEntry
public class NecesseDiscord {
    private Server server;
    private JDA jda;
    private TextChannel channel;
    private GameChatListener gameChatListener;
    private String discordToken = "Enter discord token here";
    private String channelId = "Enter channel id here";

    public ModSettings initSettings() {
        return new ModSettings() {
            @Override
            public void addSaveData(SaveData saveData) {
                saveData.addSafeString("token", discordToken);
                saveData.addSafeString("channelId", channelId);
            }

            @Override
            public void applyLoadData(LoadData loadData) {
                discordToken = loadData.getSafeString("token");
                channelId = loadData.getSafeString("channelId");
            }
        };
    }

    public void init() {

        GameEvents.addListener(ServerStartEvent.class, new GameEventListener<ServerStartEvent>() {
            @Override
            public void onEvent(ServerStartEvent e) {
                server = e.server;
                // TODO: add callback thread pool to increase perfomance
                jda = JDABuilder.createLight(
                            discordToken,
                            EnumSet.of(GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT)
                        )
                        .addEventListeners(new DiscordChatListener(server))
                        .setAutoReconnect(true)
                        .build();
                try {
                    jda.awaitReady();
                    Guild guild = DiscordUtils.getMainGuild(jda);

                    System.out.println("Get guild " + guild.getName());

                    channel = guild.getTextChannelById(channelId);
                    gameChatListener = new GameChatListener(channel);
                } catch (InterruptedException err) {
                    // TODO: exit event to remove connections, etc...
                    System.out.println("JDA is not loaded");
                }
            }

        });

        // TODO maybe register from separated class?
        GameEvents.addListener(PacketChatMessageEvent.class, new GameEventListener<PacketChatMessageEvent>() {
            @Override
            public void onEvent(PacketChatMessageEvent e) {
                gameChatListener.sendMessage(new PacketMessageWrapper(e, server).toMessageCreateData());
            }
        });
    }


}
