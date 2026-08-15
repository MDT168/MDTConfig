package com.mdt168.mdtconfig.datatypes;

import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PotionEffectTypeDataType implements ConfigDataType<PotionEffectType> {
    PotionEffectTypeDataType() {}

    @Override
    public @NotNull Object serialize(@NotNull PotionEffectType value) {
        return value.getName();
    }

    @Override
    public @Nullable PotionEffectType deserialize(@NotNull Object object) {
        return PotionEffectType.getByName(String.valueOf(object));
    }

    @Override
    public @NotNull String getName() {
        return "Potion Effect Type (e.g., SPEED)";
    }
}
