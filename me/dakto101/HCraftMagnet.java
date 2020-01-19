package me.dakto101;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;


public class HCraftMagnet extends JavaPlugin {
	
	private static String PERMISSION_ADMIN = "hcraftmagnet.admin";
	public static Plugin plugin;
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("magnet") || cmd.getName().equalsIgnoreCase("hcraftmagnet")) {
			if (args.length == 0) {
				sender.sendMessage("§6======HCraftMagnet======");
				sender.sendMessage("§6/magnet item: Lấy nam châm hút đồ. §e(" + PERMISSION_ADMIN + ")");
				sender.sendMessage("§6/magnet reload: Reload config. §e(" + PERMISSION_ADMIN + ")");
				return true;
			}
        	if (args.length == 1) {
				if ((args[0].toString().equals("reload") && (sender.hasPermission(PERMISSION_ADMIN)))) {
					try {
						HCraftMagnetConfig.reloadConfig();
					} catch (Exception e) {
						sender.sendMessage("§c[HCraftMagnet]: Có lỗi xảy ra khi reload.");
					} finally {
						sender.sendMessage("§a[HCraftMagnet]: Reload thanh cong.");
					}
				}
				if ((args[0].toString().equals("item") && (sender.hasPermission(PERMISSION_ADMIN)))) {
					if (!(sender instanceof Player)) sender.sendMessage("§c[HCraftMagnet]: Lenh nay chi danh cho nguo choi.");
					Player p = (Player) sender;
					try {
						ItemStack item = HCraftMagnetConfig.magnet;
						if (item != null) p.getInventory().addItem(item);
					} catch (Exception e) {
						p.sendMessage("§c[HCraftMagnet]: Có lỗi xảy ra khi lấy vật phẩm nam châm.");
					} finally {
						p.sendMessage("§a[HCraftMagnet]: Nhận được nam châm hút đồ!");
					}
					
				}
				return true;
			}
        	return true;
        }
        return false;
    }
    
    public void onEnable() {
    	plugin = this;
    	HCraftMagnetConfig.setup();
    	Bukkit.getPluginManager().registerEvents(new ItemSearch(), this);
    }
    
}
