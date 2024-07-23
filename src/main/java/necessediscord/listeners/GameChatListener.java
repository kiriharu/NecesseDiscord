package necessediscord.listeners;

import necessediscord.wrappers.IToDiscordMessage;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

// TODO: remove this useless trash
public class GameChatListener {

    TextChannel channel;

    public GameChatListener(TextChannel channel) {
        this.channel = channel;
    }

    public Void sendMessage(IToDiscordMessage wrapper) {
        channel.sendMessage(wrapper.toMessage()).queue();
        return null;
    }
}
