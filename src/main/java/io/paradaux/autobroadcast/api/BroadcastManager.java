package io.paradaux.autobroadcast.api;

import io.paradaux.autobroadcast.AutoBroadcast;
import org.bukkit.scheduler.BukkitTask;

public class BroadcastManager {

    ConfigurationCache configurationCache;
    AutoBroadcast autoBroadcast;
    BukkitTask task;

    public BroadcastManager(AutoBroadcast autoBroadcast, ConfigurationCache configurationCache) {
        this.autoBroadcast = autoBroadcast;
        this.configurationCache = configurationCache;
    }

    public BukkitTask createTaskTimer() {

    }



}
