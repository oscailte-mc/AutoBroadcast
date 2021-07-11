/*
 * Copyright © 2020 Property of Rían Errity Licensed under GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007. See <LICENSE.md>
 */

package io.paradaux.autobroadcast;

import io.paradaux.autobroadcast.api.BroadcastManager;
import io.paradaux.autobroadcast.api.ConfigurationCache;
import io.paradaux.autobroadcast.api.ConfigurationUtilities;
import io.paradaux.autobroadcast.api.LocaleLogger;
import io.paradaux.autobroadcast.api.VersionChecker;
import io.paradaux.autobroadcast.commands.AutoBroadcastCMD;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public final class AutoBroadcast extends JavaPlugin {

    private static Metrics metrics;

    @Override
    public void onEnable() {

        // First Run
        this.saveDefaultConfig();

        LocaleLogger localeLogger = new LocaleLogger(LoggerFactory.getLogger("AutoBroadcast"));

        // Pretty Ascii Art
        LocaleLogger.info("system.autobroadcast.startup");

        // Ensures the plugin is up-to-date with the version listed on spigot
        versionChecker();

        // config/locale cache definitions
        new ConfigurationUtilities(this);
        ConfigurationUtilities.getInstance().update(); // Make sure configuration files are up-to-date
        ConfigurationCache.builder().build(ConfigurationUtilities.getInstance().getConfig());

        // Actual Broadcasting Mechanism
        new BroadcastManager(this);

        // Provides anonymous usage statistics
        registerBstats();

        // Register Commands
        registerCommands();
    }

    @Override
    public void onDisable() {
        if (BroadcastManager.getInstance() != null) {
            BroadcastManager.getInstance().cancel();
        }
    }

    public void registerCommands() {
        Objects.requireNonNull(this.getCommand("autobroadcast")).setExecutor(new AutoBroadcastCMD());
    }

    public void registerBstats() {
        if (!ConfigurationCache.getInstance().isBstatsEnabled()) {
            return;
        }

        metrics = new Metrics(this, 9185);
    }

    public void versionChecker() {
        new VersionChecker(this, 69377).getVersion(version -> {
            if (this.getDescription().getVersion().equalsIgnoreCase(version)) {
                LocaleLogger.info("system.autobroadcast.update.unavailable");
            } else {
                LocaleLogger.info("system.autobroadcast.update.available");
            }
        });
    }

}
