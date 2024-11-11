package jp.houlab.mochidsuki.buildAssistant;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class CommandListener implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(command.getName().equalsIgnoreCase("buildassistant")) {
            if(strings.length != 0 && strings[0].equalsIgnoreCase("start")) {

            }
        }

        return false;
    }
}
