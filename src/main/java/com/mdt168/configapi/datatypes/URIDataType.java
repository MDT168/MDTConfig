package com.mdt168.configapi.datatypes;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URI;

public class URIDataType implements ConfigDataType<URI> {
    URIDataType() {}

    @Override
    public @NotNull Object serialize(@NotNull URI value) {
        return value.toString();
    }

    @Override
    public @Nullable URI deserialize(@NotNull Object object) {
        try {
            return URI.create(String.valueOf(object));
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public @NotNull String getName() {
        return "URI (Link)";
    }
}
