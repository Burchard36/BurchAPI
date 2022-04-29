package com.burchard36.api.data;

import com.burchard36.api.data.json.JsonDataFile;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;

public interface FileDataStore extends DataStore {

    BukkitTask autoSaveTask();

}
