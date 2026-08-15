package com.mdt168.mdtconfig;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;

public class ConfigType {
    private final String extraPath, prefix;

    public ConfigType(@NotNull String extraPath, @Nullable String prefix) {
        this.extraPath = extraPath;
        if (prefix == null) {
            this.prefix = "";
            return;
        }
        StringBuilder p = new StringBuilder();
        String[] parts = prefix.split("\n");
        for (String part : parts) {
            p.append("# ").append(part).append('\n');
        }
        p.append('\n');
        this.prefix = p.toString();
    }

    public ConfigType(@NotNull String extraPath) {
        this(extraPath, null);
    }

    public String getExtraPath() {
        return extraPath;
    }

    public String getPrefix() {
        return prefix;
    }

    public Path getPath(Path dataFolder) {
        return dataFolder.resolve("configs/" + extraPath);
    }
}