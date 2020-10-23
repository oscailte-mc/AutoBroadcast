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
    boolean enabledBstats;

    public ConfigurationCache() {

        if (configVersion == 1.0) {
            p.getLogger().info("You were using an older version of AutoBroadcast before this. Switching configuration versions.");
            p.getConfig().set("config-version", 1.1);
            p.getConfig().options().header("# Comments were partially lost whilst updating the configuration. You can grab the 1.1.1 default \n # Configuration from the Spigot Resource page.");
            p.getLogger().info("Configuration has been updated. Comments were lost in the process.");
        }

        if (configVersion == 1.1) {
            p.getLogger().info("You were using an older version of AutoBroadcast before this. Switching configuration versions.");
            p.getConfig().set("config-version", 1.2);
            p.getConfig().set("bstats_enabled", true);
            p.getConfig().options().header("# Comments were partially lost whilst updating the configuration. You can grab the 1.2.0 default \n # Configuration from the Spigot Resource page.");
            p.getLogger().info("Configuration has been updated. Comments were lost in the process.");
        }

        this.configVersion = config.getDouble("config-version");
        this.announcements = config.getStringList("announcements");
        this.interval = config.getInt("interval");
        this.useBypassPermission = config.getBoolean("enable-bypass-permission");
        this.enabledBstats = config.getBoolean("bstats_enabled");


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

    public boolean isEnabledBstats() { return enabledBstats; }
}
