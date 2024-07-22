package necessediscord.patches;

import necesse.engine.GameEvents;
import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.engine.network.NetworkPacket;
import necesse.engine.network.packet.PacketDisconnect;
import necesse.engine.network.server.Server;
import necesse.engine.network.server.ServerClient;
import necessediscord.events.PacketDisconnectEvent;
import net.bytebuddy.asm.Advice;

public class PacketDisconnectMessageToEventPatch {


    @ModMethodPatch(
        target = PacketDisconnect.class,
        name = "processServer",
        arguments = {NetworkPacket.class, Server.class, ServerClient.class}
    )
    public static class MethodPatch {
        // Use OnMethodEnter because after exit we can't get user by slot id
        @Advice.OnMethodEnter
        public static void onEnter(@Advice.This PacketDisconnect thiz) {
            GameEvents.triggerEvent(new PacketDisconnectEvent(thiz));
        }
    }
}
