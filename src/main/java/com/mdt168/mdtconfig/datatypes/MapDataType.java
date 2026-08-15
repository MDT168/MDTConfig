package com.mdt168.mdtconfig.datatypes;

import com.mdt168.mdtconfig.ConfigSetting;
import com.mdt168.mdtconfig.resources.ConfigMapEntry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

public class MapDataType implements ConfigDataType<Map<String, Object>> {
    private final Map<String, ConfigMapEntry<?>> entriesMap;

    public MapDataType(Map<String, ConfigMapEntry<?>> dataTypeMap) {
        this.entriesMap = dataTypeMap;
    }

    public void addEntry(ConfigMapEntry<?> entry) {
        entriesMap.put(entry.getKey(), entry);
    }

    @Override
    public @NotNull Object serialize(@NotNull Map<@NotNull String, Object> value) {
        Map<String, Object> result = new LinkedHashMap<>();
        for (Map.Entry<@NotNull String, Object> entry : value.entrySet()) {
            ConfigMapEntry<?> configMapEntry = entriesMap.get(entry.getKey());
            if (configMapEntry == null) {
                result.put(entry.getKey(), entry.getValue());
                continue;
            }
            result.put(entry.getKey(), serializeEntryValue(configMapEntry, entry.getValue()));
        }
        return result;
    }

    @Override
    public @NotNull Map<String, Object> deserialize(Object object, @NotNull ConfigSetting<Map<String, Object>> caller) {
        if (!(object instanceof Map<?, ?> rawMap)) {
            return caller.warnAndUseDefault(object);
        }
        Map<String, Object> map = new LinkedHashMap<>(rawMap.size());

        for (Map.Entry<?, ?> entry : rawMap.entrySet()) {
            if (entry.getKey() == null) {
                caller.warn("Found an invalid key in the map (Can't have null). Switching to default map");
                return caller.getDefaultValue();
            }
            String key = String.valueOf(entry.getKey());
            ConfigMapEntry<?> configMapEntry = entriesMap.get(key);

            if (configMapEntry == null) {
                map.put(key, entry.getValue());
                continue;
            }

            map.put(key, configMapEntry.getFinalValue(entry.getValue(), caller));
        }
        return map;
    }

    @Override
    public @Nullable Map<String, Object> deserialize(Object object) {
        if (!(object instanceof Map<?, ?> rawMap)) {
            return null;
        }
        Map<String, Object> map = new LinkedHashMap<>(rawMap.size());

        for (Map.Entry<?, ?> entry : rawMap.entrySet()) {
            if (entry.getKey() == null) {
                return null;
            }
            String key = String.valueOf(entry.getKey());
            ConfigMapEntry<?> configMapEntry = entriesMap.get(key);

            if (configMapEntry == null) {
                map.put(key, entry.getValue());
                continue;
            }

            Object deserialized = configMapEntry.getType().deserialize(entry.getValue());
            map.put(key, deserialized != null ? deserialized : configMapEntry.getDefaultValue());
        }
        return map;
    }

    @Override
    public @NotNull String getName() {
        return "Map";
    }

    @SuppressWarnings("unchecked")
    private static <T> Object serializeEntryValue(ConfigMapEntry<T> entry, Object object) {
        try {
            return entry.getType().serialize((T) object);
        } catch (ClassCastException e) {
            return object;
        }
    }
}