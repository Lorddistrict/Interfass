package io.realmit.interfass.api.service;

import io.realmit.interfass.api.dto.GiveItemRequest;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.Locale;

public final class GiveItemService {

    private final Plugin plugin;

    public GiveItemService(Plugin plugin) {
        this.plugin = plugin;
    }

    public void giveItem(GiveItemRequest request) {
        String playerName = request.player();
        String itemName = request.item();
        int amount = request.amount();

        Material material = Material.matchMaterial(itemName.toUpperCase(Locale.ROOT));
        if (material == null) {
            plugin.getLogger().warning("Unknown item in API request: " + itemName);
            return;
        }

        Bukkit.getScheduler().runTask(plugin, () -> {
            Player target = Bukkit.getPlayerExact(playerName);
            if (target == null) {
                plugin.getLogger().warning("Player not found in API request: " + playerName);
                return;
            }
            target.getInventory().addItem(new ItemStack(material, amount));
        });
    }
}
