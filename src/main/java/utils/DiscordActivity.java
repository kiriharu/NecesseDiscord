package utils;

import necessediscord.NecesseDiscord;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Activity;

public class DiscordActivity implements Runnable {

    private JDA jda;

    public DiscordActivity(JDA jda) {
        this.jda = jda;
    }

    private String getServerStats() {
        int online = NecesseDiscord.SERVER.getPlayersOnline();
        int slots = NecesseDiscord.SERVER.getSlots();
        int day = NecesseDiscord.SERVER.world.worldEntity.getDay();
        int tps = NecesseDiscord.SERVER.tickManager().getTPS();

        return "Online " + online + "/" + slots + " | " + "Day: " + day + " | " + "TPS: " + tps;
    }

    @Override
    public void run() {
        jda.getPresence().setActivity(Activity.playing(getServerStats()));
    }
}
