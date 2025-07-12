package necessediscord.events;

import necesse.engine.events.GameEvent;
import necesse.engine.network.server.ServerClient;
import necesse.engine.save.SaveAchievement;

public class AchievementUnlockEvent extends GameEvent {
    public final ServerClient client;
    public final SaveAchievement achievement;

    public AchievementUnlockEvent(ServerClient client, SaveAchievement achievement) {
        this.client = client;
        this.achievement = achievement;
    }
}
