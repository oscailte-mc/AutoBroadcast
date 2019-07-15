package co.paradaux.hibernia.AutoBroadcast;

import co.paradaux.hibernia.AutoBroadcast.api.BroadcastManager;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class AutoBroadcast extends JavaPlugin {

    private static Plugin plugin;
    public static Plugin getPlugin() { return plugin; }


    @Override
    public void onEnable() {
        plugin = this;

        this.getConfig().options().copyDefaults();
        saveDefaultConfig();

        BroadcastManager.initialize();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
