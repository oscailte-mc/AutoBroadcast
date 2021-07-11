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

    @Override
    public void onEnable() {

        // First Run
        this.saveDefaultConfig();
        saveResource("locale.yml", false);

        // Pretty Ascii Art
        LocaleLogger.info("system.autobroadcast.startup");

        // Ensures the plugin is up-to-date with the version listed on spigot
        versionChecker();

        // config/locale cache definitions
        new ConfigurationUtilities(this);
        ConfigurationUtilities.getInstance().update(); // Make sure configuration files are up-to-date

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
                LocaleLogger.info("system.autobroadcast.update.unavailable");
            } else {
                LocaleLogger.info("system.autobroadcast.update.available");
            }
        });
    }

}
