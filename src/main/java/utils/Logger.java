package utils;

import necesse.engine.GameLog;

public class Logger {

    private static final String prefix = "[NecesseDiscord] ";

    public static void info(String message) {
        GameLog.out.println(prefix + message);
    }

    public static void err(String message) {
        GameLog.err.println(prefix + message);
    }

}
