package necessediscord.patches;

import necesse.engine.GameEvents;
import necesse.engine.localization.message.GameMessage;
import necesse.engine.localization.message.LocalMessage;
import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.entity.mobs.Attacker;
import necesse.entity.mobs.DeathMessageTable;
import necessediscord.events.DeathMessageEvent;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.implementation.bytecode.assign.Assigner;

public class DeathMessageToEventPatch {

    @ModMethodPatch(
        target = DeathMessageTable.class,
        name = "getDeathMessage",
        arguments = {Attacker.class, GameMessage.class}
    )
    public static class MethodPatch {
        @Advice.OnMethodExit
        public static void onExit(@Advice.Return(typing = Assigner.Typing.DYNAMIC) LocalMessage returned) {
            GameEvents.triggerEvent(new DeathMessageEvent(returned.translate()));
        }
    }

}
