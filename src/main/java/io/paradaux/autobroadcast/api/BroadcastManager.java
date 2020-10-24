package io.paradaux.autobroadcast.api;

import io.paradaux.autobroadcast.AutoBroadcast;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;

public class BroadcastManager {

    PlaceholderAPIWrapper placeholderAPIWrapper;
    ConfigurationCache configurationCache;
    List<String> announcements;
    AutoBroadcast autoBroadcast;
    BukkitTask task;
    int currentPlace;

    public BroadcastManager(AutoBroadcast autoBroadcast, ConfigurationCache configurationCache) {
        this.autoBroadcast = autoBroadcast;
        this.configurationCache = configurationCache;
        announcements = configurationCache.getAnnouncements();
        placeholderAPIWrapper = new PlaceholderAPIWrapper();

        task = createTaskTimer();
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
                if (configurationCache.enableBypassPermission) {
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
        }), 60, configurationCache.getInterval() * 20);
    }

    public void nextAnnouncement() {
        currentPlace++;

        if (currentPlace >= announcements.size()) { currentPlace = 0; }
    }

    public void cancel() {
        task.cancel();
    }
}
