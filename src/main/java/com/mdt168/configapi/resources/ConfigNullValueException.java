package com.mdt168.configapi.resources;

public class ConfigNullValueException extends RuntimeException {
    public ConfigNullValueException(String message) {
        super(message);
    }

    public ConfigNullValueException(String key, String filePath) {
        this("'" + key + "' at '" + filePath + "' can't be null");
    }
}
