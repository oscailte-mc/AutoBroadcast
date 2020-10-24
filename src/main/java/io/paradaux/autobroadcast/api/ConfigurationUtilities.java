package io.paradaux.autobroadcast.api;

import io.paradaux.autobroadcast.AutoBroadcast;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class ConfigurationUtilities {

    AutoBroadcast autoBroadcast;
    File configFile;
    File localeFile;

    public ConfigurationUtilities(AutoBroadcast autoBroadcast) {
        this.autoBroadcast = autoBroadcast;
        configFile = new File
    }

    public YamlConfiguration getLocale() {
        return YamlConfiguration.loadConfiguration()
    }

    public void updateConfiguration() {}

    public void updateLocale() {}



}
