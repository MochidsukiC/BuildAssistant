package jp.houlab.mochidsuki.buildAssistant;

import jp.houlab.mochidsuki.buildAssistant.roadbuilder.BuildRoad;
import jp.houlab.mochidsuki.buildAssistant.roadbuilder.BuildRoadUnder;
import jp.houlab.mochidsuki.buildAssistant.roadbuilder.RecordRoad;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import static jp.houlab.mochidsuki.buildAssistant.Main.plugin;

public class CommandListener implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(command.getName().equalsIgnoreCase("buildRoad")) {
            if(strings.length != 0 && strings[0].equalsIgnoreCase("auto")) {
                if (strings[1].equalsIgnoreCase("start")) {
                    buildRoadTasks.put((Player) commandSender, new RecordRoad((Player) commandSender).runTaskTimer(plugin, 0, 1));
                } else if (strings[1].equalsIgnoreCase("stop")) {
                    buildRoadTasks.get((Player) commandSender).cancel();
                    buildRoadTasks.remove((Player) commandSender);
                } else if (strings[1].equalsIgnoreCase("set")) {
                    BuildRoad.build((Player) commandSender, Material.STONE, 10);
                }
            }else if (strings.length != 0 && strings[0].equalsIgnoreCase("manual")) {
                if(strings.length >= 2 && strings[1].equalsIgnoreCase("start")) {
                    buildRoadUnderTasks.put((Player) commandSender,new BuildRoadUnder((Player) commandSender,Integer.parseInt(strings[2])).runTaskTimer(plugin, 0, 1));
                } else if (strings.length >= 2 && strings[1].equalsIgnoreCase("stop")) {
                    buildRoadUnderTasks.get((Player) commandSender).cancel();
                }
            }
        }

        return false;
    }

    static HashMap<Player, BukkitTask> buildRoadTasks = new HashMap<>();
    static HashMap<Player, BukkitTask> buildRoadUnderTasks = new HashMap<>();
}
