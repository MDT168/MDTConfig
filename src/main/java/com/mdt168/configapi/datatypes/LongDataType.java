package com.mdt168.configapi.datatypes;

import com.mdt168.configapi.ConfigSetting;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LongDataType implements ConfigDataType<Long> {
    LongDataType() {}

    @Override
    public @NotNull Object serialize(@NotNull Long value) {
        return value;
    }

    @Override
    public @Nullable Long deserialize(@NotNull Object object) {
        if (object instanceof Long l) return l;
        try {
            return Long.valueOf(String.valueOf(object));
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public @NotNull String getName() {
        return "Long Whole Numbers";
    }
}
