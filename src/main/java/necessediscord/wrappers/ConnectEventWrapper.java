package necessediscord.wrappers;

import necesse.engine.events.ServerClientConnectedEvent;
import necesse.engine.localization.Localization;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;

public class ConnectEventWrapper implements IToDiscordMessage {

    private final String playerName;

    public ConnectEventWrapper(ServerClientConnectedEvent e) {
        this.playerName = e.client.getName();
    }

    @Override
    public MessageCreateData toMessage() {
        return MessageCreateData.fromContent(
            Localization.translate(
                "misc",
                "playerjoined",
                new String[]{"player", playerName}
            )
        );
    }
}
