package com.mdt168.mdtconfig.datatypes;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class SetDataType<T> extends CollectionDataType<T, Set<@NotNull T>> {

    public SetDataType(@NotNull ConfigDataType<T> elementType) {
        super(elementType);
    }

    @Override
    protected @NotNull Set<@NotNull T> createCollection(int initialCapacity) {
        return new LinkedHashSet<>(initialCapacity);
    }

    @Override
    protected @NotNull String getCollectionTypeName() {
        return "Set (List with no duplicates)";
    }
}
