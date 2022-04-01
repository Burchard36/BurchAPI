package com.burchard36;

import org.bukkit.Bukkit;

import static com.burchard36.BurchAPI.convert;

public class Logger {

    private static String prefix = "&b&lBurchAPI ";

    public static void init(final Api api) {
        prefix = api.loggerPrefix();
    }

    public static void debug(final String message,
                             final Api api) {
        if (api.isDebug()) {
            Bukkit.getLogger().info(convert(prefix + "&b:: &2DEBUG &b:: &a" + message));
        }
    }

    public static void log(final String message) {
        Bukkit.getLogger().info(convert(prefix + "&b:: &3INFO &b:: &b" + message));
    }

    public static void warn(final String message) {
        Bukkit.getLogger().info(convert(prefix + "&4:: &eWARN &4:: &c" + message));
    }

    public static void error(final String message) {
        Bukkit.getLogger().info(convert(prefix + "&4:: &cERROR &4:: &c" + message));
    }
}
