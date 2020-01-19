package me.dakto101;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class HCraftMagnetConfig {

	private static File file;
	public static FileConfiguration config;
	private static Plugin plugin = HCraftMagnet.plugin;
	public static ItemStack magnet;
	public static double maxDistance = 8;
	
	public static final String FILENAME = "config.yml";	
	
	public static void setup() {
		file = new File(plugin.getDataFolder(), FILENAME);
		if (!plugin.getDataFolder().exists()) plugin.getDataFolder().mkdirs();
		if (!file.exists()) {
			try {
				file.createNewFile();
				loadConfig();
				configLoadDefaultData(config);
				Bukkit.getServer().getConsoleSender().sendMessage("§aTao file thanh cong: " + FILENAME);
			} catch (IOException e) {
				Bukkit.getServer().getConsoleSender().sendMessage("§cKhong the tao ra file " + FILENAME);
			}
		}
		loadConfig();
	    //
		
	}
	public static void saveConfig() {
	    try {
			config.save(file);
		} catch (IOException e) {
			Bukkit.getServer().getConsoleSender().sendMessage("§cKhong the luu file " + FILENAME);
			e.printStackTrace();
		}
	}
	public static void loadConfig() {
		config = YamlConfiguration.loadConfiguration(file);
		magnet = config.getItemStack("item.magnet");
		maxDistance = config.getDouble("max-distance");
	}
	public static void reloadConfig() {
		loadConfig();
		setup();
		saveConfig();
		magnet = config.getItemStack("item.magnet");
		maxDistance = config.getDouble("max-distance");
	}
	
	public static void configLoadDefaultData(FileConfiguration config) {
		if (config == null) return;

		ItemStack item = new ItemStack(Material.IRON_INGOT);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName("§f§lNam châm hút đồ");
		List<String> lore = new ArrayList<String>();
		lore.add("§7Cầm nam châm trên tay chính sẽ tự động hút đồ trên mặt đất.");
		lore.add("§7Bán kính hoạt động: §c8 §7ô.");
		itemMeta.setLore(lore);
		item.setItemMeta(itemMeta);
		
		
	    
	    Hashtable<String,Object> hash = new Hashtable<String, Object>();
	    hash.put("max-distance", 8);

	    for (String string : hash.keySet()) {
	    	config.set(string, hash.get(string));
	    }
	    
	    config.set("item.magnet", item);
	    magnet = config.getItemStack("item.magnet");
	    maxDistance = config.getDouble("max-distance");
	    saveConfig();
	}
	
	
}
