package com.mdt168.configapi.datatypes;

import com.mdt168.configapi.ConfigSetting;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EnumDataType<T extends Enum<T>> implements ConfigDataType<T> {
    private final Class<T> enumClass;

    public EnumDataType(Class<T> enumClass) {
        this.enumClass = enumClass;
    }

    @Override
    public @NotNull Object serialize(@NotNull T value) {
        return value.name();
    }

    @Override
    public @Nullable T deserialize(@NotNull Object object) {
        try {
            return Enum.valueOf(enumClass, String.valueOf(object));
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public @NotNull String getName() {
        return "Options";
    }
}
