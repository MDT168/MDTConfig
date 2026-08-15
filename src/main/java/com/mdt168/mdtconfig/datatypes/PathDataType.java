package com.mdt168.mdtconfig.datatypes;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;

public class PathDataType implements ConfigDataType<Path> {
    PathDataType() {}

    @Override
    public @NotNull Object serialize(@NotNull Path value) {
        return value.toString();
    }

    @Override
    public @Nullable Path deserialize(@NotNull Object object) {
        try {
            return Path.of(String.valueOf(object));
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public @NotNull String getName() {
        return "File Path";
    }
}
