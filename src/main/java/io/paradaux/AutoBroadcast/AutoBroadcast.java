/*
 * Copyright © 2020 Property of Rían Errity Licensed under GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007. See <LICENSE.md>
 */

package io.paradaux.AutoBroadcast;

import io.paradaux.AutoBroadcast.API.*;
import io.paradaux.AutoBroadcast.Commands.AutoBroadcastCMD;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class AutoBroadcast extends JavaPlugin {

    private static Logger logger;
    public static Logger getPluginLogger() { return logger; }

    private static Plugin plugin;
    public static Plugin getPlugin() { return plugin; }

    private static ConfigurationCache ConfigurationCache;
    public static ConfigurationCache getConfigurationCache() { return ConfigurationCache; }

    private static BukkitTask broadcastChain;
    public static BukkitTask getBroadcastChain() { return broadcastChain; }

    private static File localeFile;
    private static YamlConfiguration locale;
    private static LocaleCache localeCache;

    public static YamlConfiguration getLocale() { return locale; }
    public static File getLocaleFile() { return localeFile; }
    public static LocaleCache getLocaleCache() { return localeCache; }

    @Override
    public void onEnable() {
        logger = this.getLogger();
        plugin = this;

        configHandler();
        startupMessage();
        localeHandler();
        registerCommands();
        beginBroadcasts();
    }

    @Override
    public void onDisable() {}


    public void beginBroadcasts() {
        broadcastChain = BroadcastManager.createTimer();
    }

    public void startupMessage() {
        logger.log(Level.FINE, "\n" +
                "+ ------------------------------------ +\n" +
                "|     Running AutoBroadcast v1.1.1     |\n" +
                "|       © Rían Errity (Paradaux)       |\n" +
                "|         https://paradaux.io          |\n" +
                "+ ------------------------------------ +\n" +
                "\n" +
                "Are you looking for a freelance plugin developer?\n" +
                "Think no further than Paradaux.io! rian@paradaux.io / Rían#6500"
        );
    }

    public void configHandler() {
        this.getConfig().options().copyDefaults();
        saveDefaultConfig();
        saveResource("locale.yml", false);
        ConfigurationCache = new ConfigurationCache();
    }

    public void localeHandler() {
        localeFile = new File(Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("AutoBroadcast")).getDataFolder(), "locale.yml");
        locale = LocaleManager.initiateDataFile();
        localeCache = new LocaleCache();

    }

    public void registerCommands() {
        Objects.requireNonNull(this.getCommand("autobroadcast")).setExecutor(new AutoBroadcastCMD());
    }

    public void versionChecker() {
        new VersionChecker(this, 69377).getVersion(version -> {
            if (this.getDescription().getVersion().equalsIgnoreCase(version)) {
                logger.info("There are no new updates available");
            } else {
                logger.info("There is a new update available. \n Please update: https://www.spigotmc.org/resources/autobroadcast.69377/");
            }
        });
    }
}