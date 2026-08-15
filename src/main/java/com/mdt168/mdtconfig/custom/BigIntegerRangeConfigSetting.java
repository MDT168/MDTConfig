package com.mdt168.mdtconfig.custom;

import com.mdt168.mdtconfig.ConfigSetting;
import com.mdt168.mdtconfig.ConfigType;
import com.mdt168.mdtconfig.datatypes.ConfigDataTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigInteger;
import java.util.function.BiFunction;

public class BigIntegerRangeConfigSetting extends ConfigSetting<BigInteger> {
    private final BigInteger min, max;
    public BigIntegerRangeConfigSetting(@NotNull String name, @NotNull String comment, @NotNull BigInteger min, @NotNull BigInteger max, @NotNull BigInteger defaultValue, @NotNull ConfigType configType) {
        this(name, comment, min, max, defaultValue, configType, null);
    }

    public BigIntegerRangeConfigSetting(@NotNull String name, @NotNull String comment, @NotNull BigInteger min, @NotNull BigInteger max, @NotNull BigInteger defaultValue, @NotNull ConfigType configType, @Nullable BiFunction<BigInteger, ConfigSetting<BigInteger>, BigInteger> sanitizer) {
        super(name, comment, ConfigDataTypes.BIG_INTEGER, defaultValue, configType, sanitizer);
        if (min.compareTo(max) > 0) {
            illegal("Illegal Range: Min Value (" + min + ") is larger than max value (" + max + ")");
        }
        this.min = min;
        this.max = max;
    }

    @Override
    public void validateDefaultValue(@NotNull BigInteger defaultValue) {
        if (defaultValue.compareTo(min) < 0 || defaultValue.compareTo(max) > 0) {
            illegalDefault("Not in range of [" + min + ", " + max + "]");
        }
    }

    @Override
    public @NotNull BigInteger modifyValue(BigInteger originalValue) {
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
