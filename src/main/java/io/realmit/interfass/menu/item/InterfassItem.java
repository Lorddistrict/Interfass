package io.realmit.interfass.menu.item;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class InterfassItem {

    public ItemStack getItem(
            ItemStack itemStack,
            List<String> itemNames,
            List<NamedTextColor> itemNamesColor,
            List<String> itemLores,
            List<NamedTextColor> itemLoresColor
    ) {
        if (!this.isValidItemData(itemNames, itemNamesColor, itemLores, itemLoresColor)) {
            return itemStack;
        }

        ItemStack updatedItemStack = setItemNamesAndColors(itemStack, itemNames, itemNamesColor);

        return setItemLoresAndColors(updatedItemStack, itemLores, itemLoresColor);
    }

    private ItemStack setItemNamesAndColors(
            ItemStack itemStack,
            List<String> itemNames,
            List<NamedTextColor> itemNamesColor
    ) {
        ItemMeta meta = itemStack.getItemMeta();
        Component name = Component.text().asComponent();

        for (int i = 0; i < itemNames.size(); i++) {
            name = name.append(Component.text(itemNames.get(i), itemNamesColor.get(i)));

            if (i < itemNames.size() - 1) {
                name = name.append(Component.text(" "));
            }
        }

        meta.displayName(name);
        itemStack.setItemMeta(meta);

        return itemStack;
    }

    private ItemStack setItemLoresAndColors(
            ItemStack itemStack,
            List<String> itemLores,
            List<NamedTextColor> itemLoresColor
    ) {
        ItemMeta meta = itemStack.getItemMeta();
        List<Component> coloredLores = new ArrayList<>();

        for (int i = 0; i < itemLores.size(); i++) {
            coloredLores.add(Component.text(itemLores.get(i), itemLoresColor.get(i)));
        }

        meta.lore(coloredLores);
        itemStack.setItemMeta(meta);

        return itemStack;
    }

    private boolean isValidItemData(
            List<String> itemNames,
            List<NamedTextColor> itemNamesColor,
            List<String> itemLores,
            List<NamedTextColor> itemLoresColor
    ) {
        if (itemNames.size() != itemNamesColor.size()) {
            return false;
        }

        return itemLores.size() == itemLoresColor.size();
    }
}
