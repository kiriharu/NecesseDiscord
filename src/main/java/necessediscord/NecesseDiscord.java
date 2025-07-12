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
import necessediscord.events.AchievementUnlockEvent;
import necessediscord.events.DeathMessageEvent;
import necessediscord.events.PacketChatMessageEvent;
import necessediscord.events.PacketDisconnectEvent;
import necessediscord.listeners.*;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.internal.utils.Checks;
import net.dv8tion.jda.internal.utils.JDALogger;
import utils.DiscordActivity;
import utils.DiscordUtils;
import utils.Logger;

import java.math.BigInteger;
import java.util.EnumSet;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@ModEntry
public class NecesseDiscord {
    public static Server SERVER;
    public static TextChannel CHANNEL;
    private JDA jda;
    private String discordToken = "Enter discord token here";
    private String channelId = "Enter channel id here";
    private boolean loadedFlag = true;

    private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    private boolean enableChatMessages = true;
    private boolean enableConnectMessages = true;
    private boolean enableDisconnectMessages = true;
    private boolean enableDeathMessages = true;
    private boolean enableDiscordActivity = true;
    private boolean enableAchievementMessages = true;

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
                saveData.addBoolean("enableDiscordActivity", enableDiscordActivity);
                saveData.addBoolean("enableAchievementMessages", enableAchievementMessages);
            }

            @Override
            public void applyLoadData(LoadData loadData) {
                discordToken = loadData.getSafeString("token");
                channelId = loadData.getSafeString("channelId");
                enableChatMessages = loadData.getBoolean("enableChatMessages");
                enableConnectMessages = loadData.getBoolean("enableConnectMessages");
                enableDisconnectMessages = loadData.getBoolean("enableDisconnectMessages");
                enableDeathMessages = loadData.getBoolean("enableDeathMessages");
                enableDiscordActivity = loadData.getBoolean("enableDiscordActivity");
                enableAchievementMessages = loadData.getBoolean("enableAchievementMessages");
            }
        };
    }

    public void init() {
        JDALogger.setFallbackLoggerEnabled(false);

        try {
            Checks.notEmpty(discordToken, "token");
            Checks.noWhitespace(discordToken, "token");
        } catch (IllegalArgumentException ignored) {
            Logger.info(
                "Found default or invalid token in config. " +
                "Please, change token in kiriharu.necessediscord.cfg!"
            );
            loadedFlag = false;
            return;
        }

        try {
            Checks.notEmpty(channelId, "channelId");
            Checks.noWhitespace(channelId, "channelId");
            new BigInteger(channelId);
        } catch (IllegalArgumentException ignored) {
            Logger.info(
                "Found default or invalid channelId in config. " +
                "Please, change channelId in kiriharu.necessediscord.cfg!"
            );
            loadedFlag = false;
            return;
        }

        GameEvents.addListener(ServerStartEvent.class, new GameEventListener<ServerStartEvent>() {
            @Override
            public void onEvent(ServerStartEvent e) {
                NecesseDiscord.SERVER = e.server;

                jda = JDABuilder.createLight(
                        discordToken,
                        EnumSet.of(GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT)
                    )
                    .addEventListeners(new DiscordChatListener(NecesseDiscord.SERVER, channelId))
                    .setAutoReconnect(true)
                    .build();
                try {
                    jda.awaitReady();
                    Guild guild = DiscordUtils.getMainGuild(jda);
                    Logger.info("Get guild " + guild.getName());
                    NecesseDiscord.CHANNEL = guild.getTextChannelById(channelId);
                } catch (InterruptedException err) {
                    loadedFlag = false;
                    Logger.err("Some error ocurred while trying to connect to discord");
                }

                if (loadedFlag & enableDiscordActivity) {
                    Logger.info("Enable Discord activity updater");
                    DiscordActivity activity = new DiscordActivity(jda);
                    scheduler.scheduleWithFixedDelay(activity, 30, 30, TimeUnit.SECONDS);
                }
            }

        });

        if (loadedFlag) {
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
            if (enableAchievementMessages) {
                Logger.info("Register achievement unlock listener");
                GameEvents.addListener(AchievementUnlockEvent.class, new AchievementUnlockListener());
            }
        }

    }

    public void dispose() {
        if (jda != null) {
            jda.shutdown();
        }
        if (scheduler != null) {
            scheduler.shutdown();
        }
    }

}
