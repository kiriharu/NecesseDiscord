package utils;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;

public class DiscordUtils {

    public static Guild getMainGuild(JDA jda) {
        return jda.getGuilds().get(0);
    }

}
