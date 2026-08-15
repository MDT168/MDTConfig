package com.mdt168.configapi.datatypes;

import com.mdt168.configapi.ConfigSetting;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ListDataType<T> extends CollectionDataType<T, List<@NotNull T>> {
    public ListDataType(@NotNull ConfigDataType<T> elementType) {
        super(elementType);
    }

    @Override
    protected @NotNull List<@NotNull T> createCollection(int initialCapacity) {
        return new ArrayList<>(initialCapacity);
    }

    @Override
    protected @NotNull String getCollectionTypeName() {
        return "List";
    }
}