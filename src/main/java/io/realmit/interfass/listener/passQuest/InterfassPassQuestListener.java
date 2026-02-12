package io.realmit.interfass.listener.passQuest;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public final class InterfassPassQuestListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }

        Inventory clickedInventory = event.getClickedInventory();
        if (clickedInventory == null) {
            return;
        }

        if (!event.getView().title().equals(Component.text("Interfass Menu"))) {
            return;
        }

        int rawSlot = event.getRawSlot();

        if (rawSlot < event.getView().getTopInventory().getSize()) {
            event.setCancelled(true);
        }

        if (event.isCancelled()) {
            switch (rawSlot) {
                case 10, 11, 19, 20 -> {
                    if (!event.isLeftClick()) {
                        return;
                    }

                    player.closeInventory();
                    player.performCommand("interfass:interfass");
                }
            }
        }
    }
}
