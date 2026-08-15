package com.mdt168.mdtconfig.custom;

import com.mdt168.mdtconfig.ConfigSetting;
import com.mdt168.mdtconfig.ConfigType;
import com.mdt168.mdtconfig.datatypes.ConfigDataTypes;
import org.bukkit.permissions.Permissible;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiFunction;
import java.util.regex.Pattern;

// A String config setting that makes sure the string is a valid and clean permission node
public class PermissionConfigSetting extends ConfigSetting<String> {
    private static final Pattern PERMISSION_PATTERN =
            Pattern.compile("[a-z0-9_-]+(\\.[a-z0-9_-]+)*");

    public PermissionConfigSetting(String name, String comment, String defaultValue, ConfigType configType) {
        super(name, comment, ConfigDataTypes.STRING, defaultValue, configType);
    }

    public PermissionConfigSetting(String name, String comment, String defaultValue, ConfigType configType, @Nullable BiFunction<String, ConfigSetting<String>, String> sanitizer) {
        super(name, comment, ConfigDataTypes.STRING, defaultValue, configType, sanitizer);
    }

    @Override
    public @NotNull String modifyComment(String originalComment) {
        return originalComment + "\nOnly a-z (Only lowercase), underscores '_' and dashes '-' are allowed\nNo starting with a dot, or ending with a dot";
    }

    @Override
    public @NotNull String modifyValue(String originalValue) {
        originalValue = originalValue.trim();
        if (!PERMISSION_PATTERN.matcher(originalValue).matches()) {
            warn("The permission node: '" + originalValue + "' is not following the rules, please read the comment on '" + getKey() + "' located at '" + getConfigType().getPath(manager.getDataFolder()) + "'");
            return getDefaultValue();
        }
        return originalValue;
    }


    public boolean hasPermission(Permissible permissible) {
        return permissible.hasPermission(getValue());
    }
}
