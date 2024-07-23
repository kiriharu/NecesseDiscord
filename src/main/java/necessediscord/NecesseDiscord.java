package necessediscord;

import necesse.engine.GameEventListener;
import necesse.engine.GameEvents;
import necesse.engine.GameLog;
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
import net.dv8tion.jda.internal.utils.JDALogger;
import utils.DiscordUtils;
import utils.Logger;

import java.util.EnumSet;

@ModEntry
public class NecesseDiscord {
    public static Server SERVER;
    public static TextChannel CHANNEL;
    private JDA jda;
    private String discordToken = "Enter discord token here";
    private String channelId = "Enter channel id here";

    private boolean enableChatMessages = true;
    private boolean enableConnectMessages = true;
    private boolean enableDisconnectMessages = true;
    private boolean enableDeathMessages = true;

    public ModSettings initSettings() {
        return new ModSettings() {
            @Override
            public void addSaveData(SaveData saveData) {
                saveData.addSafeString("token", discordToken);
                saveData.addSafeString("channelId", channelId);
                saveData.addBoolean("enableChatMessages", enableChatMessages);
                saveData.addBoolean("enableConnectMessages", enableConnectMessages);
                saveData.addBoolean("enableDisconnectMessages", enableDisconnectMessages);
                saveData.addBoolean("enableDeathMessages", enableDeathMessages);
            }

            @Override
            public void applyLoadData(LoadData loadData) {
                discordToken = loadData.getSafeString("token");
                channelId = loadData.getSafeString("channelId");
                enableChatMessages = loadData.getBoolean("enableChatMessages");
                enableConnectMessages = loadData.getBoolean("enableConnectMessages");
                enableDisconnectMessages = loadData.getBoolean("enableDisconnectMessages");
                enableDeathMessages = loadData.getBoolean("enableDeathMessages");
            }
        };
    }

    public void init() {
        JDALogger.setFallbackLoggerEnabled(false);
        GameEvents.addListener(ServerStartEvent.class, new GameEventListener<ServerStartEvent>() {
            @Override
            public void onEvent(ServerStartEvent e) {
                NecesseDiscord.SERVER = e.server;
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
                    Logger.info("Get guild " + guild.getName());
                    NecesseDiscord.CHANNEL = guild.getTextChannelById(channelId);
                } catch (InterruptedException err) {
                }
            }
        });

        if (enableChatMessages) {
            Logger.info("Register chat messages listener");
            GameEvents.addListener(PacketChatMessageEvent.class, new GameChatListener());
        }
        if (enableDisconnectMessages) {
            Logger.info("Register disconnect messages listener");
            GameEvents.addListener(PacketDisconnectEvent.class, new DisconnectMessageListener());
        }
        if (enableConnectMessages) {
            Logger.info("Register connect messages listener");
            GameEvents.addListener(ServerClientConnectedEvent.class, new ConnectMessageListener());
        }
        if (enableDeathMessages) {
            Logger.info("Register death messages listener");
            GameEvents.addListener(DeathMessageEvent.class, new DeathMessageListener());
        }
    }

    public void dispose() {
        if (jda != null) {
            jda.shutdownNow();
        }
    }

}
