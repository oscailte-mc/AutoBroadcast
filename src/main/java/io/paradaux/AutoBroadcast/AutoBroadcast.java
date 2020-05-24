package io.paradaux.AutoBroadcast;

import io.paradaux.AutoBroadcast.API.BroadcastManager;
import io.paradaux.AutoBroadcast.API.ConfigurationCache;
import io.paradaux.AutoBroadcast.API.LocaleCache;
import io.paradaux.AutoBroadcast.API.LocaleManager;
import io.paradaux.AutoBroadcast.Commands.AutoBroadcastCMD;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;

public final class AutoBroadcast extends JavaPlugin {

    private static Plugin plugin;
    public static Plugin getPlugin() { return plugin; }

    private static ConfigurationCache ConfigurationCache;
    public static ConfigurationCache getConfigurationCache() { return ConfigurationCache; }

    private static BukkitTask broadcastChain;
    public static BukkitTask getBroadcastChain() { return broadcastChain; }

    private static File localeFile;
    private static YamlConfiguration locale;
    private static LocaleCache localeCache;

    public static YamlConfiguration getLocale() { return locale; }
    public static File getLocaleFile() { return localeFile; }
    public static LocaleCache getLocaleCache() { return localeCache; }

    @Override
    public void onEnable() {
        this.getConfig().options().copyDefaults();
        saveDefaultConfig();
        saveResource("locale.yml", false);

        plugin = this;
        ConfigurationCache = new ConfigurationCache();
        localeFile = new File(Bukkit.getServer().getPluginManager().getPlugin("AutoBroadcast").getDataFolder(), "locale.yml");
        locale = LocaleManager.initiateDataFile();
        localeCache = new LocaleCache();

        System.out.println("\n" +
                "+ ------------------------------------ +\n" +
                "|     Running AutoBroadcast v1.1.0     |\n" +
                "|       © Rían Errity (Paradaux)       |\n" +
                "|         https://paradaux.io          |\n" +
                "+ ------------------------------------ +\n" +
                "\n" +
                "Are you looking for a freelance plugin developer?\n" +
                "Think no further than Paradaux.io! rian@paradaux.io / Rían#6500"
        );

        this.getCommand("autobroadcast").setExecutor(new AutoBroadcastCMD());

        broadcastChain = BroadcastManager.createTimer();
    }

    @Override
    public void onDisable() {}
}
