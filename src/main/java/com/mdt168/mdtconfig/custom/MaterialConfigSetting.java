package com.mdt168.mdtconfig.custom;

import com.mdt168.mdtconfig.ConfigSetting;
import com.mdt168.mdtconfig.ConfigType;
import org.bukkit.Material;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiFunction;

public class MaterialConfigSetting extends EnumConfigSetting<Material> {
    public MaterialConfigSetting(String name, String comment, Material defaultValue, ConfigType configType) {
        super(name, comment, false, defaultValue, configType);
    }

    public MaterialConfigSetting(String name, String comment, Material defaultValue, ConfigType configType, @Nullable BiFunction<Material, ConfigSetting<Material>, Material> sanitizer) {
        super(name, comment, false, defaultValue, configType, sanitizer);
    }

    @Override
    public @NotNull String modifyComment(String originalComment) {
        return originalComment + "\nChoose a Minecraft Material (e.g., DIAMOND_SWORD, OAK_LOG)";
    }
}
