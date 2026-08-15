package com.mdt168.configapi;

import org.bukkit.plugin.java.JavaPlugin;

public final class ConfigAPI extends JavaPlugin {

    @Override
    public void onDisable() {
        ConfigManager.saveAll();
    }
}
