package com.mdt168.mdtconfig.datatypes;

import com.mdt168.mdtconfig.ConfigSetting;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ConfigDataType<T> {
    /**
     * Changes the value to a YAML-supported value: Numbers, String, Boolean, Map and List.
     * @param value The value of the config setting
     * @return A supported value for YAML to be put in the config
     * DON'T return null if `shouldAllowNulls()` returns false (default)
     */
    Object serialize(@NotNull T value);

    /**
     * Turning the config value to the wanted type
     * @param object The original value from the config, always not Null unless you return true in `shouldAllowNulls()`
     * @param caller The config setting that we are checking the value of
     * @return The value that should be put in the config setting
     */
    default @NotNull T deserialize(Object object, @NotNull ConfigSetting<T> caller) {
        T value = deserialize(object);
        if (value == null) {
            return caller.warnAndUseDefault(object);
        }
        return value;
    }

    /**
     * Called for deserializing a specific element rather than a whole config setting value
     * @param object The object to deserialize, always not null unless you return true in 'shouldAllowNulls()`
     * @return {@code null} if the object is invalid and can't be converted to the correct type
     */
    @Nullable T deserialize(Object object);

    /**
     * @return The type name, to be displayed in the config (e.g., Type: Whole Number)
     */
    @NotNull String getName();


    /**
     * If the default value should be displayed in the config
     * @return {@code true} if the default value should be displayed, false otherwise
     */
    default boolean shouldDisplayDefaultValue() {
        return true;
    }

    /**
     * Should we not throw an exception when the value in the config is null?
     * Note: deserialize() and serialize() would have nulls if set to true
     * @return {@code true} if the config value can be null, {@code false} otherwise
     */
    default boolean shouldAllowNulls() {
        return false;
    }
}
