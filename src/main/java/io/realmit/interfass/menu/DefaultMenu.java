package io.realmit.interfass.menu;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public final class DefaultMenu implements MenuInterface
{
    private static final int LINE = 9;
    private static final int CENTER_SLOT = 13;
    private static final int FIRST_SLOT = 1;

    public void open(Player player)
    {
        Inventory inventory = Bukkit.createInventory(null, LINE*6, Component.text("Interfass Menu"));

        ItemStack diamond = new ItemStack(Material.DIAMOND);
        ItemMeta meta = diamond.getItemMeta();
        meta.displayName(Component.text("Hello from RealmIT"));
        diamond.setItemMeta(meta);
        inventory.setItem(CENTER_SLOT, diamond);

        ItemStack wss = new ItemStack(Material.WITHER_SKELETON_SKULL);
        wss.lore(List.of(Component.text("This is a lore line")));
        meta = wss.getItemMeta();
        meta.displayName(Component.text("Teleport me"));
        wss.setItemMeta(meta);

        inventory.setItem(FIRST_SLOT, wss);

        player.openInventory(inventory);
    }
}
