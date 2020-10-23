/*
 * Copyright © 2020 Property of Rían Errity Licensed under GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007. See <LICENSE.md>
 */

package io.paradaux.AutoBroadcast.API;

import io.paradaux.AutoBroadcast.AutoBroadcast;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.util.Objects;


public class LocaleCache {
    YamlConfiguration locale = AutoBroadcast.getLocale();

    double localeVersion;
    String chatPrefix;

    String autoBroadcastHelpHeader;
    String autoBroadcastHelpContent;
    String autoBroadcastReloadCommand;

    public String colorise(String args) {
        return ChatColor.translateAlternateColorCodes('&', args);
    }

    public String getString(String path) {
        return colorise(Objects.requireNonNull(locale.getString(path)).replace("%autobroadcast_chatprefix%", getChatPrefix()));
    }

    public String getStringList(String path) {
        return colorise(String.join("\n", locale.getStringList(path)).replace("%autobroadcast_chatprefix%", getChatPrefix()));
    }

    public LocaleCache(FileConfiguration config) {
        this.localeVersion = locale.getDouble("locale-version");

        if (localeVersion == 1.1) {
            Plugin p = AutoBroadcast.getPlugin();

            p.getLogger().info("You were using an older version of AutoBroadcast before this. Switching locale versions.");
            config.set("config-version", 1.2);
            config.set("autobroadcast.reload_command", "%autobroadcast_chatprefix% AutoBroadcast has been reloaded.");
            config.options().header("# Comments were partially lost whilst updating the locale. You can grab the 1.1.1 default \n # Locale from the Spigot Resource page.");
            p.getLogger().info("Locale has been updated. Comments were lost in the process.");
            p.saveConfig();
        }

        this.chatPrefix = locale.getString("chat-prefix");
        this.autoBroadcastHelpHeader = this.getString("autobroadcast.help_header");
        this.autoBroadcastHelpContent = this.getStringList("autobroadcast.help_content");
        this.autoBroadcastReloadCommand = this.getString("autobroadcast.reload_command");

    }

    public String getChatPrefix() {
        return chatPrefix;
    }

    public String getAutoBroadcastHelpHeader() {
        return autoBroadcastHelpHeader;
    }

    public String getAutoBroadcastHelpContent() {
        return autoBroadcastHelpContent;
    }

    public String getAutoBroadcastReloadCommand() {
        return autoBroadcastReloadCommand;
    }
}
