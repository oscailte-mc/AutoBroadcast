/*
 * Copyright (c) 2023, Rían Errity. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 3 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 3 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 3 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Rían Errity <rian@paradaux.io> or visit https://paradaux.io
 * if you need additional information or have any questions.
 * See LICENSE.md for more details.
 */

package io.paradaux.autobroadcast.config;

import org.bukkit.configuration.file.YamlConfiguration;

import java.util.List;

/**
 * Represents a Plain Ol' Java Representation of the "config.yml" configuration file.
 * @author Rían Errity
 * @since 2.0.0
 * */
public class ConfigurationCache {

    private static ConfigurationCache instance;
    public static ConfigurationCache getInstance() { return instance; }

    private int interval;
    private double configVersion;
    private boolean enableBypassPermission;
    private boolean randomizeAnnouncements;
    private List<String> announcements;
    private boolean bstatsEnabled;

    // Prevent instantiation
    private ConfigurationCache() {

    }

    public static Builder builder() {
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

        public ConfigurationCache build(YamlConfiguration config) {
            cache.interval = config.getInt("interval", 300);
            cache.configVersion = config.getDouble("config-version", 2.0);
            cache.enableBypassPermission = config.getBoolean("enable-bypass-permission", false);
            cache.randomizeAnnouncements = config.getBoolean("randomize-announcements", false);
            cache.announcements = config.getStringList("announcements");
            cache.bstatsEnabled = config.getBoolean("bstats_enabled", true);
            return build();
        }
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

    public boolean isRandomizeAnnouncements() {
        return randomizeAnnouncements;
    }

    public List<String> getAnnouncements() {
        return announcements;
    }

    public boolean isBstatsEnabled() {
        return bstatsEnabled;
    }
}
