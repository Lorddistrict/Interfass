package io.realmit.interfass;

import io.realmit.interfass.api.http.ApiServer;
import io.realmit.interfass.api.listener.PendingItemJoinListener;
import io.realmit.interfass.api.service.PendingItemStoreService;
import io.realmit.interfass.command.InterfassMenuCommand;
import io.realmit.interfass.command.InterfassOfflineItemsCommand;
import io.realmit.interfass.config.QuestMenuConfig;
import io.realmit.interfass.listener.InterfassItemListener;
import io.realmit.interfass.listener.InterfassItemListenerTeleport;
import io.realmit.interfass.listener.InterfassTeleportClickListener;
import io.realmit.interfass.listener.passQuest.InterfassPassQuestListener;
import io.realmit.interfass.menu.InterfassMenu;
import io.realmit.interfass.menu.InterfassMenuInterface;
import io.realmit.interfass.menu.InterfassTeleportMenu;
import io.realmit.interfass.menu.passQuests.InterfassPassQuestsMenu;
import io.realmit.interfass.services.PlayerActionsService;
import org.bukkit.Bukkit;
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
        InterfassMenu interfassMenu = new InterfassMenu();

        registerMenuCommand("interfass", interfassMenu);
        registerMenuCommand("teleport", new InterfassTeleportMenu());
        registerMenuCommand("quest", new InterfassPassQuestsMenu(questMenuConfig));
        registerMenuCommand("negative", new InterfassPassQuestsMenu(questMenuConfig));
        // <<< Register commands

        // >>> interfassNegativeMenu
        PluginCommand cmd = getCommand("redeem");
        if (null == cmd) {
            getLogger().severe("Command 'interfass:redeem' not found in plugin.yml, disabling plugin.");
            return;
        }
        InterfassOfflineItemsCommand command = new InterfassOfflineItemsCommand(
                playerActionsService,
                pendingItemStoreService
        );
        cmd.setExecutor(command);
        // <<< interfassNegativeMenu

        // Register events
        getServer().getPluginManager().registerEvents(new InterfassItemListener(interfassMenu), this);
        getServer().getPluginManager().registerEvents(new InterfassItemListenerTeleport(), this);
        getServer().getPluginManager().registerEvents(new InterfassTeleportClickListener(), this);
        getServer().getPluginManager().registerEvents(new InterfassPassQuestListener(), this);
    }

    private void registerMenuCommand(String cmdName, InterfassMenuInterface interfassMenuInterface) {
        PluginCommand cmd = getCommand(cmdName);

        if (null == cmd) {
            getLogger().severe("Command 'interfass:" + cmdName + "' not found in plugin.yml, disabling plugin.");
            return;
        }

        cmd.setExecutor(new InterfassMenuCommand(interfassMenuInterface));
    }
}
