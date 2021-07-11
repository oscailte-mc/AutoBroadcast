package io.paradaux.autobroadcast.locale;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class LocaleManager {

    private static final File locale = new File(Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("AutoBroadcast")).getDataFolder(), "locale.properties");

    private static ResourceBundle bundle;

    public LocaleManager(Plugin p) {
        if (!locale.exists()) {
            p.saveResource("AutoBroadcast_en_US.properties", false);
            new File(Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("AutoBroadcast")).getDataFolder(), "AutoBroadcast_en_US.properties").renameTo(locale);
        }

        try (FileInputStream fis = new FileInputStream(locale)) {
            bundle = new PropertyResourceBundle(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String get(String key) {
        if (!bundle.containsKey(key)) {
            return key;
        }

        return bundle.getString(key);
    }
}
