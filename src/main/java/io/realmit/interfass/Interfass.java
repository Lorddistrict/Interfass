package io.realmit.interfass;

import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class Interfass extends JavaPlugin
{
    InterfassServices services = new InterfassServices();

    @Override
    public void onEnable() {
        this.displayInitializationMessage();

        InterfassMenu interfassMenu = new InterfassMenu();

        this.handleDefaultCommand(interfassMenu);
        this.initEvents(interfassMenu);
    }

    private void displayInitializationMessage()
    {
        services.printSeparator();
        getLogger().info("[INTERFASS] > Initialization complete.");
        services.printSeparator();
    }

    private void initEvents(InterfassMenu interfassMenu)
    {
        getServer().getPluginManager().registerEvents(new InterfassItemListener(interfassMenu), this);
        getServer().getPluginManager().registerEvents(new InterfassItemListenerTeleport(), this);
    }

    private void handleDefaultCommand(InterfassMenu interfassMenu)
    {
        PluginCommand pluginCommand = getCommand("interfass");

        if (null == pluginCommand) {
            services.printSeparator();
            getLogger().severe("Command 'interfass' not found in plugin.yml, disabling plugin.");
            services.printSeparator();
            return;
        }

        pluginCommand.setExecutor(new InterfassCommand(interfassMenu));
    }
}
