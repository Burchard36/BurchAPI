package com.burchard36;

public interface Api {

    /**
     * If the debug mode is true, when debug is called on the
     * Logger#debug method a message gets logged, this also may effect other things
     * however thats all it is originally meant for
     */
    boolean isDebug();

    /**
     * This is the prefix that shows before the logger
     * @return Prefix before the logger
     */
    String loggerPrefix();
}
