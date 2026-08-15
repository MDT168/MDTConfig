package com.mdt168.configapi.custom;

import com.mdt168.configapi.ConfigSetting;
import com.mdt168.configapi.ConfigType;
import com.mdt168.configapi.datatypes.ConfigDataTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.util.function.BiFunction;

public class BigDecimalRangeConfigSetting extends ConfigSetting<BigDecimal> {
    private final BigDecimal min, max;
    public BigDecimalRangeConfigSetting(@NotNull String name, @NotNull String comment, @NotNull BigDecimal min, @NotNull BigDecimal max, @NotNull BigDecimal defaultValue, @NotNull ConfigType configType) {
        this(name, comment, min, max, defaultValue, configType, null);
    }

    public BigDecimalRangeConfigSetting(@NotNull String name, @NotNull String comment, @NotNull BigDecimal min, @NotNull BigDecimal max, @NotNull BigDecimal defaultValue, @NotNull ConfigType configType, @Nullable BiFunction<BigDecimal, ConfigSetting<BigDecimal>, BigDecimal> sanitizer) {
        super(name, comment, ConfigDataTypes.BIG_DECIMAL, defaultValue, configType, sanitizer);
        if (min.compareTo(max) > 0) {
            illegal("Illegal Range: Min Value (" + min + ") is larger than max value (" + max + ")");
        }
        this.min = min;
        this.max = max;
    }

    @Override
    public void validateDefaultValue(@NotNull BigDecimal defaultValue) {
        if (defaultValue.compareTo(min) < 0 || defaultValue.compareTo(max) > 0) {
            illegalDefault("Not in range of [" + min + ", " + max + "]");
        }
    }

    @Override
    public @NotNull BigDecimal modifyValue(BigDecimal originalValue) {
        if (originalValue.compareTo(min) < 0 || originalValue.compareTo(max) > 0) {
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
