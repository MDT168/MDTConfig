package com.mdt168.configapi.datatypes;

import com.mdt168.configapi.ConfigSetting;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class IntegerDataType implements ConfigDataType<Integer> {
    IntegerDataType() {}

    @Override
    public @NotNull Object serialize(@NotNull Integer value) {
        return value;
    }

    @Override
    public @Nullable Integer deserialize(@NotNull Object object) {
        if (object instanceof Integer i) return i;
        try {
            return Integer.valueOf(String.valueOf(object));
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public @NotNull String getName() {
        return "Whole Number";
    }
}
