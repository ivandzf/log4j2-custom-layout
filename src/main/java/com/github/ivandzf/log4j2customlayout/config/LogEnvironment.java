package com.github.ivandzf.log4j2customlayout.config;

import lombok.NonNull;

/**
 * log4j2-custom-layout
 *
 * @author Divananda Zikry Fadilla (08 October 2018)
 * Email: divanandazf@gmail.com
 * <p>
 * Documentation here !!
 */
public final class LogEnvironment {

    private static String IP_ADDRESS;
    private static String PORT;
    private static String APPLICATION_NAME;

    public static String getIpAddress() {
        return IP_ADDRESS;
    }

    public static String getPort() {
        return PORT;
    }

    public static String getApplicationName() {
        return APPLICATION_NAME;
    }

    public static boolean isIpAddress() {
        return IP_ADDRESS != null;
    }

    public static boolean isPort() {
        return PORT != null;
    }

    public static boolean isApplicationName() {
        return APPLICATION_NAME != null;
    }

    public static class Builder {
        public static void build(@NonNull String ipAddress, @NonNull String port, @NonNull String applicationName) {
            IP_ADDRESS = ipAddress;
            PORT = port;
            APPLICATION_NAME = applicationName;
        }
    }

}
