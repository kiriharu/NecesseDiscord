package necessediscord.listeners;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;

// TODO: remove this useless trash
public class GameChatListener {

    TextChannel channel;

    public GameChatListener(TextChannel channel) {
        this.channel = channel;
    }

    public Void sendMessage(MessageCreateData message) {
        channel.sendMessage(message).queue();
        return null;
    }
}
