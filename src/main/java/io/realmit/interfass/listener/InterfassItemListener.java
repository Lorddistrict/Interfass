package io.realmit.interfass.listener;

import io.realmit.interfass.services.InterfassLogger;
import io.realmit.interfass.menu.InterfassMenu;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public final class InterfassItemListener implements Listener {
    private static final Material ITEM_MATERIAL = Material.NETHER_STAR;
    private static final Component ITEM_NAME = Component.text("Open the Interfass menu");

    private final InterfassLogger logger;
    private final InterfassMenu menu;

    public InterfassItemListener(
            @NotNull InterfassLogger logger,
            @NotNull InterfassMenu menu
    ) {
        this.logger = logger;
        this.menu = menu;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        ItemStack item = new ItemStack(ITEM_MATERIAL);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(ITEM_NAME);
        item.setItemMeta(meta);

        if (player.getInventory().getItem(0) == null) {
            player.getInventory().setItem(0, item);
        } else {
            player.getInventory().addItem(item);
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getHand() != EquipmentSlot.HAND) {
            return;
        }

        Action action = event.getAction();
        if (action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        ItemStack item = event.getItem();
        if (item == null || item.getType() != ITEM_MATERIAL) {
            return;
        }

        ItemMeta meta = item.getItemMeta();
        if (meta == null || !ITEM_NAME.equals(meta.displayName())) {
            return;
        }

        event.setCancelled(true);
        menu.open(event.getPlayer());
    }
}
