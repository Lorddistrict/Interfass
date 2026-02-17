package io.realmit.interfass.api.service;

import io.realmit.interfass.api.dto.GiveItemRequest;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.Locale;
import java.util.UUID;

public final class GiveItemService {

    private final Plugin plugin;
    private final PendingItemStoreService pendingItemStoreService;

    public GiveItemService(
            Plugin plugin,
            PendingItemStoreService pendingItemStoreService
    ) {
        this.plugin = plugin;
        this.pendingItemStoreService = pendingItemStoreService;
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
            ItemStack item = new ItemStack(material, amount);

            if (null != target) {
                handleOnlinePlayer(target, item, material, amount);
            } else {
                handleOfflinePlayer(playerName, item, material, amount);
            }
        });
    }

    private void handleOnlinePlayer(Player player, ItemStack item, Material material, int amount) {
        player.getInventory().addItem(item);

        plugin.getServer().sendMessage(Component.text(
                "[API][OnlinePlayer] : " + player.getName() + " received " + amount + " " + material.name())
        );
    }

    private void handleOfflinePlayer(String playerName, ItemStack item, Material material, int amount) {
        UUID uuid = Bukkit.getOfflinePlayer(playerName).getUniqueId();

        pendingItemStoreService.addPendingItem(uuid, item);

        plugin.getLogger().info(
                "[API][OfflinePlayer] : " + amount + " " + material.name() + " for offline player " + playerName
        );
    }
}
