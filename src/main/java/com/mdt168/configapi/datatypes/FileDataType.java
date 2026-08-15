package com.mdt168.configapi.datatypes;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;


public class FileDataType implements ConfigDataType<File> {
    FileDataType() {}

    @Override
    public @NotNull Object serialize(@NotNull File value) {
        return value.toString();
    }

    @Override
    public @Nullable File deserialize(@NotNull Object object) {
        try {
            return new File(String.valueOf(object));
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public @NotNull String getName() {
        return "File Path";
    }
}
