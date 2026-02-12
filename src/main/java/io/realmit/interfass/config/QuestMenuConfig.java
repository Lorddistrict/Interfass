package io.realmit.interfass.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import java.nio.file.Files;

public class QuestMenuConfig {

    private final Plugin plugin;
    private File file;
    private FileConfiguration config;

    public QuestMenuConfig(Plugin plugin) {
        this.plugin = plugin;
        createAndLoad();
    }

    private void createAndLoad() {
        file = new File(plugin.getDataFolder(), "questMenu.yml");

        if (!file.exists()) {
            plugin.getDataFolder().mkdirs();
            try (InputStream in = plugin.getResource("questMenu.yml")) {
                if (in != null) {
                    Files.copy(in, file.toPath());
                } else {
                    // Fallback: create empty file if resource missing
                    file.createNewFile();
                }
            } catch (IOException e) {
                plugin.getLogger().severe("Could not save default questMenu.yml: " + e.getMessage());
            }
        }

        config = YamlConfiguration.loadConfiguration(file);
    }

    public void reload() {
        config = YamlConfiguration.loadConfiguration(file);
    }

    public FileConfiguration getConfig() {
        return config;
    }
}
