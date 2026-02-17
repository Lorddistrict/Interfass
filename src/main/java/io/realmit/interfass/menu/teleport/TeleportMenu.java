package io.realmit.interfass.menu.teleport;

import io.realmit.interfass.menu.MenuInterface;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public final class TeleportMenu implements MenuInterface
{
    public static final String INTERFACE_NAME = "Interfass TP Menu";

    public void open(Player player)
    {
        player.openInventory(this.initTeleportationMenu());
    }

    public Inventory initTeleportationMenu()
    {
        Inventory inventory = Bukkit.createInventory(null, 9, Component.text(INTERFACE_NAME));

        List<Component> titles = List.of(
                Component.text("Slot 1"),
                Component.text("Slot 2"),
                Component.text("Slot 3"),
                Component.text("Slot 4"),
                Component.text("Slot 5"),
                Component.text("Slot 6"),
                Component.text("Slot 7"),
                Component.text("Slot 8"),
                Component.text("Slot 9")
        );

        List<List<Component>> lores = List.of(
                List.of(Component.text("Lore for slot 1")),
                List.of(Component.text("Lore for slot 2")),
                List.of(Component.text("Lore for slot 3")),
                List.of(Component.text("Lore for slot 4")),
                List.of(Component.text("Lore for slot 5")),
                List.of(Component.text("Lore for slot 6")),
                List.of(Component.text("Lore for slot 7")),
                List.of(Component.text("Lore for slot 8")),
                List.of(Component.text("Lore for slot 9"))
        );

        for (int slot = 0; slot < 9; slot++) {
            ItemStack paper = new ItemStack(Material.PAPER);
            ItemMeta meta = paper.getItemMeta();

            meta.displayName(titles.get(slot));
            meta.lore(lores.get(slot));

            paper.setItemMeta(meta);
            inventory.setItem(slot, paper);
        }

        return inventory;
    }
}
