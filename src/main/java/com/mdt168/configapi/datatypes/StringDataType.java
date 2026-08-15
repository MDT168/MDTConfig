package com.mdt168.configapi.datatypes;

import com.mdt168.configapi.ConfigSetting;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StringDataType implements ConfigDataType<String> {
    StringDataType() {}


    @Override
    public @NotNull Object serialize(@NotNull String value) {
        return value;
    }

    @Override
    public @Nullable String deserialize(@NotNull Object object) {
        if (object instanceof String s) return s;
        return String.valueOf(object);
    }

    @Override
    public @NotNull String getName() {
        return "Text";
    }
}
