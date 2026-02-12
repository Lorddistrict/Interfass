package io.realmit.interfass.listener.passQuest;

import io.realmit.interfass.menu.InterfassMenu;
import io.realmit.interfass.services.InterfassLogger;
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

public final class InterfassPassQuestListener implements Listener {
    private final InterfassLogger logger;
    private final InterfassMenu menu;

    public InterfassPassQuestListener(
            @NotNull InterfassLogger logger,
            @NotNull InterfassMenu menu
    ) {
        this.logger = logger;
        this.menu = menu;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
    }
}
