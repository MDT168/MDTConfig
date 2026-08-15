package com.mdt168.configapi.datatypes;

import com.mdt168.configapi.ConfigSetting;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DoubleDataType implements ConfigDataType<Double> {
    DoubleDataType() {}

    @Override
    public @NotNull Object serialize(@NotNull Double value) {
        return value;
    }

    @Override
    public @Nullable Double deserialize(@NotNull Object object) {
        if (object instanceof Double d) return d;
        try {
            return Double.valueOf(String.valueOf(object));
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public @NotNull String getName() {
        return "Decimal Number";
    }
}
