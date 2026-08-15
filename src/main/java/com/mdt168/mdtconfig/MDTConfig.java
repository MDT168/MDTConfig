package com.mdt168.mdtconfig;

import org.bukkit.plugin.java.JavaPlugin;

public final class MDTConfig extends JavaPlugin {

    @Override
    public void onDisable() {
        ConfigManager.saveAll();
    }
}
