package com.mdt168.configapi.resources;

import com.mdt168.configapi.ConfigSetting;
import com.mdt168.configapi.datatypes.ConfigDataType;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ConfigMapEntry<T> {
    private final ConfigDataType<T> type;
    private final String key;
    private final T defaultValue;

    /**
     * Creates a simple entry for MapConfigSetting
     * @param key The key that will be used to access the value
     * @param type The type of the entry
     * @param defaultValue The fallback if the config value is not valid. SHOULD never be null if the data type doesn't allow nulls
     */
    public ConfigMapEntry(@NotNull String key, @NotNull ConfigDataType<T> type, T defaultValue) {
        if (!type.shouldAllowNulls()) Objects.requireNonNull(defaultValue, "defaultValue for Map Entry '" + key + "' can't be null");
        this.type = type;
        this.key = Helper.toYamlKey(key);
        this.defaultValue = defaultValue;
    }

    public String getKey() {
        return key;
    }

    public T getDefaultValue() {
        return defaultValue;
    }

    public T getFinalValue(Object rawValue, ConfigSetting<?> setting) {
        if (rawValue == null && !type.shouldAllowNulls()) {
            setting.warn("Invalid value found in the Map (Can't be null): " + getKey());
            return getDefaultValue();
        }
        T value = type.deserialize(rawValue);
        if (value == null) {
            setting.warn("Invalid value found in the Map: " + rawValue + ". Expected: '" + getType().getName() + "'. Using value: " + getDefaultValue());
            return getDefaultValue();
        }
        return value;
    }

    public ConfigDataType<T> getType() {
        return type;
    }
}
