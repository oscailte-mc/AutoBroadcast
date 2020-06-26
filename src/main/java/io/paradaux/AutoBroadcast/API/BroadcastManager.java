/*
 * Copyright © 2020 Property of Rían Errity Licensed under GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007. See <LICENSE.md>
 */

package io.paradaux.AutoBroadcast.API;

import io.paradaux.AutoBroadcast.AutoBroadcast;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;

public class BroadcastManager {

    public static Plugin plugin = AutoBroadcast.getPlugin();
    final private static int delay = AutoBroadcast.getConfigurationCache().getInterval() * 20;
    final private static List<String> Announcements = AutoBroadcast.getConfigurationCache().getAnnouncements();
    public static int currentPlace;

    public static String colourise(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static BukkitTask createTimer() {
        currentPlace = 0;

        return Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> Bukkit.getScheduler().runTask(plugin, () -> {
            for(Player p : plugin.getServer().getOnlinePlayers()) {
                if (AutoBroadcast.getConfigurationCache().isUseBypassPermission()) {
                    if (!p.hasPermission("autobroadcast.bypass")) {
                        p.sendMessage(colourise(Announcements.get(currentPlace)));
                    }
                } else {
                    p.sendMessage(colourise(Announcements.get(currentPlace)));
                }
            }

            currentPlace++;
            if (currentPlace == Announcements.size()) { currentPlace = 0; }
        }), 60, delay);
    }

}
