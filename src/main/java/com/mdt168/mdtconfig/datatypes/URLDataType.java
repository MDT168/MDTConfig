package com.mdt168.mdtconfig.datatypes;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URI;
import java.net.URL;

public class URLDataType implements ConfigDataType<URL> {
    URLDataType() {}

    @Override
    public @NotNull Object serialize(@NotNull URL value) {
        return value.toString();
    }

    @Override
    public @Nullable URL deserialize(@NotNull Object object) {
        try {
            return URI.create(String.valueOf(object)).toURL();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public @NotNull String getName() {
        return "URL (Link)";
    }
}
