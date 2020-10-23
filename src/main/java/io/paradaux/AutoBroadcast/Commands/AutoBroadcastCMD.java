/*
 * Copyright © 2020 Property of Rían Errity Licensed under GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007. See <LICENSE.md>
 */

package io.paradaux.AutoBroadcast.Commands;

import io.paradaux.AutoBroadcast.API.LocaleCache;
import io.paradaux.AutoBroadcast.AutoBroadcast;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class AutoBroadcastCMD implements CommandExecutor {

    AutoBroadcast autoBroadcast;

    public AutoBroadcastCMD(AutoBroadcast autoBroadcast) {
        this.autoBroadcast = autoBroadcast;
    }

    LocaleCache locale = AutoBroadcast.getLocaleCache();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(locale.getAutoBroadcastHelpHeader());
            sender.sendMessage(locale.getAutoBroadcastHelpContent());
            return true;
        }

        switch (args[0]) {
            case "reload": {
                autoBroadcast.reloadConfiguration();
                sender.sendMessage(locale.getAutoBroadcastReloadCommand());
                return true;
            }
        }

        return true;

    }
}
