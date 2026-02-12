package io.realmit.interfass.menu.passQuests;

import io.realmit.interfass.config.QuestMenuConfig;
import io.realmit.interfass.menu.InterfassMenuInterface;
import io.realmit.interfass.menu.item.InterfassItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Set;

public class InterfassPassQuestsMenu implements InterfassMenuInterface {

    private static final String DEFAULT_ITEM_COLOR = "WHITE";
    private static final int DEFAULT_MENU_SIZE = 1;
    private static final int DEFAULT_ITEM_SLOT = -1;

    private final QuestMenuConfig questMenuConfig;

    public InterfassPassQuestsMenu(QuestMenuConfig questMenuConfig) {
        this.questMenuConfig = questMenuConfig;
    }

    @Override
    public void open(Player player) {
        FileConfiguration config = questMenuConfig.getConfig();
        ConfigurationSection menuSection = config.getConfigurationSection("menu");

        if (null == menuSection) {
            player.sendMessage(Component.text("Quest menu not configured."));
            return;
        }

        String title = menuSection.getString("title", "Undefined menu title");
        int size = menuSection.getInt("size", DEFAULT_MENU_SIZE);

        Inventory inventory = Bukkit.createInventory(null, size, Component.text(title));
        ConfigurationSection itemsSection = menuSection.getConfigurationSection("items");

        if (null != itemsSection) {
            Set<String> itemKeys = itemsSection.getKeys(false);

            InterfassItem interfassItem = new InterfassItem();

            for (String key : itemKeys) {
                ConfigurationSection itemSection = itemsSection.getConfigurationSection(key);

                if (null == itemSection) {
                    continue;
                }

                int slot = itemSection.getInt("slot", DEFAULT_ITEM_SLOT);

                if (slot < 0 || slot >= size) {
                    continue;
                }

                String materialName = itemSection.getString("material", "STONE");
                Material material = Material.matchMaterial(materialName);

                if (null == material) {
                    material = Material.STONE;
                }

                ArrayList<String> itemNames = new ArrayList<>();
                ArrayList<NamedTextColor> itemNamesColor = new ArrayList<>();

                if (itemSection.isList("name")) {
                    itemSection.getMapList("name").forEach(map -> {
                        Object itemDisplayName = map.get("text");
                        Object itemDisplayColor = map.get("color");

                        if (itemDisplayName != null) {
                            itemNames.add(itemDisplayName.toString());
                            itemNamesColor.add(parseColor(
                                    itemDisplayColor != null ? itemDisplayColor.toString() : DEFAULT_ITEM_COLOR
                            ));
                        }
                    });
                }

                ArrayList<String> itemLores = new ArrayList<>();
                ArrayList<NamedTextColor> itemLoresColor = new ArrayList<>();

                if (itemSection.isList("lore")) {
                    itemSection.getMapList("lore").forEach(map -> {
                        Object itemLoreText = map.get("text");
                        Object itemLoreTextColor = map.get("color");

                        if (itemLoreText != null) {
                            itemLores.add(itemLoreText.toString());
                            itemLoresColor.add(parseColor(
                                    itemLoreTextColor != null ? itemLoreTextColor.toString() : DEFAULT_ITEM_COLOR
                            ));
                        }
                    });
                }

                ItemStack finalItem = interfassItem.getItem(
                        new ItemStack(material),
                        itemNames,
                        itemNamesColor,
                        itemLores,
                        itemLoresColor
                );

                inventory.setItem(slot, finalItem);
            }
        }

        player.openInventory(inventory);
    }

    private NamedTextColor parseColor(String name) {
        try {
            return NamedTextColor.NAMES.value(name.toLowerCase());
        } catch (Exception e) {
            return NamedTextColor.WHITE;
        }
    }
}
