package com.mdt168.mdtconfig.datatypes;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;

public class BigDecimalDataType implements ConfigDataType<BigDecimal> {
    BigDecimalDataType() {}

    @Override
    public @NotNull String getName() {
        return "Big Decimal Number";
    }

    @Override
    public @Nullable BigDecimal deserialize(@NotNull Object object) {
        try {
            return new BigDecimal(String.valueOf(object));
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public @NotNull Object serialize(@NotNull BigDecimal value) {
        return value;
    }
}
