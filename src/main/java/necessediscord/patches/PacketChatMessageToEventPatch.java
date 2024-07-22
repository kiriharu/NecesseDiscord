package necessediscord.patches;

import necesse.engine.GameEvents;
import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.engine.network.NetworkPacket;
import necesse.engine.network.packet.PacketChatMessage;
import necesse.engine.network.server.Server;
import necesse.engine.network.server.ServerClient;
import necessediscord.events.PacketChatMessageEvent;
import net.bytebuddy.asm.Advice;

public class PacketChatMessageToEventPatch {

    // Before leaving only patch code here, that triggers new event,
    // I tried to send message to discord right here.
    // When I add constructor to PacketChatMessageToEventPatch and
    // use static vars from it I get a crash, so workaround with events may be helpful

    @ModMethodPatch(
        target = PacketChatMessage.class,
        name = "processServer",
        arguments = {NetworkPacket.class, Server.class, ServerClient.class}
    )
    public static class MethodPatch {
        @Advice.OnMethodExit
        public static void onExit(@Advice.This PacketChatMessage thiz) {
            GameEvents.triggerEvent(new PacketChatMessageEvent(thiz));
        }
    }
}
