package com.mdt168.configapi;

import org.bukkit.plugin.Plugin;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConfigManager {
    private static final List<ConfigManager> MANAGERS = new ArrayList<>();
    private static final Yaml YAML = new Yaml();

    private final List<ConfigSetting<?>> settings = new ArrayList<>();
    private final Plugin plugin;
    private final Path dataFolder;
    private final Logger logger;
    public ConfigManager(Plugin plugin) {
        this.plugin = plugin;
        this.dataFolder = plugin.getDataFolder().toPath();
        this.logger = plugin.getLogger();
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public <T extends ConfigSetting<?>> T register(T setting) {
        setting.init(this);
        if (!settings.contains(setting)) {
            settings.add(setting);
        }
        return setting;
    }

    public void reloadFromConfig() {
        ConfigSetting.clearRawData();
        for (ConfigSetting<?> setting : settings) {
            setting.reloadValueFromRawValue();
        }
    }

    protected Map<String, Object> loadFor(ConfigType type) {
        try {
            Path path = type.getPath(dataFolder);
            if (Files.notExists(path)) {
                return new HashMap<>();
            }

            try (InputStream stream = Files.newInputStream(path)) {
                Object loaded = YAML.load(stream);
                if (loaded instanceof Map<?, ?> map) {
                    Map<String, Object> result = new HashMap<>();
                    map.forEach((k, v) -> result.put(String.valueOf(k), v));
                    return result;
                }
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to load " + type.getExtraPath() + ".", e);
        }
        return new HashMap<>();
    }

    public static <T extends ConfigManager> T register(T manager) {
        if (!MANAGERS.contains(manager)) {
            MANAGERS.add(manager);
        }
        return manager;
    }

    public static void saveAll() {
        for (ConfigManager manager : MANAGERS) {
            manager.save();
        }
    }

    public void save() {
        Map<ConfigType, StringBuilder> allLines = new HashMap<>();
        for (ConfigSetting<?> setting : settings) {
            StringBuilder result = allLines.computeIfAbsent(setting.getConfigType(), s -> new StringBuilder().append(setting.getConfigType().getPrefix()));
            String[] commentParts = setting.getComment().split("\n");
            for (String comment : commentParts) {
                result.append("# ").append(comment).append('\n');
            }


            result.append("# ");
            if (setting.getType().shouldDisplayDefaultValue()) {
                result.append("Default Value: ").append(setting.getSerializedDefaultValue()).append(" | ");
            }
            result.append("Type: ").append(setting.getType().getName()).append('\n');

            Map<String, Object> root = new HashMap<>();
            root.put(setting.getKey(), setting.getRaw());
            result.append(YAML.dumpAs(root, null, DumperOptions.FlowStyle.BLOCK)).append("\n");
        }
        allLines.forEach(((configType, builder) -> {
            Path path = configType.getPath(dataFolder);
            try {
                builder.deleteCharAt(builder.length() - 1);
                if (Files.notExists(path)) {
                    Files.createDirectories(path.getParent());
                    Files.createFile(path);
                }
                Files.writeString(path, builder);
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Failed to save config '" + configType.getExtraPath() + "'", e);
            }
        }));
    }

    public Logger getLogger() {
        return logger;
    }

    public Path getDataFolder() {
        return dataFolder;
    }
}
