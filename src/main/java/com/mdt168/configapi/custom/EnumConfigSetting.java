package com.mdt168.configapi.custom;

import com.mdt168.configapi.ConfigSetting;
import com.mdt168.configapi.ConfigType;
import com.mdt168.configapi.datatypes.ConfigDataTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiFunction;

public class EnumConfigSetting<T extends Enum<T>> extends ConfigSetting<T> {
    private final boolean shouldDisplayEnumConstants;

    public EnumConfigSetting(String name, String comment, boolean shouldDisplayEnumConstants, T defaultValue, ConfigType configType) {
        super(name, comment, ConfigDataTypes.enumType(defaultValue.getDeclaringClass()), defaultValue, configType);
        this.shouldDisplayEnumConstants = shouldDisplayEnumConstants;
    }

    public EnumConfigSetting(String name, String comment, boolean shouldDisplayEnumConstants, T defaultValue, ConfigType configType, @Nullable BiFunction<T, ConfigSetting<T>, T> sanitizer) {
        super(name, comment, ConfigDataTypes.enumType(defaultValue.getDeclaringClass()), defaultValue, configType, sanitizer);
        this.shouldDisplayEnumConstants = shouldDisplayEnumConstants;
    }

    @Override
    public @NotNull String modifyComment(String originalComment) {
        if (!shouldDisplayEnumConstants) return originalComment;
        StringBuilder comment = new StringBuilder(originalComment).append("\nOptions: ");
        T[] constants = getDefaultValue().getDeclaringClass().getEnumConstants();
        for (int i = 0; i < constants.length; i++) {
            comment.append(constants[i]);
            if (i + 1 != constants.length) {
                comment.append(", ");
            }
        }
        return comment.toString();
    }
}
