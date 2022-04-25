package com.burchard36.api.utils;

import com.burchard36.api.BurchAPI;
import org.bukkit.Bukkit;

import static com.burchard36.api.BurchAPI.convert;

public class Logger {

    public static void debug(final String message) {
        if (BurchAPI.INSTANCE.isDebug()) {
            BurchAPI.INSTANCE.getLogger().info(convert(BurchAPI.INSTANCE.loggerPrefix()
                    + " &b:: &2DEBUG &b:: &a" + message));
        }
    }

    public static void log(final String message) {
        BurchAPI.INSTANCE.getLogger().info(convert(BurchAPI.INSTANCE.loggerPrefix()
                + " &b:: &3INFO &b:: &b" + message));
    }

    public static void warn(final String message) {
        Bukkit.getLogger().info(convert(BurchAPI.INSTANCE.loggerPrefix()
                + " &4:: &eWARN &4:: &c" + message));
    }

    public static void error(final String message) {
        Bukkit.getLogger().info(convert(BurchAPI.INSTANCE.loggerPrefix()
                + " &4:: &cERROR &4:: &c" + message));
    }
}
