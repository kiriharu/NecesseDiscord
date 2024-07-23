package necessediscord.events;

import necesse.engine.events.GameEvent;

public class DeathMessageEvent extends GameEvent {
    public final String message;

    public DeathMessageEvent(String message) {
        this.message = message;
    }
}
