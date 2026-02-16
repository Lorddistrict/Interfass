package io.realmit.interfass.api.service;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public final class PrintMessageService {

    private final Plugin plugin;

    public PrintMessageService(Plugin plugin) {
        this.plugin = plugin;
    }

    public void printMessage() {
        Bukkit.getScheduler().runTask(plugin, () -> {
            plugin.getServer().sendMessage(Component.text("Hello chat !"));
        });
    }
}
