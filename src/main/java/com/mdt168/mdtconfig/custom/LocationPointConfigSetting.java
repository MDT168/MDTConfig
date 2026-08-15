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

public class LocationPointConfigSetting extends TypedMapConfigSetting<Double> {
    public LocationPointConfigSetting(@NotNull String name, @NotNull String comment, double defaultX, double defaultY, double defaultZ, @NotNull ConfigType configType) {
        this(name, comment, defaultX, defaultY, defaultZ, configType, null);
    }

    public LocationPointConfigSetting(@NotNull String name, @NotNull String comment, double defaultX, double defaultY, double defaultZ, @NotNull ConfigType configType, @Nullable BiFunction<Map<String, Double>, ConfigSetting<Map<String, Double>>, Map<String, Double>> sanitizer) {
        super(name, comment, ConfigDataTypes.DOUBLE, toEntries(defaultX, defaultY, defaultZ), configType, sanitizer);
    }

    public double getX() {
        return getValue("x");
    }

    public double getY() {
        return getValue("y");
    }

    public double getZ() {
        return getValue("z");
    }

    private static List<ConfigMapEntry<Double>> toEntries(double x, double y, double z) {
        List<ConfigMapEntry<Double>> entries = new ArrayList<>();
        entries.add(new ConfigMapEntry<>("x", ConfigDataTypes.DOUBLE, x));
        entries.add(new ConfigMapEntry<>("y", ConfigDataTypes.DOUBLE, y));
        entries.add(new ConfigMapEntry<>("z", ConfigDataTypes.DOUBLE, z));
        return entries;
    }
}
