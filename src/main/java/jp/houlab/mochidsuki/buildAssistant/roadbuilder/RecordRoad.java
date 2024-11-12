package jp.houlab.mochidsuki.buildAssistant.roadbuilder;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class RecordRoad extends BukkitRunnable {
    Player player;
    List<Axes> axes = new ArrayList<Axes>();
    public RecordRoad(Player player) {
        this.player = player;
    }
    @Override
    public void run() {
        Axes axes = new Axes();
        axes.setX(player.getLocation().getX());
        axes.setY(player.getLocation().getY());
        axes.setZ(player.getLocation().getZ());
        this.axes.add(axes);
        BuildRoad.RoadAxes.put(player,this.axes);
    }
}
