package com.mdt168.mdtconfig.datatypes;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ShortDataType implements ConfigDataType<Short> {
    ShortDataType() {}

    @Override
    public @NotNull Object serialize(@NotNull Short value) {
        return value;
    }

    @Override
    public @Nullable Short deserialize(@NotNull Object object) {
        if (object instanceof Short s) return s;
        try {
            return Short.valueOf(String.valueOf(object));
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public @NotNull String getName() {
        return "Short Whole Number";
    }
}
