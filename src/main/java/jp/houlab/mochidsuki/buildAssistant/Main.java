package jp.houlab.mochidsuki.buildAssistant;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    static public Plugin plugin;
    @Override
    public void onEnable() {
        // Plugin startup logic
        getCommand("buildRoad").setExecutor(new CommandListener());

        plugin = this;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
