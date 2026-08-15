package com.mdt168.configapi.custom;

import com.mdt168.configapi.ConfigSetting;
import com.mdt168.configapi.ConfigType;
import com.mdt168.configapi.datatypes.ConfigDataTypes;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.function.BiFunction;

public class AutoRoundDecimalConfigSetting extends ConfigSetting<Double> {
    private final double scale;
    public AutoRoundDecimalConfigSetting(String name, String comment, int decimalPlaces, Double defaultValue, ConfigType configType) {
        super(name, comment, ConfigDataTypes.DOUBLE, defaultValue, configType);
        this.scale = Math.pow(10, Math.max(decimalPlaces, 0));
    }

    public AutoRoundDecimalConfigSetting(String name, String comment, int decimalPlaces, Double defaultValue, ConfigType configType, @Nullable BiFunction<Double, ConfigSetting<Double>, Double> sanitizer) {
        super(name, comment, ConfigDataTypes.DOUBLE, defaultValue, configType, sanitizer);
        this.scale = Math.pow(10, Math.max(decimalPlaces, 0));
    }

    @Override
    public @NotNull Double modifyValue(Double originalValue) {
        return Math.round(originalValue * scale) / scale;
    }
}
