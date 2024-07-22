package necessediscord.wrappers;

import necesse.engine.network.server.Server;
import necessediscord.events.PacketChatMessageEvent;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;

public class ChatMessageEventWrapper implements IToDiscordMessage {
    public String playerName;
    public String message;

    public ChatMessageEventWrapper(PacketChatMessageEvent event, Server server) {
        this.playerName = server.getClient(event.packet.slot).getName();
        this.message = event.packet.gameMessage.translate();
    }

    public MessageCreateData toMessage() {
        return MessageCreateData.fromContent(playerName + ": " + message);
    }
}
