package com.burchard36;

import static com.burchard36.ApiLib.convert;

public class Logger {

    private static final String prefix = "&6Dragon&eSpigot&6Z ";

    public static void debug(final String message,
                             final Api api) {
        if (api.isDebug()) {
            System.out.println(convert(prefix + "&b:: &2DEBUG &b:: &a" + message));
        }
    }

    public static void log(final String message) {
        System.out.println(convert(prefix + "&b:: &3INFO &b:: &b" + message));
    }

    public static void warn(final String message) {
        System.out.println(convert(prefix + "&4:: &eWARN &4:: &c" + message));
    }

    public static void error(final String message) {
        System.out.println(convert(prefix + "&4:: &cERROR &4:: &c" + message));
    }
}
