/*
 * Copyright © 2020 Property of Rían Errity Licensed under GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007. See <LICENSE.md>
 */

package io.paradaux.autobroadcast;

import io.paradaux.autobroadcast.api.*;
import io.paradaux.autobroadcast.commands.AutoBroadcastCMD;
import org.bstats.bukkit.Metrics;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.logging.Logger;

public final class AutoBroadcast extends JavaPlugin {

    /* Lazy Dependency Injection */

    // Manages everything to do with the repeating broadcasts.


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

        // First Run
        this.saveDefaultConfig();
        saveResource("locale.yml", false);

        // Pretty Ascii Art
        startupMessage();

        // Ensures the plugin is up-to-date with the version listed on spigot
        versionChecker();

        // config/locale cache definitions
        configurationUtilities = new ConfigurationUtilities(this);
        configurationUtilities.update(); // Make sure configuration files are up-to-date

        configurationCache = new ConfigurationCache(this, configurationUtilities.getConfig());
        localeCache = new LocaleCache(configurationUtilities.getLocale());

        // Actual Broadcasting Mechanism

        new BroadcastManager(this, configurationCache);

        // Provides anonymous usage statistics
        registerBstats();

        // Register Commands
        registerCommands();
    }

    @Override
    public void onDisable() {
        BroadcastManager.getInstance().cancel();
    }

    public void startupMessage() {
        getLogger().info( "\n" +
                "+ ------------------------------------ +\n" +
                "|     Running AutoBroadcast v2.0.0     |\n" +
                "|       © Rían Errity (Paradaux)       |\n" +
                "|         https://paradaux.io          |\n" +
                "+ ------------------------------------ +\n" +
                "\n" +
                "Are you looking for a freelance plugin developer?\n" +
                "Think no further than Paradaux.io! rian@paradaux.io / Rían#6500\n"
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
