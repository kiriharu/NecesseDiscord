package necessediscord.listeners;

import necesse.engine.localization.message.GameMessageBuilder;
import necesse.engine.network.packet.PacketChatMessage;
import necesse.engine.network.server.Server;
import necesse.gfx.GameColor;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
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

        // Construct message
        User author = event.getAuthor();
        Member member = event.getMember();
        String message = event.getMessage().getContentDisplay();

        assert member != null;
        PacketChatMessage packet = new PacketChatMessage(
            new GameMessageBuilder()
                .append(GameColor.CYAN.getColorCode())
                .append("[Discord] ")
                .append(GameColor.getCustomColorCode(member.getColor()))
                .append(author.getEffectiveName() + ": ")
                .append(GameColor.WHITE.getColorCode())
                .append(message)
        );

        server.network.sendToAllClients(packet);
    }
}
