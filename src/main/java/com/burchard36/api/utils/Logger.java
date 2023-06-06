package com.burchard36.api.utils;

import com.burchard36.api.BurchAPI;
import org.bukkit.Bukkit;

import static com.burchard36.api.BurchAPI.convert;

/**
 * The API's logger
 */
public class Logger {

    /**
     * Logs a debug message, if the API is in debug mode
     * @param message A {@link String} to debug, supports ampersand symbols
     */
    public static void debug(final String message) {
        if (BurchAPI.INSTANCE.isDebug()) {
            BurchAPI.INSTANCE.getLogger().info(convert(BurchAPI.INSTANCE.loggerPrefix()
                    + " &b:: &2DEBUG &b:: &a" + message, null));
        }
    }

    /**
     * Logs a message to the server, supports ampersand symbols for coloring
     * @param message A {@link String}, supports ampersand symbols for coloring
     */
    public static void log(final String message) {
        BurchAPI.INSTANCE.getLogger().info(convert(BurchAPI.INSTANCE.loggerPrefix()
                + " &b:: &3INFO &b::" + message, null));
    }

    /**
     * Warns the server, supports ampersand symbols
     * @param message A {@link String} to warn to the server, supports ampersand symbols for coloring
     */
    public static void warn(final String message) {
        Bukkit.getLogger().info(convert(BurchAPI.INSTANCE.loggerPrefix()
                + " &4:: &eWARN &4:: &c" + message, null));
    }

    /**
     * Logs an error to the server, supports ampersand symbols for coloring
     * @param message A {@link String} to log as an error, supports ampersand symbols for coloring
     */
    public static void error(final String message) {
        Bukkit.getLogger().info(convert(BurchAPI.INSTANCE.loggerPrefix()
                + " &4:: &cERROR &4:: &c" + message, null));
    }
}
