package necessediscord.wrappers;

import necesse.engine.localization.message.GameMessageBuilder;
import necesse.engine.network.packet.PacketChatMessage;
import necesse.gfx.GameColor;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class DiscordMessageWrapper {

    public User author;
    public Member member;
    public String message;

    public DiscordMessageWrapper(MessageReceivedEvent event) {
        this.author = event.getAuthor();
        this.member = event.getMember();
        this.message = event.getMessage().getContentDisplay();
    }

    public PacketChatMessage toPacketChatMessage() {
        return new PacketChatMessage(
            new GameMessageBuilder()
                .append(GameColor.CYAN.getColorCode())
                .append("[Discord] ")
                .append(GameColor.getCustomColorCode(member.getColor()))
                .append(author.getEffectiveName() + ": ")
                .append(GameColor.WHITE.getColorCode())
                .append(message)
        );
    }
}
