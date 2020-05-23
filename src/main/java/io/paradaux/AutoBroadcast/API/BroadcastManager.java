package io.paradaux.AutoBroadcast.API;

import io.paradaux.AutoBroadcast.AutoBroadcast;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;

public class BroadcastManager {

    public static BukkitTask timer;
    public static Plugin plugin = AutoBroadcast.getPlugin();
    private static int delay = AutoBroadcast.getConfigurationCache().getInterval() * 20;
    private static List<String> Announcements = AutoBroadcast.getConfigurationCache().getAnnouncements();
    public static int currentPlace;

    public static String colorise(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static BukkitTask createTimer() {
        currentPlace = 0;

        return Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> {
            Bukkit.getScheduler().runTask(plugin, () -> {
                for(Player p : plugin.getServer().getOnlinePlayers()) {
                    if (AutoBroadcast.getConfigurationCache().isUseBypassPermission()) {
                        if (!p.hasPermission("autobroadcast.bypass")) {
                            p.sendMessage(colorise(Announcements.get(currentPlace)));
                        }
                    } else {
                        p.sendMessage(colorise(Announcements.get(currentPlace)));
                    }
                }

                currentPlace++;
                if (currentPlace == Announcements.size()) { currentPlace = 0; }
            });
        }, 60, delay);
    }

}
