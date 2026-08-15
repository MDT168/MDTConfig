package com.mdt168.mdtconfig.datatypes;

import com.mdt168.mdtconfig.ConfigSetting;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class OptionalDataType<T> implements ConfigDataType<Optional<T>> {
    private final ConfigDataType<T> dataType;

    public OptionalDataType(@NotNull ConfigDataType<T> dataType) {
        this.dataType = dataType;
    }

    @Override
    public @NotNull Object serialize(@NotNull Optional<T> value) {
        return value.map(dataType::serialize).orElse("");
    }

    @Override
    public @NotNull Optional<T> deserialize(@Nullable Object object, @NotNull ConfigSetting<Optional<T>> caller) {
        if (object == null || (object instanceof String s && s.isBlank())) return Optional.empty();
        return Optional.ofNullable(dataType.deserialize(object));
    }

    @Override
    public @NotNull Optional<T> deserialize(@Nullable Object object) {
        if (object == null) return Optional.empty();
        return Optional.ofNullable(dataType.deserialize(object));
    }

    @Override
    public boolean shouldAllowNulls() {
        return true;
    }

    @Override
    public @NotNull String getName() {
        return dataType.getName() + " (Optional)";
    }
}
