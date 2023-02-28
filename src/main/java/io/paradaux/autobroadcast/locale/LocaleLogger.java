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

import org.slf4j.Logger;

/**
 * A wrapper on top of SLF4J which allows for the logging of system messages using locale entries to provide easier internationalisation.
 * @author Rían Errity
 * @since 2.0.0
 * */
public class LocaleLogger {

    private static Logger logger;
    private static boolean debug;

    public LocaleLogger(Logger logger, boolean debug) {
        LocaleLogger.logger = logger;
        LocaleLogger.debug = debug;
    }

    public static void toggleDebug() {
        debug = !debug;
    }

    public static void info(String str, String... args) {
        logger.info(LocaleManager.get(str), (Object[]) args);
    }

    public static void warn(String str, String... args) {
        logger.warn(LocaleManager.get(str), (Object[]) args);
    }

    public static void debug(String str, String... args) {
        if (debug) {
            logger.debug(LocaleManager.get(str), (Object[]) args);
        }
    }

    public static void error(String str, String... args) {
        logger.error(str, (Object[]) args);
    }
}
