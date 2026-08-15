package com.mdt168.mdtconfig.custom;

import com.mdt168.mdtconfig.ConfigSetting;
import com.mdt168.mdtconfig.ConfigType;
import com.mdt168.mdtconfig.datatypes.ConfigDataType;
import com.mdt168.mdtconfig.datatypes.ConfigDataTypes;
import com.mdt168.mdtconfig.resources.ConfigMapEntry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

public class MapConfigSetting extends ConfigSetting<Map<String, Object>> {
    private final Map<String, ConfigMapEntry<?>> entries;

    public MapConfigSetting(@NotNull String name, @NotNull String comment, @NotNull List<@NotNull ConfigMapEntry<?>> schema, @NotNull ConfigType configType, @Nullable BiFunction<Map<String, Object>, ConfigSetting<Map<String, Object>>, Map<String, Object>> sanitizer) {
        this(name, comment, toEntriesMap(schema), configType, sanitizer);
    }

    private MapConfigSetting(@NotNull String name, @NotNull String comment, @NotNull Map<String, ConfigMapEntry<?>> entriesMap, @NotNull ConfigType configType, @Nullable BiFunction<Map<String, Object>, ConfigSetting<Map<String, Object>>, Map<String, Object>> sanitizer) {
        super(name, comment, ConfigDataTypes.mapOf(entriesMap), toDataMap(entriesMap), configType, sanitizer);
        this.entries = entriesMap;
    }
    public MapConfigSetting(@NotNull String name, @NotNull String comment, @NotNull List<@NotNull ConfigMapEntry<?>> schema, @NotNull ConfigType configType) {
        this(name, comment, toEntriesMap(schema), configType, null);
    }

    /**
     * Looks up a value in the map. It may never be null if the ConfigDataType in the schema disallow nulls
     * @param key The key of the Map Entry
     * @return The value from the config
     * @throws IllegalArgumentException if the key doesn't exist
     */
    public Object getValue(String key) {
        Map<String, Object> map = getValue();
        if (!map.containsKey(key)) {
            illegal("Unknown Map Key '" + key + "': Not found");
        }
        return map.get(key);
    }

    @Override
    public @Unmodifiable Map<String, Object> getValue() {
        return Collections.unmodifiableMap(super.getValue());
    }

    /**
     * Looks up a value in the map and casts it to the specified type.
     *
     * <p>The value may be {@code null} if the corresponding
     * {@link ConfigDataType} in the schema allows null values.
     *
     * @param key The key of the map entry
     * @param type The class to cast the value to
     * @param <T> The expected type of the value
     * @return The value from the config, cast to the specified type
     * @throws IllegalArgumentException if the key doesn't exist
     * @throws ClassCastException if the value cannot be cast to the specified type
     */
    public <T> T getValue(String key, Class<T> type) {
        return type.cast(getValue(key));
    }

    private Map<String, Object> getMap() {
        return super.getValue();
    }

    public <T> void addEntry(@NotNull ConfigMapEntry<T> entry) {
        setValue(entry, entry.getDefaultValue());
    }

    public void setValue(@NotNull String key, Object value) {
        ConfigMapEntry<?> entry = entries.get(key);
        Map<String, Object> map = getMap();

        if (entry == null) {
            map.put(key, value);
        } else {
            Object finalValue = entry.getFinalValue(value, this);
            map.put(key, finalValue);
        }
        setValue(map);
    }

    public <T> void setValue(@NotNull ConfigMapEntry<T> entry, T value) {
        entries.put(entry.getKey(), entry);
        Object finalValue = entry.getFinalValue(value, this);
        getMap().put(entry.getKey(), finalValue);
        setValue(getMap());
    }

    public @NotNull Object getDefaultValue(String key) {
        if (!entries.containsKey(key)) {
            illegal("Unknown Map Key '" + key + "': Not found");
        }
        return entries.get(key).getDefaultValue();
    }

    @Override
    public @NotNull Map<String, Object> modifyValue(Map<String, Object> original) {
        Map<String, Object> originalValue = new LinkedHashMap<>(original);
        for (Map.Entry<String, ConfigMapEntry<?>> entry : entries.entrySet()) {
            originalValue.putIfAbsent(entry.getKey(), entry.getValue().getDefaultValue());
        }
        for (Map.Entry<String, Object> entry : originalValue.entrySet()) {
            ConfigMapEntry<?> configEntry = entries.get(entry.getKey());
            if (configEntry == null) {
                originalValue.put(entry.getKey(), entry.getValue());
                continue;
            }
            entry.setValue(configEntry.getFinalValue(entry.getValue(), this));
        }
        return originalValue;
    }

    private static Map<String, ConfigMapEntry<?>> toEntriesMap(List<ConfigMapEntry<?>> schema) {
        Map<String, ConfigMapEntry<?>> map = new LinkedHashMap<>();
        for (ConfigMapEntry<?> entry : schema) {
            map.put(entry.getKey(), entry);
        }
        return map;
    }

    private static Map<String, Object> toDataMap(Map<String, ConfigMapEntry<?>> entriesMap) {
        Map<String, Object> map = new LinkedHashMap<>();
        for (Map.Entry<String, ConfigMapEntry<?>> entry : entriesMap.entrySet()) {
            map.put(entry.getKey(), entry.getValue().getDefaultValue());
        }
        return map;
    }
}
