package com.projectlibre1.cookie;

import java.net.CookieHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CookieUtils {
    private static final Logger LOGGER = Logger.getLogger(CookieUtils.class.getName());

    public static void disableCookieHandler() {
        try {
            CookieHandler.setDefault(null);
        } catch (SecurityException e) {
            LOGGER.log(Level.SEVERE, "Erro ao desabilitar cookie", e);
        }
    }

}
