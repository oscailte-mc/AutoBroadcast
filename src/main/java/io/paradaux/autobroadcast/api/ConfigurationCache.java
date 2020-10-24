/*
 * Copyright © 2020 Property of Rían Errity Licensed under GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007. See <LICENSE.md>
 */

package io.paradaux.autobroadcast.api;

import io.paradaux.autobroadcast.AutoBroadcast;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class ConfigurationCache {

    int interval;
    double configVersion;
    boolean enableBypassPermission;
    List<String> announcements;
    boolean bstatsEnabled;

    public ConfigurationCache(int interval, double configVersion, boolean enableBypassPermission, List<String> announcements, boolean bstatsEnabled) {
        this.interval = interval;
        this.configVersion = configVersion;
        this.enableBypassPermission = enableBypassPermission;
        this.announcements = announcements;
        this.bstatsEnabled = bstatsEnabled;
    }

    public ConfigurationCache(AutoBroadcast autoBroadcast, FileConfiguration config) {
        this.interval = config.getInt("interval", 300);
        this.configVersion = config.getDouble("config-version");
        this.enableBypassPermission = config.getBoolean("enable-bypass-permission", false);
        this.announcements = config.getStringList("announcements");
        this.bstatsEnabled = config.getBoolean("bstats_enabled", true);
    }

    public int getInterval() {
        return interval;
    }

    public double getConfigVersion() {
        return configVersion;
    }

    public boolean isEnableBypassPermission() {
        return enableBypassPermission;
    }

    public List<String> getAnnouncements() {
        return announcements;
    }

    public boolean isBstatsEnabled() {
        return bstatsEnabled;
    }
}
