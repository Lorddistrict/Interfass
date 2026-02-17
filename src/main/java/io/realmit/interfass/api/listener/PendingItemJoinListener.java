package io.realmit.interfass.api.listener;

import io.realmit.interfass.api.service.PendingItemStoreService;
import io.realmit.interfass.services.PlayerActionsService;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public final class PendingItemJoinListener implements Listener {

    private final PendingItemStoreService pendingItemStoreService;
    private final PlayerActionsService playerActionsService;

    public PendingItemJoinListener(
            PendingItemStoreService pendingItemStoreService,
            PlayerActionsService playerActionsService
    ) {
        this.pendingItemStoreService = pendingItemStoreService;
        this.playerActionsService = playerActionsService;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (!playerActionsService.canPlayerRedeemItems(player)) {
            return;
        }

        List<ItemStack> items = pendingItemStoreService.consumePendingItems(player.getUniqueId());

        if (items.isEmpty()) {
            return;
        }

        for (ItemStack item : items) {
            player.getInventory().addItem(item);
        }

        player.sendMessage(Component.text(
                "You received " + items.size() + " stored item(s) from the API while you were offline."
        ));
    }
}
