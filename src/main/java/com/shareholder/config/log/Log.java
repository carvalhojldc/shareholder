package com.shareholder.config.log;

import com.shareholder.config.SystemInstall;

import java.io.IOException;
import java.util.logging.*;

public class Log {

    protected Log() {
    }

    public static void configure() {
        Logger logger = Logger.getLogger(Class.class.getName());

        logger.setUseParentHandlers(false);
        ConsoleHandler handler = new ConsoleHandler();
        Formatter lf = new LogFormatter();
        handler.setFormatter(lf);
        logger.addHandler(handler);

        try {
            FileHandler fh = new FileHandler(SystemInstall.getPath() + "event.log");
            logger.addHandler(fh);
            SimpleFormatter sf = new SimpleFormatter();
            fh.setFormatter(sf);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Logger getLogger(Class c) {
        return Logger.getLogger(c.getName());
    }
}



