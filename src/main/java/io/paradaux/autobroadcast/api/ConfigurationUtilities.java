/*
 * Copyright © 2020 Property of Rían Errity Licensed under GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007. See <LICENSE.md>
 */

package io.paradaux.autobroadcast.api;

import io.paradaux.autobroadcast.AutoBroadcast;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public class ConfigurationUtilities {

    AutoBroadcast autoBroadcast;
    File configFile;
    File localeFile;

    public ConfigurationUtilities(AutoBroadcast autoBroadcast) {
        configFile = new File(Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("AutoBroadcast")).getDataFolder(), "config.yml");
        localeFile = new File(Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("AutoBroadcast")).getDataFolder(), "locale.yml");
        this.autoBroadcast = autoBroadcast;
    }

    public YamlConfiguration getLocale() {
        return YamlConfiguration.loadConfiguration(localeFile);
    }

    public YamlConfiguration getConfig() {
        return YamlConfiguration.loadConfiguration(configFile);
    }

    public void setLocale(FileConfiguration locale) {
        try {
            locale.save(localeFile);
        } catch (IOException exception) {
            autoBroadcast.getLogger().severe("Error whilst updating locale.");
        }
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
        this.updateLocale(this.getLocale());
    }

    public void reload() {
        BroadcastManager broadcastManager = AutoBroadcast.getBroadcastManager();
        autoBroadcast.setConfigurationCache(new ConfigurationCache(autoBroadcast, this.getConfig()));
        autoBroadcast.setLocaleCache(new LocaleCache(this.getLocale()));
        broadcastManager.cancel();
        autoBroadcast.setBroadcastManager(new BroadcastManager(autoBroadcast, AutoBroadcast.getConfigurationCache()));
    }

    public void updateConfiguration(FileConfiguration config) {
        Logger logger = autoBroadcast.getLogger();

        if (config.getDouble("config-version") <= 1.0d) {
            logger.info("Switching config versions from: " + config.getDouble("config-version") + " to: 1.1");
            config.set("config-version", 1.1d);

            config.options().header("Comments were partially lost whilst updating the config. You can grab the 1.2.0 default \nConfig from the Spigot Resource page.");
            logger.info("Config has been updated. Comments were lost in the process due to Spigot limitations.");
        }

        if (config.getDouble("config-version") == 1.1d) {
            logger.info("Switching locale versions from: " + config.getDouble("config-version") + " to: 1.2");
            config.set("config-version", 1.2d);
            config.set("bstats_enabled", true);

            config.options().header("Comments were partially lost whilst updating the config. You can grab the 1.2.0 default \nConfig from the Spigot Resource page.");
            logger.info("Config has been updated. Comments were lost in the process due to Spigot limitations.");
        }

        this.setConfig(config);
    }

    public void updateLocale(FileConfiguration locale) {

        Logger logger = autoBroadcast.getLogger();

        // No changes in 1.0

        if (locale.getDouble("locale-version") <= 1.1d) {
            logger.info("Switching locale versions from: " + locale.getDouble("locale-version") + " to: 1.2");

            locale.set("locale-version", 1.2d);
            locale.set("autobroadcast.reload_command", "%autobroadcast_chatprefix% &7AutoBroadcast has been reloaded.");

            // Adding a line to the help content is hard.
            List<String> tempList = locale.getStringList("autobroadcast.help_content");
            tempList.add("&7To &creload&7 the configuration run /&cautobroadcast reload");
            locale.set("autobroadcast.help_content", tempList);

            locale.options().header("# Comments were partially lost whilst updating the locale. You can grab the 1.2.0 default \nLocale from the Spigot Resource page.");
            logger.info("Locale has been updated. Comments were lost in the process due to Spigot limitations.");
        }
        this.setLocale(locale);
    }
}
