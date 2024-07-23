package necessediscord;

import necesse.engine.GameEventListener;
import necesse.engine.GameEvents;
import necesse.engine.events.ServerClientConnectedEvent;
import necesse.engine.events.ServerStartEvent;
import necesse.engine.modLoader.ModSettings;
import necesse.engine.modLoader.annotations.ModEntry;
import necesse.engine.network.server.Server;
import necesse.engine.save.LoadData;
import necesse.engine.save.SaveData;
import necessediscord.events.DeathMessageEvent;
import necessediscord.events.PacketChatMessageEvent;
import necessediscord.events.PacketDisconnectEvent;
import necessediscord.listeners.*;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.WebhookClient;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.requests.GatewayIntent;
import utils.DiscordUtils;

import java.util.EnumSet;

@ModEntry
public class NecesseDiscord {
    public static Server SERVER;
    public static TextChannel CHANNEL;
    private JDA jda;
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
                NecesseDiscord.SERVER = e.server;
                // TODO: add callback thread pool to increase perfomance
                jda = JDABuilder.createLight(
                            discordToken,
                            EnumSet.of(GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT)
                        )
                        .addEventListeners(new DiscordChatListener(NecesseDiscord.SERVER))
                        .setAutoReconnect(true)
                        .build();
                try {
                    jda.awaitReady();
                    Guild guild = DiscordUtils.getMainGuild(jda);
                    System.out.println("Get guild " + guild.getName());
                    NecesseDiscord.CHANNEL = guild.getTextChannelById(channelId);
                } catch (InterruptedException err) {
                    // TODO: exit event to remove connections, etc...
                    System.out.println("JDA is not loaded");
                }
            }
        });

        GameEvents.addListener(PacketChatMessageEvent.class, new GameChatListener());
        GameEvents.addListener(PacketDisconnectEvent.class, new DisconnectMessageListener());
        GameEvents.addListener(ServerClientConnectedEvent.class, new ConnectMessageListener());
        GameEvents.addListener(DeathMessageEvent.class, new DeathMessageListener());

    }

}
