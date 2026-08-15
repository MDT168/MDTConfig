package com.mdt168.mdtconfig.custom;

import com.mdt168.mdtconfig.ConfigSetting;
import com.mdt168.mdtconfig.ConfigType;
import com.mdt168.mdtconfig.datatypes.ConfigDataType;
import com.mdt168.mdtconfig.datatypes.ConfigDataTypes;
import com.mdt168.mdtconfig.resources.ConfigMapEntry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

public class TypedMapConfigSetting<T> extends ConfigSetting<Map<String, T>> {
    private final Map<String, ConfigMapEntry<? extends T>> entries = new LinkedHashMap<>();

    public TypedMapConfigSetting(@NotNull String name, @NotNull String comment, ConfigDataType<T> type, @NotNull List<@NotNull ConfigMapEntry<? extends T>> defaultValue, @NotNull ConfigType configType) {
        this(name, comment, type, defaultValue, configType, null);
    }

    public TypedMapConfigSetting(@NotNull String name, @NotNull String comment, ConfigDataType<T> type, @NotNull List<@NotNull ConfigMapEntry<? extends T>> defaultValue, @NotNull ConfigType configType, @Nullable BiFunction<Map<String, T>, ConfigSetting<Map<String, T>>, Map<String, T>> sanitizer) {
        super(name, comment, ConfigDataTypes.mapOf(type), toDataMap(defaultValue), configType, sanitizer);
        for (ConfigMapEntry<? extends T> entry : defaultValue) {
            entries.put(entry.getKey(), entry);
        }
    }

    public @NotNull T getValue(String key) {
        if (!entries.containsKey(key)) {
            illegal("Unknown Map Key '" + key + "': Not found");
        }
        Map<String, T> val = getValue();
        ConfigMapEntry<? extends T> entry = entries.get(key);
        return val.computeIfAbsent(key, k -> entry.getDefaultValue());
    }

    public void addEntry(@NotNull ConfigMapEntry<? extends T> entry) {
        entries.put(entry.getKey(), entry);
        getValue().put(entry.getKey(), entry.getDefaultValue());
    }

    public @NotNull T getDefaultValue(String key) {
        if (!entries.containsKey(key)) {
            illegal("Unknown Map Key '" + key + "': Not found");
        }
        return entries.get(key).getDefaultValue();
    }

    @Override
    public @NotNull Map<String, T> modifyValue(Map<String, T> original) {
        Map<String, T> originalValue = new LinkedHashMap<>(original);
        for (ConfigMapEntry<? extends T> entry : entries.values()) {
            originalValue.putIfAbsent(entry.getKey(), entry.getDefaultValue());
        }
        for (Map.Entry<String, T> entry : originalValue.entrySet()) {
            ConfigMapEntry<? extends T> configEntry = entries.get(entry.getKey());
            if (configEntry == null) continue;
            entry.setValue(configEntry.getFinalValue(entry.getValue(), this));
        }
        return originalValue;
    }

    private static <T> Map<String, T> toDataMap(List<ConfigMapEntry<? extends T>> entries) {
        Map<String, T> map = new LinkedHashMap<>();
        for (ConfigMapEntry<? extends T> entry : entries) {
            map.put(entry.getKey(), entry.getDefaultValue());
        }
        return map;
    }
}