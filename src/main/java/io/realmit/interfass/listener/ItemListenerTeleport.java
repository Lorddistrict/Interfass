package io.realmit.interfass.listener;

import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public final class ItemListenerTeleport implements Listener {
    private static final Material ITEM_MATERIAL = Material.WITHER_SKELETON_SKULL;
    private static final Component ITEM_NAME = Component.text("Teleport me");
    private static final Component ITEM_LORE = Component.text("This is a lore line");

    public ItemListenerTeleport() {
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

        ItemMeta wssMeta = item.getItemMeta();
        if (wssMeta == null || !ITEM_NAME.equals(wssMeta.displayName())) {
            return;
        }

        List<Component> lore = wssMeta.lore();
        if (null == lore || 1 != lore.size() || !ITEM_LORE.equals(lore.getFirst())) {
            return;
        }

        event.setCancelled(true);

        World world = Bukkit.getWorlds().getFirst();
        Player player = event.getPlayer();

        player.teleport(world.getSpawnLocation());
    }
}
