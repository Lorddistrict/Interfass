package io.realmit.interfass.command;

import io.realmit.interfass.menu.InterfassMenuInterface;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

public final class InterfassCommand implements CommandExecutor {

    private final InterfassMenuInterface menu;

    public InterfassCommand(InterfassMenuInterface menu) {
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
