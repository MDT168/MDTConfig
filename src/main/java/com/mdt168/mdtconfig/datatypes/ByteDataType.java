package com.mdt168.mdtconfig.datatypes;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ByteDataType implements ConfigDataType<Byte> {
    ByteDataType() {}

    @Override
    public @NotNull Object serialize(@NotNull Byte value) {
        return value;
    }

    @Override
    public @Nullable Byte deserialize(@NotNull Object object) {
        if (object instanceof Byte b) return b;
        try {
            return Byte.valueOf(String.valueOf(object));
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public @NotNull String getName() {
        return "Byte (Whole Number '-128..127')";
    }
}
