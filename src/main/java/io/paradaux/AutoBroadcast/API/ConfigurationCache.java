/*
 * Copyright © 2020 Property of Rían Errity Licensed under GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007. See <LICENSE.md>
 */

package io.paradaux.AutoBroadcast.API;

import io.paradaux.AutoBroadcast.AutoBroadcast;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class ConfigurationCache {

    Plugin p = AutoBroadcast.getPlugin();
    FileConfiguration config = p.getConfig();

    double configVersion;
    boolean useBypassPermission;
    List<String> announcements;
    int interval;

    public ConfigurationCache() {
        this.configVersion = config.getDouble("config-version");
        this.announcements = config.getStringList("announcements");
        this.interval = config.getInt("interval");
        this.useBypassPermission = config.getBoolean("enable-bypass-permission");

        if (configVersion == 0.0) {
            p.getLogger().info("You were using an older version of AutoBroadcast before this. Switching configuration versions.");
            p.getConfig().set("config-version", 1.1);
            config.options().header("# Comments were partially lost whilst updating the configuration. You can grab the 1.1.1 default \n # Configuration from the Spigot Resource page.");
            p.getLogger().info("Configuration has been updated. Comments were lost in the process.");
        }
    }

    public boolean isUseBypassPermission() {
        return useBypassPermission;
    }

    public List<String> getAnnouncements() {
        return announcements;
    }

    public int getInterval() {
        return interval;
    }

}
