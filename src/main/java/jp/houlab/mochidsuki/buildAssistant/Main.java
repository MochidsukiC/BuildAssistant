package jp.houlab.mochidsuki.buildAssistant;

import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getCommand("buildRoad").setExecutor(new CommandListener());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
