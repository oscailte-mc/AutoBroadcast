/*
 * Copyright © 2020 Property of Rían Errity Licensed under GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007. See <LICENSE.md>
 */

package io.paradaux.autobroadcast;

import co.aikar.taskchain.BukkitTaskChainFactory;
import co.aikar.taskchain.TaskChain;
import co.aikar.taskchain.TaskChainFactory;
import io.paradaux.autobroadcast.api.*;
import io.paradaux.autobroadcast.commands.AutoBroadcastCMD;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class AutoBroadcast extends JavaPlugin {

    /* Lazy Dependency Injection */

    // Aikar's TaskChain Implementation
    private static TaskChainFactory taskChainFactory;
    public static <T> TaskChain<T> newChain() { return taskChainFactory.newChain(); }

    // Manages everything to do with the repeating broadcasts.
    private static BroadcastManager broadcastManager;
    public static BroadcastManager getBroadcastManager() { return broadcastManager; }
    public void setBroadcastManager(BroadcastManager broadcast) { broadcastManager = broadcast; }

    // Handles config.yml/locale.yml
    private static ConfigurationUtilities configurationUtilities;
    public static ConfigurationUtilities getConfigurationUtilities() { return configurationUtilities; }

    // config.yml cache
    private static ConfigurationCache configurationCache;
    public static ConfigurationCache getConfigurationCache() { return configurationCache; }
    public void setConfigurationCache(ConfigurationCache configuration) { configurationCache = configuration; }

    // locale.yml cache
    private static LocaleCache localeCache;
    public static LocaleCache getLocaleCache() { return localeCache; }
    public void setLocaleCache(LocaleCache locale) { localeCache = locale; }



    @Override
    public void onEnable() {

        // Initialise Taskchain
        taskChainFactory = BukkitTaskChainFactory.create(this);

        // First Run
        this.saveDefaultConfig();
        saveResource("locale.yml", false);

        // Pretty Ascii Art
        startupMessage();

        // Ensure's the plugin is up-to-date with the version listed on spigot
        versionChecker();

        // config/locale cache definitions
        configurationUtilities = new ConfigurationUtilities(this);
        configurationUtilities.update(); // Make sure configuration files are up-to-date

        configurationCache = new ConfigurationCache(this, configurationUtilities.getConfig());
        localeCache = new LocaleCache(configurationUtilities.getLocale());

        // Actual Broadcasting Mechanism

        broadcastManager = new BroadcastManager(this, configurationCache);

        // Provides anonymous usage statistics
        registerBstats();

        // Register Commands
        registerCommands();
    }

    @Override
    public void onDisable() {
        broadcastManager.cancel();
    }



    public void startupMessage() {
        getLogger().info( "\n" +
                "+ ------------------------------------ +\n" +
                "|     Running AutoBroadcast v1.2.0     |\n" +
                "|       © Rían Errity (Paradaux)       |\n" +
                "|         https://paradaux.io          |\n" +
                "+ ------------------------------------ +\n" +
                "\n" +
                "Are you looking for a freelance plugin developer?\n" +
                "Think no further than Paradaux.io! rian@paradaux.io / Rían#6500"
        );
    }

    public void registerCommands() {
        Objects.requireNonNull(this.getCommand("autobroadcast")).setExecutor(new AutoBroadcastCMD(this));
    }

    public void registerBstats() {
        if (!configurationCache.isBstatsEnabled()) return;
        Metrics metrics = new Metrics(this, 9185);
    }

    public void versionChecker() {
        Logger logger = this.getLogger();

        new VersionChecker(this, 69377).getVersion(version -> {
            if (this.getDescription().getVersion().equalsIgnoreCase(version)) {
                logger.info("There are no new updates available");
            } else {
                logger.info("There is a new update available. \n Please update: https://www.spigotmc.org/resources/autobroadcast.69377/");
            }
        });
    }

}
