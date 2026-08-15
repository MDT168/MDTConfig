package com.mdt168.mdtconfig.resources;

import com.mdt168.mdtconfig.ConfigSetting;
import com.mdt168.mdtconfig.datatypes.ConfigDataType;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

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
        if (rawValue != null) {
            T deserializedRaw = getType().deserialize(rawValue);
            if (!Objects.equals(deserializedRaw, getDefaultValue())) {
                setting.warn("The map key '" + getKey() + "' is read-only. The value that was changed in the config is ignored.");
            }
        }
        return getDefaultValue();
    }
}
