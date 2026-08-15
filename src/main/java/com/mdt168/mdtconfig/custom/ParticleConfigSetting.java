package com.mdt168.mdtconfig.custom;

import com.mdt168.mdtconfig.ConfigSetting;
import com.mdt168.mdtconfig.ConfigType;
import org.bukkit.Particle;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiFunction;

public class ParticleConfigSetting extends EnumConfigSetting<Particle> {
    public ParticleConfigSetting(@NotNull String name, @NotNull String comment, @NotNull Particle defaultValue, @NotNull ConfigType configType) {
        this(name, comment, defaultValue, configType, null);
    }

    public ParticleConfigSetting(@NotNull String name, @NotNull String comment, @NotNull Particle defaultValue, @NotNull ConfigType configType, @Nullable BiFunction<Particle, ConfigSetting<Particle>, Particle> sanitizer) {
        super(name, comment, false, defaultValue, configType, sanitizer);
    }

    @Override
    public @NotNull String modifyComment(String originalComment) {
        return originalComment + "\nMinecraft Particles (e.g., VILLAGER_ANGRY)";
    }
}
