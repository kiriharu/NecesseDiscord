package necessediscord.listeners;

import necesse.engine.GameEventListener;
import necessediscord.NecesseDiscord;
import necessediscord.events.DeathMessageEvent;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;

public class DeathMessageListener extends GameEventListener<DeathMessageEvent> {


    @Override
    public void onEvent(DeathMessageEvent event) {
        MessageCreateData messageCreateData = MessageCreateData.fromContent(event.message);
        NecesseDiscord.CHANNEL.sendMessage(messageCreateData).queue();
    }
}
