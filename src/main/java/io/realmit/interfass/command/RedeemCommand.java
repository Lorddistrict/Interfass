package io.realmit.interfass.command;

import io.realmit.interfass.api.service.PendingItemStoreService;
import io.realmit.interfass.services.PlayerActionsService;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.List;

public final class RedeemCommand implements CommandExecutor {

    private final PendingItemStoreService pendingItemStoreService;
    private final PlayerActionsService playerActionsService;


    public RedeemCommand(
            PlayerActionsService playerActionsService,
            PendingItemStoreService pendingItemStoreService
    ) {
        this.playerActionsService = playerActionsService;
        this.pendingItemStoreService = pendingItemStoreService;
    }

    @Override
    public boolean onCommand(
            @NonNull CommandSender sender,
            @NotNull Command command,
            @NotNull String label,
            String @NonNull [] args
    ) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        if (!playerActionsService.canPlayerRedeemItems(player)) {
            return true;
        }

        List<ItemStack> items = pendingItemStoreService.consumePendingItems(player.getUniqueId());

        if (items.isEmpty()) {
            return true;
        }

        for (ItemStack item : items) {
            player.getInventory().addItem(item);
        }

        player.sendMessage(Component.text("Vous avez reçu vos items en attente !"));

        return true;
    }
}
