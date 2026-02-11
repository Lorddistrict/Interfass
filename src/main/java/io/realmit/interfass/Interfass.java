package io.realmit.interfass;

import io.realmit.interfass.command.InterfassCommand;
import io.realmit.interfass.listener.InterfassItemListener;
import io.realmit.interfass.listener.InterfassItemListenerTeleport;
import io.realmit.interfass.listener.InterfassTeleportClickListener;
import io.realmit.interfass.menu.InterfassMenu;
import io.realmit.interfass.menu.InterfassTeleportMenu;
import io.realmit.interfass.services.InterfassLogger;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class Interfass extends JavaPlugin {
    @Override
    public void onEnable() {
        InterfassLogger logger = new InterfassLogger(this);

        logger.separator();
        logger.info("[INTERFASS] > Initialization complete.");

        InterfassMenu interfassMenu = new InterfassMenu();
        PluginCommand cmdMenu = getCommand("interfass");

        if (null == cmdMenu) {
            logger.separator();
            getLogger().severe("Command 'interfass' not found in plugin.yml, disabling plugin.");
            return;
        }

        cmdMenu.setExecutor(new InterfassCommand(interfassMenu));

        InterfassTeleportMenu interfassTeleportMenu = new InterfassTeleportMenu();
        PluginCommand cmdTeleport = getCommand("teleport");

        if (null == cmdTeleport) {
            logger.separator();
            getLogger().severe("Command 'interfass:teleport' not found in plugin.yml, disabling plugin.");
            return;
        }

        cmdTeleport.setExecutor(new InterfassCommand(interfassTeleportMenu));

        getServer().getPluginManager().registerEvents(new InterfassItemListener(logger, interfassMenu), this);
        getServer().getPluginManager().registerEvents(new InterfassItemListenerTeleport(logger), this);
        getServer().getPluginManager().registerEvents(new InterfassTeleportClickListener(), this);
    }
}
