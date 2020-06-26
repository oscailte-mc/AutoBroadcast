/*
 * Copyright © 2020 Property of Rían Errity Licensed under GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007. See <LICENSE.md>
 */

package io.paradaux.AutoBroadcast.API;

import io.paradaux.AutoBroadcast.AutoBroadcast;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.Objects;


public class LocaleCache {
    YamlConfiguration locale = AutoBroadcast.getLocale();

    double localeVersion;
    String chatPrefix;

    String autoBroadcastHelpHeader;
    String autoBroadcastHelpContent;

    public String colorise(String args) {
        return ChatColor.translateAlternateColorCodes('&', args);
    }

    public String getString(String path) {
        return colorise(Objects.requireNonNull(locale.getString(path)).replace("%autobroadcast_chatprefix%", getChatPrefix()));
    }

    public String getStringList(String path) {
        return colorise(String.join("\n", locale.getStringList(path)).replace("%autobroadcast_chatprefix%", getChatPrefix()));
    }

    public LocaleCache() {
        this.localeVersion = locale.getDouble("locale-version");
        this.chatPrefix = locale.getString("chat-prefix");
        this.autoBroadcastHelpHeader = this.getString("autobroadcast.help_header");
        this.autoBroadcastHelpContent = this.getStringList("autobroadcast.help_content");
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


}
