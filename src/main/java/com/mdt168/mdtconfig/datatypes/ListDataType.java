package com.mdt168.mdtconfig.datatypes;

import org.jetbrains.annotations.NotNull;

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