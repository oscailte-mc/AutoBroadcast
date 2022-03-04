/*
 * Copyright (c) 2021, Rían Errity. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 3 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 3 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 3 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Rían Errity <rian@paradaux.io> or visit https://paradaux.io
 * if you need additional information or have any questions.
 * See LICENSE.md for more details.
 */

package io.paradaux.autobroadcast.adventure;

import io.paradaux.autobroadcast.AutoBroadcast;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.IOException;

/**
 * AdventureImpl is a wrapper around the kyori/adventure Framework for Minecraft Components.
 * @author Rían Errity
 * @since 2.0.0
 * */
public class AdventureImpl implements AutoCloseable {

    private static AdventureImpl instance;

    /**
     * Singleton field containing the MiniMessage parser in use.
     * */
    private static MiniMessage miniMessage;

    /**
     * Singleton access to the adventure implementation.
     * */
    public static AdventureImpl getInstance() {

        return instance;
    }

    /**
     * The Adventure API
     * */
    private BukkitAudiences adventure;

    /**
     * Initialise the Adventure Implementation using the Bukkit Platform.
     * */
    public AdventureImpl(AutoBroadcast plugin) {
        this.adventure = BukkitAudiences.create(plugin);
        miniMessage = MiniMessage.miniMessage();

        AdventureImpl.instance = this;
    }

    /**
     * Sends a chat component to the specified {@link Player}, interpreting the provided String as a MiniMessage-serialised Adventure Component.
     * @param player The player you wish to send the component to
     * @param message A MiniMessage-serialised Adventure component.
     * */
    public void sendMiniMessage(Player player, String message) {
        adventure().player(player).sendMessage(miniMessage.deserialize(message));
    }

    /**
     * Sends a chat component to the specified {@link CommandSender}, interpreting the provided String as a MiniMessage-serialised Adventure Component.
     * @param sender The sender you wish to send the component to
     * @param message A MiniMessage-serialised Adventure component.
     * */
    public void sendMiniMessage(CommandSender sender, String message) {
        adventure().sender(sender).sendMessage(miniMessage.deserialize(message));
    }

    /**
     * Returns the BukkitAudience Instance.
     * @return BukkitAudiences
     * @throws IllegalStateException Thrown when the audience is invalid.
     * */
    @NonNull
    public BukkitAudiences adventure() {
        if(this.adventure == null) {
            throw new IllegalStateException("Adventure is null / closed");
        }
        return this.adventure;
    }

    /**
     * Disables the Adventure integration, preventing further use internally and by dependencies.
     * @throws IOException caused by closure.
     * */
    @Override
    public void close() throws IOException {
        if(this.adventure != null) {
            this.adventure.close();
            this.adventure = null;
        }
    }
}
