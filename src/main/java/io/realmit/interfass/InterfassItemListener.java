package io.realmit.interfass;

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

public final class InterfassItemListener implements Listener
{
    // Configure your special item here
    private static final Material ITEM_MATERIAL = Material.NETHER_STAR;
    private static final Component ITEM_NAME = Component.text("Open the Interfass menu");

    private final InterfassMenu menu;

    public InterfassItemListener(@NotNull InterfassMenu menu)
    {
        this.menu = menu;
    }

    // Give the item when the player joins
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        ItemStack item = new ItemStack(ITEM_MATERIAL);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(ITEM_NAME);
        item.setItemMeta(meta);

        // Put it in hotbar slot 0 if empty, otherwise just add
        if (player.getInventory().getItem(0) == null) {
            player.getInventory().setItem(0, item);
        } else {
            player.getInventory().addItem(item);
        }
    }

    // Open the GUI when the player right‑clicks with that item
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        // Only main‑hand interactions (avoid double‑fire with off‑hand)
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
            return; // make sure it's *our* item, not any Nether Star
        }

        event.setCancelled(true); // prevent default use
        menu.open(event.getPlayer());
    }
}
