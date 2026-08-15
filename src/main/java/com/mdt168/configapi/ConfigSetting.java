package com.mdt168.configapi;
import com.mdt168.configapi.datatypes.ConfigDataType;
import com.mdt168.configapi.resources.ConfigInvalidDefaultValueException;
import com.mdt168.configapi.resources.ConfigNullValueException;
import com.mdt168.configapi.resources.ConfigSettingNotInitializedException;
import com.mdt168.configapi.resources.Helper;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;

public class ConfigSetting<T> {
    private static final Map<ConfigType, Map<String, Object>> rawData = new HashMap<>();

    private boolean initialized;
    private final String name, key;
    private String comment, path;
    private final ConfigType config;
    private final T defaultValue;
    private T value;
    private Object raw;
    private final ConfigDataType<T> type;
    private final @Nullable BiFunction<T, ConfigSetting<T>, T> sanitizer;
    protected ConfigManager manager;

    public ConfigSetting(@NotNull String name, @NotNull String comment, @NotNull ConfigDataType<T> type, @NotNull T defaultValue, @NotNull ConfigType configType) {
        this(name, comment, type, defaultValue, configType, null);
    }

    @SuppressWarnings("")
    public ConfigSetting(@NotNull String name, @NotNull String comment, @NotNull ConfigDataType<T> type, @NotNull T defaultValue, @NotNull ConfigType configType, @Nullable BiFunction<T, ConfigSetting<T>, T> sanitizer) {
        Objects.requireNonNull(defaultValue, "defaultValue for '" + name + "' Config Setting cannot be null");
        this.name = name;
        this.config = configType;
        this.defaultValue = defaultValue;
        this.comment = comment;
        this.type = type;
        this.key = Helper.toYamlKey(name);
        this.sanitizer = sanitizer;
    }

    public static void clearRawData() {
        rawData.clear();
    }



    public ConfigSetting<T> register() {
        return manager.register(this);
    }

    /**
     * Called on creation, to modify the comment before put into the final variable
     * @return The final comment after modification
     */
    public @NotNull String modifyComment(String originalComment) {
        return originalComment;
    }

    /**
     * Used to make sure that the default value is valid and meets the requirements of a specific config setting, use illegalDefault(String message) when something is wrong
     * @param defaultValue The default value of the config setting
     */
    public void validateDefaultValue(@NotNull T defaultValue) {

    }

    /**
     * Called after the sanitizer, to apply class-specific changes and make sure everything is correct before putting into the value field
     * @param originalValue The value raw from the config, with no changes
     * @return The value after modification, the normal config setting just returns the same value
     */
    public @NotNull T modifyValue(T originalValue) {
        return originalValue;
    }

    public void init(ConfigManager manager) {
        this.manager = manager;
        this.path = config.getPath(manager.getDataFolder()).toString();
        validateDefaultValue(defaultValue);
        this.comment = modifyComment(comment);
        Values<T> values = getRawValue();
        this.value = values.value;
        this.raw = values.rawValue;
        this.initialized = true;
    }

    public String getName() {
        return name;
    }

    protected void illegalDefault(String message) {
        throw new ConfigInvalidDefaultValueException("[" + getLocationString() + "] Illegal Default Value: " + message);
    }
    protected void illegal(String message) {
        throw new IllegalArgumentException("[" + getLocationString() + "] " + message);
    }

    public String getLocationString() {
        return "'" + getKey() + "' at '" + getFilePath() + "'";
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    /**
     * The value from the config without any cleaning
     * @return The exact value from the config without any changes
     */
    public Object getRaw() {
        return raw;
    }

    public T getValue() {
        checkInit();
        return this.value;
    }

    private void notInit() {
        throw new ConfigSettingNotInitializedException(this);
    }

    public void reloadValueFromRawValue() {
        Values<T> values = getRawValue();
        this.value = values.value;
        this.raw = values.rawValue;
    }

    protected Values<T> getRawValue() {
        Object val = rawData.computeIfAbsent(config, manager::loadFor).computeIfAbsent(key, k -> type.serialize(defaultValue));
        if (val == null && !type.shouldAllowNulls()) {
            throw new ConfigNullValueException(key, getFilePath());
        }

        T initialValue = type.deserialize(val, this);
        T sanitized = sanitizer == null ? initialValue : sanitizer.apply(initialValue, this);
        T finalValue = modifyValue(sanitized);

        return new Values<>(val, finalValue);
    }

    public void warn(Object theInvalidValue, T usedValue) {
        warn("Has an invalid value: " + theInvalidValue + ", Using value: " + usedValue);
    }

    public void warn(String message) {
        if (manager == null) notInit();
        manager.getLogger().warning("['" + key + "' at '" + getFilePath() + "'] " + message);
    }


    private void checkInit() {
        if (!initialized) notInit();
    }

    public T warnAndUseDefault(Object theInvalidValue) {
        warn(theInvalidValue, defaultValue);
        return defaultValue;
    }

    public T getDefaultValue() {
        return defaultValue;
    }

    public void setValue(T value) {
        if (sanitizer != null) {
            this.value = modifyValue(sanitizer.apply(value, this));
        } else {
            this.value = modifyValue(value);
        }
        this.raw = type.serialize(this.value);
    }

    public String getComment() {
        return comment;
    }

    public String getKey() {
        return key;
    }

    public ConfigDataType<T> getType() {
        return type;
    }

    public @Nullable BiFunction<T, ConfigSetting<T>, T> getSanitizer() {
        return sanitizer;
    }

    public ConfigType getConfigType() {
        return config;
    }

    public String getFilePath() {
        if (path == null) notInit();
        return path;
    }

    public String getSerializedDefaultValue() {
        Object obj = type.serialize(getDefaultValue());
        return String.valueOf(obj == null ? "None" : obj);
    }

    public record Values<T>(Object rawValue, T value) {}
}
