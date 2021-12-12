package com.eliaseeg.custommotd;

import org.bukkit.plugin.java.JavaPlugin;

public class CustomMOTD extends JavaPlugin {

	public static CustomMOTD instance;
	
	@Override
	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(new PingListener(), this);
		this.getServer().getPluginCommand("custommotd").setExecutor(new CustomMOTDCommand());
		this.saveDefaultConfig();
		
		instance = this;
	}
	
	public static CustomMOTD getInstance() {
		return instance;
	}
	
}