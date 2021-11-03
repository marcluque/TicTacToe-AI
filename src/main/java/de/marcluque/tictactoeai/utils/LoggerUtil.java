package de.marcluque.tictactoeai.utils;

import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/*
 * Created with <3 by marcluque, November 2021
 */
public final class LoggerUtil {

    private LoggerUtil() {}

    public static Logger createLogger(String loggerName, String format, Class<?> clazz) {
        Logger mainLogger = Logger.getLogger(loggerName);
        mainLogger.setUseParentHandlers(false);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(new SimpleFormatter() {
            @Override
            public String format(LogRecord lr) {
                return String.format(format,
                        new Date(lr.getMillis()),
                        lr.getMessage()
                );
            }
        });
        mainLogger.addHandler(handler);

        return Logger.getLogger(clazz.getName());
    }
}