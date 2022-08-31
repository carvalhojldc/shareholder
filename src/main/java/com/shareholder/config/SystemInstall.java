package com.shareholder.config;

import com.shareholder.config.log.Log;

import java.io.File;
import java.util.logging.Logger;

import static java.lang.System.exit;

public class SystemInstall {
    private static final Logger log = Log.getLogger(Class.class);

    public SystemInstall() {
    }

    public static String getPath() {
        final String OS = System.getProperty("os.name").toLowerCase();
        final String PROGRAM_NAME = ProgramDef.getProgramName();
        if ((OS.indexOf("win") >= 0)) {
            return "C:\\ProgramData\\" + PROGRAM_NAME + "\\";
        } else {
            return "~/.local/" + PROGRAM_NAME + "/";
        }
    }

    public static boolean verifyIfPathExists(String path) {
        return new File(path).exists();
    }

    public static void createSystemPath(String path) {
        log.finest("Creating program path:" + path);
        try {
            File theDir = new File(path);
            theDir.mkdirs();
        } catch (Exception ex) {
            log.severe(ex.getMessage());
            exit(2);
        }
        log.finest("Path created");
    }
}
