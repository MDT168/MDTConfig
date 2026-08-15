package com.mdt168.mdtconfig.datatypes;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class HexDataType implements ConfigDataType<String> {
    HexDataType() {}

    @Override
    public @NotNull Object serialize(@NotNull String value) {
        return value;
    }

    @Override
    public @Nullable String deserialize(@NotNull Object object) {
        String s = String.valueOf(object).trim();
        if (s.length() != 7) {
            return null;
        }
        if (s.charAt(0) != '#') {
            return null;
        }
        for (int i = 1; i < 7; i++) {
            char c = s.charAt(i);
            boolean isHexDigit = (c >= '0' && c <= '9') ||
                    (c >= 'A' && c <= 'F') ||
                    (c >= 'a' && c <= 'f');
            if (!isHexDigit) {
                return null;
            }
        }
        return s;
    }

    @Override
    public @NotNull String getName() {
        return "Hex Color (e.g., #FFFFFF)";
    }
}
