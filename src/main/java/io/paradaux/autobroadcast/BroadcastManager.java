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
import io.paradaux.autobroadcast.config.ConfigurationCache;
import io.paradaux.autobroadcast.hooks.PlaceholderAPIWrapper;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;

public class BroadcastManager {

    private static BroadcastManager instance;
    public static BroadcastManager getInstance() { return instance; }

    private final AdventureImpl adventure;

    private final PlaceholderAPIWrapper placeholderAPIWrapper;
    private final ConfigurationCache config;
    private final List<String> announcements;
    private final AutoBroadcast autoBroadcast;
    private final BukkitTask task;
    private int currentPlace;

    public BroadcastManager(AutoBroadcast autoBroadcast) {
        this.autoBroadcast = autoBroadcast;
        this.config = ConfigurationCache.getInstance();
        this.announcements = config.getAnnouncements();
        this.placeholderAPIWrapper = new PlaceholderAPIWrapper();
        this.task = createTaskTimer();
        this.adventure = AdventureImpl.getInstance();

        BroadcastManager.instance = this;
    }

    public BukkitTask createTaskTimer() {
        currentPlace = 0;

        return Bukkit.getScheduler().runTaskTimerAsynchronously(autoBroadcast, () -> Bukkit.getScheduler().runTask(autoBroadcast, () -> {

            // For every online player
            for (Player player : autoBroadcast.getServer().getOnlinePlayers()) {
                // If the player is ineligible to receive announcements
                if (config.isEnableBypassPermission()) {
                    if (player.hasPermission("autobroadcast.bypass")) return;
                }

                if (!placeholderAPIWrapper.isPresent()) {
                    adventure.sendMiniMessage(player, announcements.get(currentPlace));
                    nextAnnouncement();
                    return;
                }

                // Parse PlaceholderAPI Placeholders and colorise the template.
                String message = placeholderAPIWrapper.withPlaceholders(player, announcements.get(currentPlace));
                adventure.sendMiniMessage(player, message);
            }

            // Move onto the next announcement
            nextAnnouncement();
        }), 60, config.getInterval() * 20L);
    }

    public void nextAnnouncement() {
        currentPlace++;

        if (currentPlace >= announcements.size()) { currentPlace = 0; }
    }

    public void cancel() {
        task.cancel();
    }
}
