/*
 * Copyright © 2020 Property of Rían Errity Licensed under GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007. See <LICENSE.md>
 */

package io.paradaux.autobroadcast.hooks;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class PlaceholderAPIWrapper {

    public boolean isPresent() {
        return Bukkit.getServer().getPluginManager().getPlugin("PlaceholderAPI") != null;
    }

    public String withPlaceholders(Player player, String input) {
        return PlaceholderAPI.setPlaceholders(player, input);
    }
    public String withPlaceholders(OfflinePlayer player, String input) {
        return PlaceholderAPI.setPlaceholders(player, input);
    }
}
