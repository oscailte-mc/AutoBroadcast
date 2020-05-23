package io.paradaux.AutoBroadcast.API;

import io.paradaux.AutoBroadcast.AutoBroadcast;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class LocaleManager {

    private static File localeFile;

    public static YamlConfiguration initiateDataFile() {
        return YamlConfiguration.loadConfiguration(AutoBroadcast.getLocaleFile());
    }

}
