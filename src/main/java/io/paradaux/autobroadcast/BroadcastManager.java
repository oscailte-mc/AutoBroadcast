/*
 * Copyright © 2020 Property of Rían Errity Licensed under GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007. See <LICENSE.md>
 */

package io.paradaux.autobroadcast;

import io.paradaux.autobroadcast.config.ConfigurationCache;
import io.paradaux.autobroadcast.hooks.PlaceholderAPIWrapper;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import java.util.List;

public class BroadcastManager {

    private static BroadcastManager instance;
    public static BroadcastManager getInstance() { return instance; }

    private final PlaceholderAPIWrapper placeholderAPIWrapper;
    private final ConfigurationCache config;
    private final List<String> announcements;
    private final AutoBroadcast autoBroadcast;
    private final BukkitTask task;
    private int currentPlace;

    public BroadcastManager(AutoBroadcast autoBroadcast) {
        this.autoBroadcast = autoBroadcast;
        this.config = ConfigurationCache.getInstance();
        announcements = config.getAnnouncements();
        placeholderAPIWrapper = new PlaceholderAPIWrapper();

        task = createTaskTimer();
        instance = this;
    }

    public static String colourise(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
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
                    player.sendMessage(colourise(announcements.get(currentPlace)));
                    nextAnnouncement();
                    return;
                }

                // Parse PlaceholderAPI Placeholders and colorise the template.
                String message = placeholderAPIWrapper.withPlaceholders(player, colourise(announcements.get(currentPlace)));
                player.sendMessage(message);
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