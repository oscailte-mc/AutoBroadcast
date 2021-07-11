/*
 * Copyright © 2020 Property of Rían Errity Licensed under GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007. See <LICENSE.md>
 */

package io.paradaux.autobroadcast.config;

import io.paradaux.autobroadcast.AutoBroadcast;
import io.paradaux.autobroadcast.BroadcastManager;
import io.paradaux.autobroadcast.locale.LocaleLogger;
import io.paradaux.autobroadcast.locale.LocaleManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Logger;

public class ConfigurationUtilities {

    private static ConfigurationUtilities instance;
    public static ConfigurationUtilities getInstance() { return instance; }

    private final AutoBroadcast autoBroadcast;
    private final File configFile;

    public ConfigurationUtilities(AutoBroadcast autoBroadcast) {
        configFile = new File(Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("AutoBroadcast")).getDataFolder(), "config.yml");
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

    public void update() {
        this.updateConfiguration(this.getConfig());
    }

    public void reload() {
        BroadcastManager broadcastManager = BroadcastManager.getInstance();
        ConfigurationCache.builder().build(this.getConfig());
        broadcastManager.cancel();
        new BroadcastManager(autoBroadcast);
    }

    public void updateConfiguration(FileConfiguration config) {
        Logger logger = autoBroadcast.getLogger();

        if (config.getDouble("config-version") <= 1.0d) {
            LocaleLogger.info("system.autobroadcast.config.update.version-change.start", String.valueOf(config.getDouble("config-version")));
            config.set("config-version", 1.1d);

            config.options().header(LocaleManager.get("system.autobroadcast.config.update.version-chage.comments-lost"));
        }

        if (config.getDouble("config-version") == 1.1d) {
            LocaleLogger.info("system.autobroadcast.config.update.version-change.start", String.valueOf(config.getDouble("config-version")));
            config.set("config-version", 1.2d);
            config.set("bstats_enabled", true);

            config.options().header(LocaleManager.get("system.autobroadcast.config.update.version-chage.comments-lost"));
        }

        this.setConfig(config);
    }

}
