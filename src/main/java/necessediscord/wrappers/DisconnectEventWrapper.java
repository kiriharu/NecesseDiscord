package necessediscord.wrappers;

import necesse.engine.localization.Localization;
import necesse.engine.network.server.Server;
import necessediscord.events.PacketDisconnectEvent;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;

public class DisconnectEventWrapper implements IToDiscordMessage {
    public String playerName;
    public String errorMessage;

    public DisconnectEventWrapper(PacketDisconnectEvent event, Server server) {
        this.playerName = server.getClient(event.packet.slot).getName();
        this.errorMessage = event.packet.code.getErrorMessage(event.packet.codeContent);
    }

    public MessageCreateData toMessage() {
        String message = Localization.translate(
            "disconnect",
            "chatmsg",
            "name",
            playerName, "msg",
            errorMessage
        );
        return MessageCreateData.fromContent(message);
    }
}
