package io.realmit.interfass.menu.passQuests;

import io.realmit.interfass.constants.InterfassMenuConstants;
import io.realmit.interfass.menu.InterfassMenuInterface;
import io.realmit.interfass.menu.item.InterfassItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class InterfassPassQuestsMenu implements InterfassMenuInterface {

    public void open(Player player) {
        Inventory inventory = Bukkit.createInventory(
                null,
                InterfassMenuConstants.DOUBLE_CHEST_SIZE,
                Component.text("Interfass Menu")
        );

        ArrayList<String> itemNames = new ArrayList<>();
        itemNames.add("Quest #1");
        itemNames.add("(Difficile)");

        ArrayList<NamedTextColor> itemNamesColor = new ArrayList<>();
        itemNamesColor.add(NamedTextColor.GOLD);
        itemNamesColor.add(NamedTextColor.GRAY);

        ArrayList<String> itemLores = new ArrayList<>();
        itemLores.add("Lore for quest #1");
        itemLores.add("Lore line n°2");

        ArrayList<NamedTextColor> itemLoresColor = new ArrayList<>();
        itemLoresColor.add(NamedTextColor.RED);
        itemLoresColor.add(NamedTextColor.DARK_BLUE);

        InterfassItem interfassItem = new InterfassItem();
        inventory.setItem(11, interfassItem.getItem(
                new ItemStack(org.bukkit.Material.DIAMOND),
                itemNames,
                itemNamesColor,
                itemLores,
                itemLoresColor
        ));

        player.openInventory(inventory);
    }
}
