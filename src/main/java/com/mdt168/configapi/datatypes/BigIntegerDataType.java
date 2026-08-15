package com.mdt168.configapi.datatypes;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigInteger;

public class BigIntegerDataType implements ConfigDataType<BigInteger> {
    BigIntegerDataType() {}

    @Override
    public @NotNull String getName() {
        return "Big Integer (Whole Number)";
    }

    @Override
    public @Nullable BigInteger deserialize(@NotNull Object object) {
        try {
            return new BigInteger(String.valueOf(object));
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public @NotNull Object serialize(@NotNull BigInteger value) {
        return value;
    }
}
