/*
 * Copyright (c) 2023, Rían Errity. All rights reserved.
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

package io.paradaux.autobroadcast.locale;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * Utility to loading and holding singleton instances of the ResourceBundle which serves as the locale/internationalisation system for the
 * plugin.
 * @author Rían Errity
 * @since 2.0.0
 * */
public class LocaleManager {

    private static final File locale = new File(Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("AutoBroadcast")).getDataFolder(), "locale.properties");

    private static ResourceBundle bundle;

    public LocaleManager(Plugin p) {
        if (!locale.exists()) {
            p.saveResource("AutoBroadcast_en_US.properties", false);
            new File(Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("AutoBroadcast"))
                    .getDataFolder(), "AutoBroadcast_en_US.properties").renameTo(locale);
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
