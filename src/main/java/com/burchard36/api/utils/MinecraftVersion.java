package com.burchard36.api.utils;

import org.bukkit.Bukkit;

public enum MinecraftVersion {
    UNKNOWN(Integer.MAX_VALUE), // Use the newest known mappings
    MC1_7_R4(174), MC1_8_R3(183), MC1_9_R1(191), MC1_9_R2(192), MC1_10_R1(1101), MC1_11_R1(1111), MC1_12_R1(1121),
    MC1_13_R1(1131), MC1_13_R2(1132), MC1_14_R1(1141), MC1_15_R1(1151), MC1_16_R1(1161), MC1_16_R2(1162), MC1_16_R3(1163), MC1_17_R1(1171), MC1_18_R1(1181, true), MC1_18_R2(1182, true);

    private static MinecraftVersion version;

    private final int versionId;
    private final boolean mojangMapping;

    MinecraftVersion(int versionId) {
        this(versionId, false);
    }

    MinecraftVersion(int versionId, boolean mojangMapping) {
        this.versionId = versionId;
        this.mojangMapping = mojangMapping;
    }

    /**
     * @return A simple comparable Integer, representing the version.
     */
    public int getVersionId() {
        return versionId;
    }

    /**
     * @return True if method names are in Mojang format and need to be remapped internally
     */
    public boolean isMojangMapping() {
        return mojangMapping;
    }

    /**
     * This method is required to hot-wire the plugin during mappings generation for newer mc versions thanks to md_5 not used mojmap.
     */
    public String getPackageName() {
        if(this == UNKNOWN) {
            return Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        }
        return this.name().replace("MC", "v");
    }

    /**
     * Returns true if the current versions is at least the given Version
     * @param version The minimum version
     */
    public static boolean isAtLeastVersion(MinecraftVersion version) {
        return getVersion().getVersionId() >= version.getVersionId();
    }

    /**
     * Returns true if the current versions newer (not equal) than the given version
     * @param version The minimum version
     */
    public static boolean isNewerThan(MinecraftVersion version) {
        return getVersion().getVersionId() > version.getVersionId();
    }

    /**
     * Getter for this servers MinecraftVersion. Also init's bStats and checks the
     * shading.
     *
     * @return The enum for the MinecraftVersion this server is running
     */
    public static MinecraftVersion getVersion() {
        if (version != null) {
            return version;
        }
        final String ver = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        Logger.log("[NBTAPI] Found Spigot: " + ver + "! Trying to find NMS support");
        try {
            version = MinecraftVersion.valueOf(ver.replace("v", "MC"));
        } catch (IllegalArgumentException ex) {
            version = MinecraftVersion.UNKNOWN;
        }
        if (version != UNKNOWN) {
            Logger.log("[NBTAPI] NMS support '" + version.name() + "' loaded!");
        } else {
            Logger.warn("[NBTAPI] This Server-Version(" + ver + ") is not supported " + MinecraftVersion.class.getName() + ". The NBT-API will try to work as good as it can! Some functions may not work!");
        }
        return version;
    }
}
