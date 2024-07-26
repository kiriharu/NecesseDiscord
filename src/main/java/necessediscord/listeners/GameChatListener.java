package necessediscord.listeners;

import necesse.engine.GameEventListener;
import necessediscord.NecesseDiscord;
import necessediscord.events.PacketChatMessageEvent;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;

public class GameChatListener extends GameEventListener<PacketChatMessageEvent> {


    @Override
    public void onEvent(PacketChatMessageEvent event) {
        String player = NecesseDiscord.SERVER.getClient(event.packet.slot).getName();
        String msg = event.packet.gameMessage.translate();

        // Don't send commands to chat
        if (msg.startsWith("/")) return;

        MessageCreateData messageCreateData = MessageCreateData.fromContent(player + ": " + msg);
        NecesseDiscord.CHANNEL.sendMessage(messageCreateData).queue();
    }
}
