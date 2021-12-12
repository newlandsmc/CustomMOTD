package com.eliaseeg.custommotd;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class PingListener implements Listener {

	@EventHandler
	public void onPing(ServerListPingEvent event) {
		int found = 0;
		for (String key : CustomMOTD.getInstance().getConfig().getConfigurationSection("CUSTOM_MOTDS").getKeys(false)) {
			if (CustomMOTD.getInstance().getConfig().getBoolean("CUSTOM_MOTDS." + key + ".ACTIVE")) {
				found++;
				List<String> lines = this.colorList(CustomMOTD.getInstance().getConfig().getStringList("CUSTOM_MOTDS." + key + ".LINES"));
				event.setMotd(lines.get(0) + "\n" + lines.get(1));
			}
		}
		
		// There's none custom motd running so we should show the secundary if the count is 1 or greater if not
		// send the default one
		if (found < 1) {
			int counter = CustomMOTD.getInstance().getConfig().getInt("COUNTER");
			
			if (counter < 1) {
				List<String> lines = this.colorList(CustomMOTD.getInstance().getConfig().getStringList("DefaultMOTD"));
				event.setMotd(lines.get(0) + "\n" + lines.get(1));
			}else if (counter >= 1) {
				List<String> lines = this.colorList(CustomMOTD.getInstance().getConfig().getStringList("SecondaryMOTD"));
				event.setMotd(lines.get(0) + "\n" + lines.get(1));
			}
			
		}
	}

	private List<String> colorList(List<String> list){
		List<String> lines = new ArrayList<String>();
		
		for (String s : list) {
			lines.add(ChatColor.translateAlternateColorCodes('&', s));
		}
		
		return lines;
	}
}