package com.mdt168.configapi.custom;

import com.mdt168.configapi.ConfigSetting;
import com.mdt168.configapi.ConfigType;
import org.bukkit.Sound;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiFunction;

public class SoundConfigSetting extends EnumConfigSetting<Sound> {
    public SoundConfigSetting(@NotNull String name, @NotNull String comment, @NotNull Sound defaultValue, @NotNull ConfigType configType) {
        this(name, comment, defaultValue, configType, null);
    }

    public SoundConfigSetting(@NotNull String name, @NotNull String comment, @NotNull Sound defaultValue, @NotNull ConfigType configType, @Nullable BiFunction<Sound, ConfigSetting<Sound>, Sound> sanitizer) {
        super(name, comment, false, defaultValue, configType, sanitizer);
    }

    @Override
    public @NotNull String modifyComment(String originalComment) {
        return originalComment + "\nMinecraft Sounds (e.g., ENTITY_VILLAGER_HURT)";
    }
}
