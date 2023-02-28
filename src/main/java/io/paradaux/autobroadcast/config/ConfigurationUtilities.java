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

import io.paradaux.autobroadcast.AutoBroadcast;
import io.paradaux.autobroadcast.BroadcastManager;
import io.paradaux.autobroadcast.locale.LocaleLogger;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

/**
 * Contains various saving, reloading and updating utilities for manipulating the configuration files.
 * @author Rían Errity
 * @since 2.0.0
 * */
public class ConfigurationUtilities {

    private static ConfigurationUtilities instance;
    public static ConfigurationUtilities getInstance() { return instance; }

    private final AutoBroadcast autoBroadcast;
    private final File configFile;

    public ConfigurationUtilities(AutoBroadcast autoBroadcast) {
        configFile = new File(autoBroadcast.getDataFolder(), "config.yml");
        this.autoBroadcast = autoBroadcast;
        instance = this;
    }

    public YamlConfiguration getConfig() {
        return YamlConfiguration.loadConfiguration(configFile);
    }

    public void setConfig(FileConfiguration config) {
        try {
            config.save(configFile);
        } catch (IOException exception) {
            LocaleLogger.error("system.autobradcast.config.save.error", exception.getMessage());
        }
    }

    public void reload() {
        BroadcastManager broadcastManager = BroadcastManager.getInstance();
        ConfigurationCache.builder().build(this.getConfig());
        broadcastManager.setCancelled(true);
        new BroadcastManager(autoBroadcast);
    }

    public void update(FileConfiguration config) {
        if (config.getDouble("config-version") < 2.0d) {
            LocaleLogger.info("system.autobroadcast.config.update.version-change.start", config.getDouble("config-version") + "", 2.0d + "");
            LocaleLogger.info("system.autobroadcast.config.update.version-change.legacy", String.valueOf(config.getDouble("config-version")));
            if (!configFile.renameTo(new File(autoBroadcast.getDataFolder(), "config.yml.legacy"))) {
                LocaleLogger.error("system.autobroadcast.config.update.rename-failure");
            }

            autoBroadcast.saveDefaultConfig();
        }

        LocaleLogger.info("system.autobroadcast.config.update.version-change.comments-lost");
    }

}
