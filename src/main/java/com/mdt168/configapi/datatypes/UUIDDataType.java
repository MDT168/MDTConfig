package com.mdt168.configapi.datatypes;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class UUIDDataType implements ConfigDataType<UUID> {
    UUIDDataType() {}

    @Override
    public @NotNull Object serialize(@NotNull UUID value) {
        return value.toString();
    }

    @Override
    public @Nullable UUID deserialize(@NotNull Object object) {
        if (object instanceof UUID u) return u;
        try {
            return UUID.fromString(String.valueOf(object));
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public @NotNull String getName() {
        return "Unique ID";
    }
}
