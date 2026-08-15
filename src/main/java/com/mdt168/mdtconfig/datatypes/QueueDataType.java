package com.mdt168.mdtconfig.datatypes;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayDeque;
import java.util.Queue;

public class QueueDataType<T> extends CollectionDataType<T, Queue<@NotNull T>> {

    public QueueDataType(@NotNull ConfigDataType<T> elementType) {
        super(elementType);
    }

    @Override
    protected @NotNull Queue<@NotNull T> createCollection(int initialCapacity) {
        return new ArrayDeque<>(initialCapacity);
    }

    @Override
    protected @NotNull String getCollectionTypeName() {
        return "Queue";
    }
}
