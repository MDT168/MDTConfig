package com.mdt168.mdtconfig.custom;

import com.mdt168.mdtconfig.ConfigSetting;
import com.mdt168.mdtconfig.ConfigType;
import com.mdt168.mdtconfig.datatypes.ConfigDataTypes;
import com.mdt168.mdtconfig.resources.ConfigMapEntry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

public class LocationPointConfigSetting extends MapConfigSetting {
    public LocationPointConfigSetting(@NotNull String name, @NotNull String comment, double defaultX, double defaultY, double defaultZ, @NotNull ConfigType configType) {
        this(name, comment, defaultX, defaultY, defaultZ, configType, null);
    }

    public LocationPointConfigSetting(@NotNull String name, @NotNull String comment, double defaultX, double defaultY, double defaultZ, @NotNull ConfigType configType, @Nullable BiFunction<Map<String, Object>, ConfigSetting<Map<String, Object>>, Map<String, Object>> sanitizer) {
        super(name, comment, toEntries(defaultX, defaultY, defaultZ), configType, sanitizer);
    }

    public double getX() {
        return (double) getValue("x");
    }

    public double getY() {
        return (double) getValue("y");
    }

    public double getZ() {
        return (double) getValue("z");
    }

    private static List<ConfigMapEntry<?>> toEntries(double x, double y, double z) {
        List<ConfigMapEntry<?>> entries = new ArrayList<>();
        entries.add(new ConfigMapEntry<>("x", ConfigDataTypes.DOUBLE, x));
        entries.add(new ConfigMapEntry<>("y", ConfigDataTypes.DOUBLE, y));
        entries.add(new ConfigMapEntry<>("z", ConfigDataTypes.DOUBLE, z));
        return entries;
    }
}
