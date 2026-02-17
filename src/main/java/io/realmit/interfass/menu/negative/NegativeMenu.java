package io.realmit.interfass.menu.negative;

import io.realmit.interfass.menu.MenuInterface;
import io.realmit.interfass.enums.NegativeCharactersEnum;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public final class NegativeMenu implements MenuInterface
{
    public void open(Player player)
    {
//        String title = InterfassCharRepo.getNeg(8) + "&0" + InterfassCharRepo.MENU_CONTAINER_27;
        String title = NegativeCharactersEnum.getNeg(8) + "&f" + NegativeCharactersEnum.MENU_TEST;
        Component titleComponent = LegacyComponentSerializer.legacyAmpersand().deserialize(title);
        Inventory inventory = Bukkit.createInventory(null, 54, titleComponent);

        // Fill with an object
        ItemStack item = new ItemStack(Material.NETHER_STAR);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text("Open the Interfass menu"));
        item.setItemMeta(meta);

        for (int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, item);

        }

        player.openInventory(inventory);
    }
}
