package jp.houlab.mochidsuki.buildAssistant.roadbuilder;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class BuildRoadUnder extends BukkitRunnable {
    Player p;
    int thick;
    public BuildRoadUnder(Player player, int thick) {
        p = player;
        this.thick = thick;
    }
    @Override
    public void run() {
        Material material = p.getInventory().getItemInMainHand().getType();
        float yaw = p.getLocation().getYaw();
        for(int i = 0;i < thick;i++) {
            Location loc = new Location(p.getWorld(),(int) (Math.cos(Math.toRadians(yaw)) * (i- (double) thick /2) + p.getLocation().getX()), p.getLocation().getBlockY() - 1, (int) (Math.sin(Math.toRadians(yaw)) * (i- (double) thick /2) + p.getLocation().getZ()));
            if(p.getWorld().getBlockAt(loc).getType() == Material.AIR && !(loc.getBlockX() == p.getLocation().getBlockX() && loc.getBlockZ() == p.getLocation().getBlockZ())) {
                p.getWorld().setType(loc, material);
            }
            if(p.getWorld().getBlockAt(loc.clone().add(0,1,0)).getType() == material) {
                p.getWorld().setType(loc.clone().add(0,1,0), Material.AIR);
            }
        }
    }
}
