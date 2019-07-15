package co.paradaux.hibernia.AutoBroadcast.api;

import co.paradaux.hibernia.AutoBroadcast.AutoBroadcast;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;

public class BroadcastManager {

    public static BukkitTask timer;
    public static Plugin plugin = AutoBroadcast.getPlugin();
    private static int delay = plugin.getConfig().getInt("interval") * 20;
    private static List<String> Announcements = plugin.getConfig().getStringList("announcements");
    public static int currentPlace;



    public static BukkitTask createTimer() {

        currentPlace = 0;

        return Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> {
            Bukkit.getScheduler().runTask(plugin, () -> {
                for(Player p : plugin.getServer().getOnlinePlayers()) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', Announcements.get(currentPlace)));
                }
                currentPlace = currentPlace + 1;
                if (currentPlace == Announcements.size()) {
                    currentPlace = 0;
                }
            });
        }, 60, delay);
    }

    public static void initialize() {

        timer = createTimer();

    }

}
