package com.mdt168.configapi;

import com.mdt168.configapi.datatypes.ConfigDataType;
import com.mdt168.configapi.resources.ConfigSettingUnsupportedOperationException;
import org.jetbrains.annotations.NotNull;

// A read-only config setting
// The value is displayed in the config and always uses the default value
public class FixedConfigSetting<T> extends ConfigSetting<T> {
    public FixedConfigSetting(@NotNull String name, @NotNull String comment, @NotNull ConfigDataType<T> type, @NotNull T value, @NotNull ConfigType configType) {
        super(name, comment, type, value, configType);
    }

    @Override
    public final @NotNull T modifyValue(T originalValue) {
        return originalValue;
    }

    @Override
    public final T getValue() {
        return getDefaultValue();
    }

    @Override
    protected final ConfigSetting.Values<T> getRawValue() {
        return new Values<>(getType().serialize(getDefaultValue()), getDefaultValue());
    }

    /**
     * Always throws ConfigSettingUnsupportedOperationException
     */
    @Override
    public final void setValue(T value) {
        throw new ConfigSettingUnsupportedOperationException("Can't use setValue() for fixed config settings.");
    }
}
