package necessediscord.listeners;

import necesse.engine.network.server.Server;
import necessediscord.wrappers.DiscordMessageWrapper;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class DiscordChatListener extends ListenerAdapter {

    private final Server server;

    public DiscordChatListener(Server server) {
        this.server = server;
    }

    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        // Ignore bots message to remove duplicates
        if (event.getAuthor().isBot()) {
            return;
        }
        server.network.sendToAllClients(new DiscordMessageWrapper(event).toPacketChatMessage());
    }
}
