package necessediscord.listeners;

import necesse.engine.GameEventListener;
import necessediscord.NecesseDiscord;
import necessediscord.events.AchievementUnlockEvent;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;

public class AchievementUnlockListener extends GameEventListener<AchievementUnlockEvent> {

    @Override
    public void onEvent(AchievementUnlockEvent event) {
        String playerName = event.client.getName();
        String achievementName = event.achievement.getName().translate();
        String message = ":trophy: " + playerName + " unlocked achievement: " + achievementName;
        MessageCreateData messageCreateData = MessageCreateData.fromContent(message);
        NecesseDiscord.CHANNEL.sendMessage(messageCreateData).queue();
    }
}
