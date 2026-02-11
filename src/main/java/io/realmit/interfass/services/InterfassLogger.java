package io.realmit.interfass.services;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class InterfassLogger {
    private final JavaPlugin plugin;

    public InterfassLogger(@NotNull JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void separator() {
        plugin.getLogger().info("[INTERFASS] ==================================================================");
    }

    public void info(String message) {
        plugin.getLogger().info(message);
    }

    public void warn(String message) {
        plugin.getLogger().warning(message);
    }

    public void error(String message) {
        plugin.getLogger().severe(message);
    }
}
