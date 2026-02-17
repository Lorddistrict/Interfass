package io.realmit.interfass.command;

import io.realmit.interfass.menu.MenuInterface;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

public final class MenuCommand implements CommandExecutor {

    private final MenuInterface menu;

    public MenuCommand(MenuInterface menu) {
        this.menu = menu;
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

        menu.open(player);
        return true;
    }
}
