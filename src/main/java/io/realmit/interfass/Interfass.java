package io.realmit.interfass;

import io.realmit.interfass.api.http.ApiServer;
import io.realmit.interfass.command.InterfassCommand;
import io.realmit.interfass.config.QuestMenuConfig;
import io.realmit.interfass.listener.InterfassItemListener;
import io.realmit.interfass.listener.InterfassItemListenerTeleport;
import io.realmit.interfass.listener.InterfassTeleportClickListener;
import io.realmit.interfass.listener.passQuest.InterfassPassQuestListener;
import io.realmit.interfass.menu.InterfassMenu;
import io.realmit.interfass.menu.InterfassTeleportMenu;
import io.realmit.interfass.menu.negative.InterfassNegativeMenu;
import io.realmit.interfass.menu.passQuests.InterfassPassQuestsMenu;
import io.realmit.interfass.services.InterfassLogger;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class Interfass extends JavaPlugin {

    @Override
    public void onEnable() {
        InterfassLogger logger = new InterfassLogger(this);

        logger.separator();
        logger.info("[INTERFASS] > Initialization complete.");

        // >>> API
        ApiServer apiServer = new ApiServer(this, 8081);
        try {
            apiServer.start();
        } catch (Exception e) {
            getLogger().severe("Failed to start API server: " + e.getMessage());
            e.printStackTrace();
        }
        // <<< API

        // ---- custom configs
        //this.saveDefaultConfig(); // if you also use config.yml
        QuestMenuConfig questMenuConfig = new QuestMenuConfig(this);

        // ---- interfassMenu
        InterfassMenu interfassMenu = new InterfassMenu();
        PluginCommand cmd = getCommand("interfass");

        if (null == cmd) {
            logger.separator();
            getLogger().severe("Command 'interfass' not found in plugin.yml, disabling plugin.");
            return;
        }

        cmd.setExecutor(new InterfassCommand(interfassMenu));

        // ---- interfassTeleportMenu
        InterfassTeleportMenu interfassTeleportMenu = new InterfassTeleportMenu();
        cmd = getCommand("teleport");

        if (null == cmd) {
            logger.separator();
            getLogger().severe("Command 'interfass:teleport' not found in plugin.yml, disabling plugin.");
            return;
        }

        cmd.setExecutor(new InterfassCommand(interfassTeleportMenu));

        // ---- interfassPassQuestsMenu
        InterfassPassQuestsMenu interfassPassQuestsMenu = new InterfassPassQuestsMenu(questMenuConfig);
        cmd = getCommand("quest");

        if (null == cmd) {
            logger.separator();
            getLogger().severe("Command 'interfass:quest' not found in plugin.yml, disabling plugin.");
            return;
        }

        cmd.setExecutor(new InterfassCommand(interfassPassQuestsMenu));

        // >>> interfassNegativeMenu
        InterfassNegativeMenu interfassNegativeMenu = new InterfassNegativeMenu();
        cmd = getCommand("negative");
        if (null == cmd) {
            logger.separator();
            getLogger().severe("Command 'interfass:negative' not found in plugin.yml, disabling plugin.");
            return;
        }
        cmd.setExecutor(new InterfassCommand(interfassNegativeMenu));
        // <<< interfassNegativeMenu

        // ---- events
        getServer().getPluginManager().registerEvents(new InterfassItemListener(logger, interfassMenu), this);
        getServer().getPluginManager().registerEvents(new InterfassItemListenerTeleport(logger), this);
        getServer().getPluginManager().registerEvents(new InterfassTeleportClickListener(), this);
        getServer().getPluginManager().registerEvents(new InterfassPassQuestListener(), this);
    }
}
