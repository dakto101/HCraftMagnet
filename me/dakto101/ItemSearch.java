// 
// Decompiled by Procyon v0.5.36
// 

package me.dakto101;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.inventory.ItemStack;

public class ItemSearch implements Listener, Runnable {

	@Override
	public void run() {
		double maxDistance = HCraftMagnetConfig.maxDistance;
		for (Player p : Bukkit.getServer().getOnlinePlayers()) {
			if (p.getInventory().getItemInMainHand().equals(HCraftMagnetConfig.magnet)) {
				for (Entity entity : p.getNearbyEntities(maxDistance, maxDistance, maxDistance)) {
                    if (!(entity instanceof Item)) {
                        continue;
                    }
                    Item item = (Item) entity;
                    ItemStack stack = item.getItemStack();
                    Location location = item.getLocation();
                    if (stack.getAmount() <= 0 || item.isDead()) {
                        continue;
                    }
                    if (item.getPickupDelay() > item.getTicksLived()) {
                        continue;
                    }
                    Player closestPlayer = null;
                    double distanceSmall = maxDistance;
                    if (p != null) {
                    	double playerDistance = p.getLocation().distance(location);
                    	if (playerDistance >= distanceSmall) {
                    		continue;
                    	}
                    	distanceSmall = playerDistance;
                    	closestPlayer = p;
                    }
                    if (closestPlayer == null) {
                    	continue;
                    }
                    item.setVelocity(closestPlayer.getLocation().toVector().subtract(item.getLocation().toVector()).normalize());
				}
			}
		}
	}
	
	@EventHandler
	public void onEnable(PluginEnableEvent e) {
		if (!e.getPlugin().getName().equals(HCraftMagnet.plugin.getName())) return;
		Bukkit.getScheduler().scheduleSyncRepeatingTask(HCraftMagnet.plugin, this, 51L, 5L);
	}

}

