package io.paradaux.autobroadcast.api;

import org.slf4j.Logger;

public class LocaleLogger {

    private static Logger logger;

    public LocaleLogger(Logger logger) {
        LocaleLogger.logger = logger;
    }

    public static void info(String str, String... args) {
        logger.info(LocaleManager.get(str), (Object[]) args);
    }

    public static void warn(String str, String... args) {
        logger.warn(LocaleManager.get(str), (Object[]) args);
    }

    public static void debug(String str, String... args) {
        logger.debug(LocaleManager.get(str), (Object[]) args);
    }

    public static void error(String str, String... args) {
        logger.error(str, (Object[]) args);
    }
}
