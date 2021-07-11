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

package io.paradaux.autobroadcast.commands;

import io.paradaux.autobroadcast.adventure.AdventureImpl;
import io.paradaux.autobroadcast.config.ConfigurationUtilities;
import io.paradaux.autobroadcast.locale.LocaleManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class AutoBroadcastCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (args.length <= 0) {
            AdventureImpl.getInstance().sendMiniMessage(sender, LocaleManager.get("command.autobroadcast.content"));
            return true;
        }

        if ("reload".equals(args[0])) {
            if (!sender.hasPermission("autobroadcast.reload")) {
                return true;
            }
            ConfigurationUtilities.getInstance().reload();
            AdventureImpl.getInstance().sendMiniMessage(sender, LocaleManager.get("command.autobroadcast.reload.content"));
            return true;
        }

        AdventureImpl.getInstance().sendMiniMessage(sender, LocaleManager.get("command.autobroadcast.content"));
        return true;
    }
}
