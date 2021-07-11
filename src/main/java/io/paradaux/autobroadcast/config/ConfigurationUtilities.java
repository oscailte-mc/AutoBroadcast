/*
 * Copyright © 2020 Property of Rían Errity Licensed under GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007. See <LICENSE.md>
 */

package io.paradaux.autobroadcast.config;

import io.paradaux.autobroadcast.AutoBroadcast;
import io.paradaux.autobroadcast.BroadcastManager;
import io.paradaux.autobroadcast.adventure.AdventureImpl;
import io.paradaux.autobroadcast.locale.LocaleLogger;
import io.paradaux.autobroadcast.locale.LocaleManager;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

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
            autoBroadcast.getLogger().severe("Error whilst updating config.");
        }
    }

    public void reload() {
        BroadcastManager broadcastManager = BroadcastManager.getInstance();
        ConfigurationCache.builder().build(this.getConfig());
        broadcastManager.cancel();
        new BroadcastManager(autoBroadcast);
    }

    public void update(FileConfiguration config) {
        Logger logger = autoBroadcast.getLogger();

        if (config.getDouble("config-version") < 2.0d) {
            LocaleLogger.info("system.autobroadcast.config.update.version-change.legacy", String.valueOf(config.getDouble("config-version")));
            configFile.renameTo(new File(autoBroadcast.getDataFolder(), "config.yml.legacy"));
            autoBroadcast.saveDefaultConfig();
        }
    }

}
