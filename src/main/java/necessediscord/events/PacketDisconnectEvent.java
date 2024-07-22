package necessediscord.events;

import necesse.engine.events.GameEvent;
import necesse.engine.network.packet.PacketDisconnect;

public class PacketDisconnectEvent extends GameEvent {
    public final PacketDisconnect packet;

    public PacketDisconnectEvent(PacketDisconnect packet) {
        this.packet = packet;
    }
}
