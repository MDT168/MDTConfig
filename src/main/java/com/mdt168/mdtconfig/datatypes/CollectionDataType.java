package com.mdt168.mdtconfig.datatypes;

import com.mdt168.mdtconfig.ConfigSetting;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class CollectionDataType<E, C extends Collection<@NotNull E>> implements ConfigDataType<C> {
    protected final @NotNull ConfigDataType<E> elementType;

    public CollectionDataType(@NotNull ConfigDataType<E> elementType) {
        this.elementType = elementType;
    }

    protected abstract @NotNull C createCollection(int initialCapacity);

    protected abstract @NotNull String getCollectionTypeName();

    @Override
    public @NotNull Object serialize(@NotNull C collection) {
        List<Object> serializedList = new ArrayList<>(collection.size());
        for (E item : collection) {
            serializedList.add(elementType.serialize(item));
        }
        return serializedList;
    }

    @Override
    public @NotNull C deserialize(@NotNull Object object, @NotNull ConfigSetting<C> caller) {
        if (!(object instanceof Collection<?> rawCollection)) {
            return caller.warnAndUseDefault(object);
        }

        C result = createCollection(rawCollection.size());

        for (Object rawElement : rawCollection) {
            if (rawElement == null && !elementType.shouldAllowNulls()) {
                caller.warn("Invalid element for Config Setting List (null element). Skipping.");

                continue;
            }

            E convertedElement = elementType.deserialize(rawElement);
            if (convertedElement == null) {
                caller.warn("Invalid element '" + rawElement + "' in " + getCollectionTypeName() + "'. Skipping.");
                continue;
            }

            if (!result.add(convertedElement)) {
                caller.warn("Duplicate element '" + convertedElement + "' in " + getCollectionTypeName() + " '"
                        + caller.getKey() + "' at '" + caller.getFilePath() + "'. Skipping.");
            }
        }

        return result;
    }

    @Override
    public @Nullable C deserialize(@NotNull Object object) {
        if (!(object instanceof Collection<?> rawCollection)) {
            return null;
        }

        C result = createCollection(rawCollection.size());

        for (Object rawElement : rawCollection) {
            if (rawElement == null && !elementType.shouldAllowNulls()) continue;
            E convertedElement = elementType.deserialize(rawElement);
            if (convertedElement == null) {
                return null;
            }
            result.add(convertedElement);
        }

        return result;
    }

    @Override
    public @NotNull String getName() {
        return elementType.getName() + " " + getCollectionTypeName();
    }
}