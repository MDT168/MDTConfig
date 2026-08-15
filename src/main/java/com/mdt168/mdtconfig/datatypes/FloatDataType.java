package com.mdt168.mdtconfig.datatypes;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FloatDataType implements ConfigDataType<Float> {
    FloatDataType() {}

    @Override
    public @NotNull Object serialize(@NotNull Float value) {
        return value;
    }

    @Override
    public @Nullable Float deserialize(@NotNull Object object) {
        if (object instanceof Float f) return f;
        try {
            return Float.valueOf(String.valueOf(object));
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public @NotNull String getName() {
        return "Decimal Number";
    }
}
