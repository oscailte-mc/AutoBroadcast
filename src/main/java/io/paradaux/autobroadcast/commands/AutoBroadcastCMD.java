/*
 * Copyright © 2020 Property of Rían Errity Licensed under GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007. See <LICENSE.md>
 */

package io.paradaux.autobroadcast.commands;

import io.paradaux.autobroadcast.config.ConfigurationUtilities;
import io.paradaux.autobroadcast.locale.LocaleManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class AutoBroadcastCMD implements CommandExecutor {

    public static String colourise(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (args.length <= 0) {
            sender.sendMessage(colourise(LocaleManager.get("command.autobroadcast.content")));
            return true;
        }

        if ("reload".equals(args[0])) {
            if (!sender.hasPermission("autobroadcast.reload")) {
                return true;
            }

            ConfigurationUtilities.getInstance().reload();
            sender.sendMessage(colourise(LocaleManager.get("command.autobroadcast.reload.content")));
            return true;
        }

        sender.sendMessage(colourise(LocaleManager.get("command.autobroadcast.content")));
        return true;
    }
}
