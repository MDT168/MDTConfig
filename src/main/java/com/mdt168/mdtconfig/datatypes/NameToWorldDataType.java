package com.mdt168.mdtconfig.datatypes;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NameToWorldDataType implements ConfigDataType<World> {
    NameToWorldDataType() {}

    @Override
    public @NotNull Object serialize(@NotNull World value) {
        return value.getName();
    }

    @Override
    public @Nullable World deserialize(@NotNull Object object) {
        try {
            return Bukkit.getWorld(String.valueOf(object));
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public @NotNull String getName() {
        return "World Name";
    }
}
