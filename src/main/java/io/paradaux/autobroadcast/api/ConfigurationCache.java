/*
 * Copyright © 2020 Property of Rían Errity Licensed under GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007. See <LICENSE.md>
 */

package io.paradaux.autobroadcast.api;

import org.bukkit.configuration.file.YamlConfiguration;
import java.util.List;

public class ConfigurationCache {

    private static ConfigurationCache instance;
    public static ConfigurationCache getInstance() { return instance; }

    private int interval;
    private double configVersion;
    private boolean enableBypassPermission;
    private List<String> announcements;
    private boolean bstatsEnabled;

    // Prevent instantiation
    private ConfigurationCache() {

    }

    public Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final ConfigurationCache cache;

        public Builder() {
            cache = new ConfigurationCache();
        }

        public ConfigurationCache build() {
            ConfigurationCache.instance = cache;
            return cache;
        }

    }

}
