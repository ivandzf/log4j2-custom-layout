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

    private static String ipAddress;
    private static String port;
    private static String applicationName;

    public static String getIpAddress() {
        return ipAddress;
    }

    public static String getPort() {
        return port;
    }

    public static String getApplicationName() {
        return applicationName;
    }

    public static boolean hasIpAddress() {
        return ipAddress != null;
    }

    public static boolean hasPort() {
        return port != null;
    }

    public static boolean hasApplicationName() {
        return applicationName != null;
    }

    public static class Builder {
        public static void build(@NonNull String ipAddress, @NonNull String port, @NonNull String applicationName) {
            LogEnvironment.ipAddress = ipAddress;
            LogEnvironment.port = port;
            LogEnvironment.applicationName = applicationName;
        }
    }

}
