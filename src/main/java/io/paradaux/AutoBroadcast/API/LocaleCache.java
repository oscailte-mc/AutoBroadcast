package io.paradaux.AutoBroadcast.API;

import io.paradaux.AutoBroadcast.AutoBroadcast;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;


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
        return colorise(locale.getString(path).replace("%autobroadcast_chatprefix%", getChatPrefix()));
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

    public double getLocaleVersion() {
        return localeVersion;
    }

    public void setLocaleVersion(double localeVersion) {
        this.localeVersion = localeVersion;
    }

    public String getChatPrefix() {
        return chatPrefix;
    }

    public void setChatPrefix(String chatPrefix) {
        this.chatPrefix = chatPrefix;
    }

    public String getAutoBroadcastHelpHeader() {
        return autoBroadcastHelpHeader;
    }

    public void setAutoBroadcastHelpHeader(String autoBroadcastHelpHeader) {
        this.autoBroadcastHelpHeader = autoBroadcastHelpHeader;
    }

    public String getAutoBroadcastHelpContent() {
        return autoBroadcastHelpContent;
    }

    public void setAutoBroadcastHelpContent(String autoBroadcastHelpContent) {
        this.autoBroadcastHelpContent = autoBroadcastHelpContent;
    }
}
