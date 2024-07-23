package necessediscord.listeners;

import necesse.engine.GameEventListener;
import necesse.engine.events.ServerClientConnectedEvent;
import necesse.engine.localization.Localization;
import necessediscord.NecesseDiscord;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;

public class ConnectMessageListener extends GameEventListener<ServerClientConnectedEvent> {

    @Override
    public void onEvent(ServerClientConnectedEvent event) {
        String playerName = event.client.getName();
        MessageCreateData message = MessageCreateData.fromContent(
                Localization.translate(
                    "misc",
                    "playerjoined",
                    new String[]{"player", playerName}
            )
        );
        NecesseDiscord.CHANNEL.sendMessage(message).queue();
    }
}
