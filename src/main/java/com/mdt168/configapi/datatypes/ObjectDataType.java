package com.mdt168.configapi.datatypes;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ObjectDataType implements ConfigDataType<Object>{
    ObjectDataType() {}

    @Override
    public @NotNull Object serialize(@NotNull Object value) {
        return value;
    }

    @Override
    public @Nullable Object deserialize(@NotNull Object object) {
        return object;
    }

    @Override
    public @NotNull String getName() {
        return "Any Type";
    }
}
