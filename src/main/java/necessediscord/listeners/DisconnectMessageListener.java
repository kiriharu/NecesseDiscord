package necessediscord.listeners;

import necesse.engine.GameEventListener;
import necesse.engine.localization.Localization;
import necessediscord.NecesseDiscord;
import necessediscord.events.PacketDisconnectEvent;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;

public class DisconnectMessageListener extends GameEventListener<PacketDisconnectEvent> {

    @Override
    public void onEvent(PacketDisconnectEvent event) {
        String playerName = NecesseDiscord.SERVER.getClient(event.packet.slot).getName();
        String errorMessage = event.packet.code.getErrorMessage(event.packet.codeContent);
        String message = Localization.translate(
            "disconnect",
            "chatmsg",
            "name",
            playerName, "msg",
            errorMessage
        );
        MessageCreateData messageCreateData = MessageCreateData.fromContent(message);
        NecesseDiscord.CHANNEL.sendMessage(messageCreateData).queue();
    }
}
