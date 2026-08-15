package com.mdt168.configapi.resources;

import com.mdt168.configapi.ConfigSetting;
import com.mdt168.configapi.datatypes.ConfigDataType;
import org.jetbrains.annotations.NotNull;

public class FixedConfigMapEntry<T> extends ConfigMapEntry<T>{
    /**
     * Creates a simple entry for MapConfigSetting
     * If the value in the config was changed, it will be ignored and uses the provided value in the constructor
     * A read-only Map Entry
     *
     * @param key The key that will be used to access the value
     * @param type The type of the entry
     * @param value The value that will always be used. SHOULD never be null if the data type doesn't allow nulls
     */
    public FixedConfigMapEntry(@NotNull String key, @NotNull ConfigDataType<T> type, T value) {
        super(key, type, value);
    }

    @Override
    public T getFinalValue(Object rawValue, ConfigSetting<?> setting) {
        return getDefaultValue();
    }
}
