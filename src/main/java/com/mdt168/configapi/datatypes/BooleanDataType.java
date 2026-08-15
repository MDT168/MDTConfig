package com.mdt168.configapi.datatypes;

import com.mdt168.configapi.ConfigSetting;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BooleanDataType implements ConfigDataType<Boolean> {
    BooleanDataType() {}

    @Override
    public @NotNull Object serialize(@NotNull Boolean value) {
        return value;
    }

    @Override
    public @Nullable Boolean deserialize(@NotNull Object object) {
        if (object instanceof Boolean b) return b;
        String val = String.valueOf(object).trim();
        if (val.equalsIgnoreCase("true")) return true;
        if (val.equalsIgnoreCase("false")) return false;
        return null;
    }

    @Override
    public @NotNull String getName() {
        return "true/false";
    }
}
