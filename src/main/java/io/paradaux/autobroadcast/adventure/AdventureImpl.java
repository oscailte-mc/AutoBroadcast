package io.paradaux.autobroadcast.adventure;


import io.paradaux.autobroadcast.AutoBroadcast;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.markdown.DiscordFlavor;
import net.kyori.adventure.text.minimessage.transformation.TransformationType;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.IOException;

public class AdventureImpl implements AutoCloseable {

    private static AdventureImpl instance;
    private static MiniMessage miniMessage;

    public static AdventureImpl getInstance() {
        return instance;
    }

    public static MiniMessage getMiniMessage() {
        return miniMessage;
    }

    private BukkitAudiences adventure;

    public AdventureImpl(AutoBroadcast plugin) {
        this.adventure = BukkitAudiences.create(plugin);
        miniMessage = MiniMessage.get();

        AdventureImpl.instance = this;
    }

    /**
     * Send a MiniMessage
     * */
    public void sendMiniMessage(Player player, String message) {
        adventure().player(player).sendMessage(miniMessage.parse(message));
    }
    @NonNull
    public BukkitAudiences adventure() {
        if(this.adventure == null) {
            throw new IllegalStateException("Adventure is null / closed");
        }
        return this.adventure;
    }

    @Override
    public void close() throws IOException {
        if(this.adventure != null) {
            this.adventure.close();
            this.adventure = null;
        }
    }
}
