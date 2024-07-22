package necessediscord.wrappers;

import necesse.engine.network.server.Server;
import necessediscord.events.PacketChatMessageEvent;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;

public class PacketMessageWrapper {
    public String playerName;
    public String message;

    public PacketMessageWrapper(PacketChatMessageEvent event, Server server) {
        this.playerName = server.getClient(event.packet.slot).getName();
        this.message = event.packet.gameMessage.translate();
    }

    public MessageCreateData toMessageCreateData() {
        return MessageCreateData.fromContent(playerName + ": " + message);
    }
}
