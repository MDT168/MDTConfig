package com.mdt168.mdtconfig.resources;

import com.mdt168.mdtconfig.ConfigSetting;

public class ConfigSettingNotInitializedException extends RuntimeException {
    public ConfigSettingNotInitializedException(ConfigSetting<?> setting) {
        super("Attempted to access an uninitialized ConfigSetting. "
                + "Call init() before using it. ["
                + setting.getLocationString() + "]");
    }
}
