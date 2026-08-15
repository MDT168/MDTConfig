package com.mdt168.mdtconfig.custom;

import com.mdt168.mdtconfig.ConfigSetting;
import com.mdt168.mdtconfig.ConfigType;
import com.mdt168.mdtconfig.datatypes.ConfigDataTypes;
import com.mdt168.mdtconfig.resources.ConfigMapEntry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

public class MapConfigSetting extends TypedMapConfigSetting<Object> {

    public MapConfigSetting(@NotNull String name, @NotNull String comment, @NotNull List<@NotNull ConfigMapEntry<Object>> defaultValue, @NotNull ConfigType configType) {
        super(name, comment, ConfigDataTypes.OBJECT, defaultValue, configType);
    }

    public MapConfigSetting(@NotNull String name, @NotNull String comment, @NotNull List<@NotNull ConfigMapEntry<Object>> defaultValue, @NotNull ConfigType configType, @Nullable BiFunction<Map<String, Object>, ConfigSetting<Map<String, Object>>, Map<String, Object>> sanitizer) {
        super(name, comment, ConfigDataTypes.OBJECT, defaultValue, configType, sanitizer);
    }
}
