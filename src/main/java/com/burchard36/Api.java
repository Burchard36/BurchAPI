package com.burchard36;

import com.burchard36.json.PluginDataManager;

public interface Api {

    /**
     * In order for the Data Management side of this plugin to work, it needs to have access to an already running
     * PluginDataManager
     * @return running PluginDataManager instance
     */
    PluginDataManager getPluginDataManager();

    /**
     * If the debug mode is true, when debug is called on the
     * Logger#debug method a message gets logged, this also may effect other things
     * however thats all it is originally meant for
     */
    boolean isDebug();
}
