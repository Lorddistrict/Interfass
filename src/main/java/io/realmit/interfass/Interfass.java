package io.realmit.interfass;

import io.realmit.interfass.api.http.ApiServer;
import io.realmit.interfass.api.listener.PendingItemJoinListener;
import io.realmit.interfass.api.service.PendingItemStoreService;
import io.realmit.interfass.command.MenuCommand;
import io.realmit.interfass.command.RedeemCommand;
import io.realmit.interfass.config.QuestMenuConfig;
import io.realmit.interfass.listener.ItemListener;
import io.realmit.interfass.listener.ItemListenerTeleport;
import io.realmit.interfass.listener.TeleportClickListener;
import io.realmit.interfass.listener.passQuest.PassQuestListener;
import io.realmit.interfass.menu.DefaultMenu;
import io.realmit.interfass.menu.MenuInterface;
import io.realmit.interfass.menu.teleport.TeleportMenu;
import io.realmit.interfass.menu.passQuests.PassQuestsMenu;
import io.realmit.interfass.services.PlayerActionsService;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class Interfass extends JavaPlugin {

    @Override
    public void onEnable() {
        // >>> API
        ApiServer apiServer = new ApiServer(this, 8081);

        try {
            apiServer.start();
        } catch (Exception e) {
            getLogger().severe("Failed to start API server: " + e.getMessage());
            e.printStackTrace();
        }

        PendingItemStoreService pendingItemStoreService = apiServer.getPendingItemStoreService();
        PlayerActionsService playerActionsService = new PlayerActionsService(pendingItemStoreService);
        PendingItemJoinListener pendingItemJoinListener = new PendingItemJoinListener(
                pendingItemStoreService,
                playerActionsService
        );

        if (null != pendingItemStoreService) {
            Bukkit.getPluginManager().registerEvents(pendingItemJoinListener, this);
        }
        // <<< API

        // >>> custom configs
        QuestMenuConfig questMenuConfig = new QuestMenuConfig(this);
        // <<< custom configs

        // >>> Register commands
        DefaultMenu interfassMenu = new DefaultMenu();

        // >>> Register commands
        registerMenuCommand("interfass", interfassMenu);
        registerMenuCommand("teleport", new TeleportMenu());
        registerMenuCommand("quest", new PassQuestsMenu(questMenuConfig));
        registerMenuCommand("negative", new PassQuestsMenu(questMenuConfig));
        registerCommand("redeem", new RedeemCommand(playerActionsService, pendingItemStoreService));
        // <<< Register commands

        // Register events
        getServer().getPluginManager().registerEvents(new ItemListener(interfassMenu), this);
        getServer().getPluginManager().registerEvents(new ItemListenerTeleport(), this);
        getServer().getPluginManager().registerEvents(new TeleportClickListener(), this);
        getServer().getPluginManager().registerEvents(new PassQuestListener(), this);
    }

    private PluginCommand checkCommand(String cmdName) {
        PluginCommand cmd = getCommand(cmdName);
        if (null == cmd) {
            getLogger().severe("Command 'interfass:" + cmdName + "' not found in plugin.yml, disabling plugin.");
        }

        return cmd;
    }

    private void registerCommand(String commandName, CommandExecutor executor) {
        PluginCommand cmd = checkCommand(commandName);
        cmd.setExecutor(executor);
    }

    private void registerMenuCommand(String commandName, MenuInterface interfassMenuInterface) {
        PluginCommand cmd = checkCommand(commandName);
        cmd.setExecutor(new MenuCommand(interfassMenuInterface));
    }
}
