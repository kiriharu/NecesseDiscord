package necessediscord.events;

import necesse.engine.events.GameEvent;
import necesse.engine.network.packet.PacketChatMessage;

public class PacketChatMessageEvent extends GameEvent {
    public final PacketChatMessage packet;

    public PacketChatMessageEvent(PacketChatMessage packet) {
        this.packet = packet;
    }
}
