/*
 * Copyright © 2020 Property of Rían Errity Licensed under GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007. See <LICENSE.md>
 */

package io.paradaux.autobroadcast.api;

import io.paradaux.autobroadcast.AutoBroadcast;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.Objects;


public class LocaleCache {

    double localeVersion;
    String chatPrefix;

    String reloadCommand;
    String helpHeader;
    List<String> helpContent;

    public LocaleCache(double localeVersion, String chatPrefix, String reloadCommand, String helpHeader, List<String> helpContent) {
        this.localeVersion = localeVersion;
        this.chatPrefix = chatPrefix;
        this.reloadCommand = reloadCommand;
        this.helpHeader = helpHeader;
        this.helpContent = helpContent;
    }

    public LocaleCache(YamlConfiguration locale) {
        this.localeVersion = locale.getDouble("locale-version");
        this.chatPrefix = locale.getString("chat-prefix");
        this.reloadCommand = locale.getString("autobroadcast.reload_command");
        this.helpHeader = locale.getString("autobroadcast.help_header");
        this.helpContent = locale.getStringList("autobroadcast.help_content");
    }

    public double getLocaleVersion() {
        return localeVersion;
    }

    public String getChatPrefix() {
        return chatPrefix;
    }

    public String getReloadCommand() {
        return reloadCommand.replace("%autobroadcast_chatprefix%", getChatPrefix());
    }

    public String getHelpHeader() {
        return helpHeader.replace("%autobroadcast_chatprefix%", getChatPrefix());
    }

    public String getHelpContent() {
        return String.join("\n", helpContent).replace("%autobroadcast_chatprefix%", getChatPrefix());
    }
}
