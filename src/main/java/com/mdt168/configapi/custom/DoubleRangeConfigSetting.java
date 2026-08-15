package com.mdt168.configapi.custom;

import com.mdt168.configapi.ConfigSetting;
import com.mdt168.configapi.ConfigType;
import com.mdt168.configapi.datatypes.ConfigDataTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiFunction;

public class DoubleRangeConfigSetting extends ConfigSetting<Double> {
    private final double min, max;
    public DoubleRangeConfigSetting(@NotNull String name, @NotNull String comment, double min, double max, @NotNull Double defaultValue, @NotNull ConfigType configType) {
        this(name, comment, min, max, defaultValue, configType, null);
    }

    public DoubleRangeConfigSetting(@NotNull String name, @NotNull String comment, double min, double max, @NotNull Double defaultValue, @NotNull ConfigType configType, @Nullable BiFunction<Double, ConfigSetting<Double>, Double> sanitizer) {
        super(name, comment, ConfigDataTypes.DOUBLE, defaultValue, configType, sanitizer);
        if (min > max) {
            illegal("Illegal Range: Min Value (" + min + ") is larger than max value (" + max + ")");
        }
        this.min = min;
        this.max = max;
    }

    @Override
    public void validateDefaultValue(@NotNull Double defaultValue) {
        if (defaultValue > max || defaultValue < min) {
            illegalDefault("Not within range [" + min + ", " + max + "]");
        }
    }

    @Override
    public @NotNull Double modifyValue(Double originalValue) {
        if (originalValue > max || originalValue < min) {
            warn("Value is not within range: " + originalValue + ". Using default value: " + getDefaultValue());
            return getDefaultValue();
        }
        return originalValue;
    }

    @Override
    public @NotNull String modifyComment(String originalComment) {
        return originalComment + "\nRange: " + min + " to " + max;
    }
}
