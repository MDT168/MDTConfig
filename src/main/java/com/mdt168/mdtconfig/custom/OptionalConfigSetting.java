package com.mdt168.mdtconfig.custom;

import com.mdt168.mdtconfig.ConfigSetting;
import com.mdt168.mdtconfig.ConfigType;
import com.mdt168.mdtconfig.datatypes.ConfigDataType;
import com.mdt168.mdtconfig.datatypes.ConfigDataTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class OptionalConfigSetting<T> extends ConfigSetting<Optional<T>> {
    public OptionalConfigSetting(@NotNull String name, @NotNull String comment, @NotNull ConfigDataType<T> type, @Nullable T defaultValue, @NotNull ConfigType configType) {
        super(name, comment, ConfigDataTypes.optionalOf(type), Optional.ofNullable(defaultValue), configType);
    }

    public OptionalConfigSetting(@NotNull String name, @NotNull String comment, @NotNull ConfigDataType<T> type, @Nullable T defaultValue, @NotNull ConfigType configType, @Nullable BiFunction<Optional<T>, ConfigSetting<Optional<T>>, Optional<T>> sanitizer) {
        super(name, comment, ConfigDataTypes.optionalOf(type), Optional.ofNullable(defaultValue), configType, sanitizer);
    }

    @Override
    public String getSerializedDefaultValue() {
        String s = super.getSerializedDefaultValue();
        if (s == null || s.isBlank()) return "Not set";
        return s;
    }

    public @Nullable T get() {
        return getValue().orElse(null);
    }

    public @Nullable T getIfPresentOrDefault() {
        return getValue().orElse(getDefaultValue().orElse(null));
    }

    public void ifPresent(Consumer<T> action) {
        getValue().ifPresent(action);
    }
}
