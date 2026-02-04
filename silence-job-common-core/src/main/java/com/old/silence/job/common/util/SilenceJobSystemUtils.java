package com.old.silence.job.common.util;

public final class SilenceJobSystemUtils {

    private SilenceJobSystemUtils() {
        throw new AssertionError();
    }

    public static String getOsName() {
        return System.getProperty("os.name");
    }

    public static boolean isOsWindows() {
        String osName = getOsName();
        return osName != null && osName.toLowerCase().contains("windows");
    }
}
