package necessediscord.wrappers;

import net.dv8tion.jda.api.utils.messages.MessageCreateData;

public interface IToDiscordMessage {
    public MessageCreateData toMessage();
}
