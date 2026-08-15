package com.mdt168.mdtconfig.custom;

import com.mdt168.mdtconfig.ConfigSetting;
import com.mdt168.mdtconfig.ConfigType;
import com.mdt168.mdtconfig.datatypes.ConfigDataTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiFunction;

public class IntRangeConfigSetting extends ConfigSetting<Integer> {
    private final int min, max;
    public IntRangeConfigSetting(@NotNull String name, @NotNull String comment, int min, int max, @NotNull Integer defaultValue, @NotNull ConfigType configType) {
        this(name, comment, min, max, defaultValue, configType, null);
    }

    public IntRangeConfigSetting(@NotNull String name, @NotNull String comment, int min, int max, @NotNull Integer defaultValue, @NotNull ConfigType configType, @Nullable BiFunction<Integer, ConfigSetting<Integer>, Integer> sanitizer) {
        super(name, comment, ConfigDataTypes.INTEGER, defaultValue, configType, sanitizer);
        if (min > max) {
            illegal("Illegal Range: Min Value (" + min + ") is larger than max value (" + max + ")");
        }
        this.min = min;
        this.max = max;
    }

    @Override
    public void validateDefaultValue(@NotNull Integer defaultValue) {
        if (defaultValue > max || defaultValue < min) {
            illegalDefault("Not within range [" + min + ", " + max + "]");
        }
    }

    @Override
    public @NotNull Integer modifyValue(Integer originalValue) {
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
