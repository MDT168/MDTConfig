package com.mdt168.configapi.resources;

public class Helper {
    /**
     * Normalizes a string into a clean, valid YAML key.
     * @param name The original string (e.g., "NPC Location")
     * @return The normalized key (e.g., "npc-location")
     */
    public static String toYamlKey(String name) {
        if (name == null || name.trim().isEmpty()) {
            return "";
        }

        return name
                .trim()
                .toLowerCase()
                .replaceAll("['\"]", "")
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("^-+|-+$", "");
    }
}
