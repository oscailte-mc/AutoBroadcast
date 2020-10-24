package io.paradaux.autobroadcast.api;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class PlaceholderAPIWrapper {
    public PlaceholderAPIWrapper() {}
    public void cancel() {}

    public boolean isPresent() { return Bukkit.getServer().getPluginManager().getPlugin("PlaceholderAPI") != null; }

    public String withPlaceholders(Player player, String input) { return PlaceholderAPI.setPlaceholders(player, input); }
    public String withPlaceholders(OfflinePlayer player, String input) { return PlaceholderAPI.setPlaceholders(player, input); }

}
