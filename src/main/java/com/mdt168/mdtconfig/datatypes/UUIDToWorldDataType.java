package com.mdt168.mdtconfig.datatypes;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class UUIDToWorldDataType implements ConfigDataType<World> {
    UUIDToWorldDataType() {}


    @Override
    public @NotNull Object serialize(@NotNull World value) {
        return String.valueOf(value.getUID());
    }

    @Override
    public @Nullable World deserialize(@NotNull Object object) {
        try {
            return Bukkit.getWorld(UUID.fromString(String.valueOf(object)));
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public @NotNull String getName() {
        return "World Unique ID";
    }
}
