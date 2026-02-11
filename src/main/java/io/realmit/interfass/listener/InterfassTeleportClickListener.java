package io.realmit.interfass.listener;

import io.realmit.interfass.menu.InterfassTeleportMenu;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

public final class InterfassTeleportClickListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }

        if (!event.getView().title().equals(Component.text(InterfassTeleportMenu.INTERFACE_NAME))) {
            return;
        }

        event.setCancelled(true);

        if (event.getClick() != ClickType.LEFT) {
            return;
        }

        int slot = event.getRawSlot();

        if (slot < 0 || slot >= 9) {
            return;
        }

        switch (slot) {
            case 0 -> {
                player.closeInventory();
                player.performCommand("interfass:interfass");
            }
            case 1 -> {
                player.sendMessage("You clicked slot 2, teleporting somewhere...");
            }
            case 8 -> {
                player.sendMessage("You clicked slot 9, teleporting somewhere...");
            }
        }
    }
}
