/*
 * Copyright (c) 2021, Rían Errity. All rights reserved.
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

package io.paradaux.autobroadcast;

import io.paradaux.autobroadcast.adventure.AdventureImpl;
import io.paradaux.autobroadcast.commands.AutoBroadcastCMD;
import io.paradaux.autobroadcast.config.ConfigurationCache;
import io.paradaux.autobroadcast.config.ConfigurationUtilities;
import io.paradaux.autobroadcast.hooks.VersionChecker;
import io.paradaux.autobroadcast.locale.LocaleLogger;
import io.paradaux.autobroadcast.locale.LocaleManager;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Objects;

/**
 * AutoBroadcast by Rían Errity, a simple MiniMessage-supporting auto-broadcasting system for Minecraft Spigot Servers, using the adventure
 * framework for Minecraft 1.17.X
 * @author Rían Errity
 * @since 2.0.0
 * */
public final class AutoBroadcast extends JavaPlugin {

    /**
     * bStats Metrics
     * */
    private static Metrics metrics;

    @Override
    public void onEnable() {

        // First Run
        this.saveDefaultConfig();

        new LocaleManager(this);

        LocaleLogger localeLogger = new LocaleLogger(LoggerFactory.getLogger("AutoBroadcast"));

        // Pretty Ascii Art
        LocaleLogger.info("system.autobroadcast.startup");

        // Ensures the plugin is up-to-date with the version listed on spigot
        versionChecker();

        // config/locale cache definitions
        new ConfigurationUtilities(this);
        ConfigurationUtilities.getInstance().update(this.getConfig()); // Make sure configuration files are up-to-date
        ConfigurationCache.builder().build(ConfigurationUtilities.getInstance().getConfig());

        // Register Adventure
        new AdventureImpl(this);

        // Actual Broadcasting Mechanism
        new BroadcastManager(this);

        // Provides anonymous usage statistics
        registerBStats();

        // Register Commands
        registerCommands();
    }

    @Override
    public void onDisable() {
        if (BroadcastManager.getInstance() != null) {
            BroadcastManager.getInstance().setCancelled(true);
            try {
                AdventureImpl.getInstance().close();
            } catch (IOException e) {
                LocaleLogger.error("system.error.generic", e.toString());
            }
        }
    }

    public void registerCommands() {
        Objects.requireNonNull(this.getCommand("autobroadcast")).setExecutor(new AutoBroadcastCMD());
    }

    public void registerBStats() {
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
