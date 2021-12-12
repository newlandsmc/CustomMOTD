package com.eliaseeg.custommotd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.md_5.bungee.api.ChatColor;

public class CustomMOTDCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("custommotd")) {
			if (args.length == 0) {
				sender.sendMessage(ChatColor.BOLD + "" + ChatColor.YELLOW + "/custommotd help " + ChatColor.RESET + ChatColor.GRAY + "- Show this help.");
				sender.sendMessage(ChatColor.BOLD + "" + ChatColor.YELLOW + "/custommotd counterup " + ChatColor.RESET + ChatColor.GRAY + "- Adds 1 point to the counter.");
				sender.sendMessage(ChatColor.BOLD + "" + ChatColor.YELLOW + "/custommotd  counterdown " + ChatColor.RESET + ChatColor.GRAY + "- Decrease 1 point to the counter.");
				sender.sendMessage(ChatColor.BOLD + "" + ChatColor.YELLOW + "/custommotd enable <nameofmotd> " + ChatColor.RESET + ChatColor.GRAY + "- Enables a custom motd (will override the boosters motd).");
				sender.sendMessage(ChatColor.BOLD + "" + ChatColor.YELLOW + "/custommotd disable <nameofmotd> " + ChatColor.RESET + ChatColor.GRAY + "- Disables a custom motd.");
			}
			else if (args.length == 1) {
				if (args[0].equalsIgnoreCase("counterup")) {
					if (sender.hasPermission("custommotd.counterup")) {
						CustomMOTD.getInstance().getConfig().set("COUNTER", (CustomMOTD.getInstance().getConfig().getInt("COUNTER") + 1));
						CustomMOTD.getInstance().saveConfig();
						sender.sendMessage("Added 1 to count.");
						return true;
					}else {
						sender.sendMessage(ChatColor.RED + "You don't have permission to do this.");
					}
				}else if (args[0].equalsIgnoreCase("counterdown")) {
					if (sender.hasPermission("custommotd.counterdown")) {
						CustomMOTD.getInstance().getConfig().set("COUNTER", (CustomMOTD.getInstance().getConfig().getInt("COUNTER") - 1));
						CustomMOTD.getInstance().saveConfig();
						sender.sendMessage("Removed 1 from count.");
						return true;
					}else {
						sender.sendMessage(ChatColor.RED + "You don't have permission to do this.");
					}
				}else if (args[0].equalsIgnoreCase("help")) {
					sender.sendMessage(ChatColor.BOLD + "" + ChatColor.YELLOW + "/custommotd help " + ChatColor.RESET + ChatColor.GRAY + "- Show this help.");
					sender.sendMessage(ChatColor.BOLD + "" + ChatColor.YELLOW + "/custommotd counterup " + ChatColor.RESET + ChatColor.GRAY + "- Adds 1 point to the counter.");
					sender.sendMessage(ChatColor.BOLD + "" + ChatColor.YELLOW + "/custommotd  counterdown " + ChatColor.RESET + ChatColor.GRAY + "- Decrease 1 point to the counter.");
					sender.sendMessage(ChatColor.BOLD + "" + ChatColor.YELLOW + "/custommotd enable <nameofmotd> " + ChatColor.RESET + ChatColor.GRAY + "- Enables a custom motd (will override the boosters motd).");
					sender.sendMessage(ChatColor.BOLD + "" + ChatColor.YELLOW + "/custommotd disable <nameofmotd> " + ChatColor.RESET + ChatColor.GRAY + "- Disables a custom motd.");
				}else {
					sender.sendMessage(ChatColor.RED + "Command not found.");
				}
				
			}else if (args.length == 2) {
				
				if (args[0].equalsIgnoreCase("enable")) {
					
					if (sender.hasPermission("custommotd.enable")) {
						int found = 0;
						
						for (String key : CustomMOTD.getInstance().getConfig().getConfigurationSection("CUSTOM_MOTDS").getKeys(true)) {
							if (args[1].equalsIgnoreCase(key)){
								found ++;
								CustomMOTD.getInstance().getConfig().set("CUSTOM_MOTDS." + key + ".ACTIVE", true);
								CustomMOTD.getInstance().saveConfig();
								sender.sendMessage("Succesfully changed motd to true.");
								break;
							}
						}
						
						if (found < 1) {
							sender.sendMessage(ChatColor.RED + "Unable to find a motd with that name.");
						}
						return true;
					}else {
						sender.sendMessage(ChatColor.RED + "You don't have permission to do this.");
					}
				}else if (args[0].equalsIgnoreCase("disable")) {
					
					if (sender.hasPermission("custommotd.disable")) {
						int found = 0;
						
						for (String key : CustomMOTD.getInstance().getConfig().getConfigurationSection("CUSTOM_MOTDS").getKeys(true)) {
							if (args[1].equalsIgnoreCase(key)){
								found ++;
								CustomMOTD.getInstance().getConfig().set("CUSTOM_MOTDS." + key + ".ACTIVE", false);
								CustomMOTD.getInstance().saveConfig();
								sender.sendMessage("Succesfully changed motd to false.");
								break;
							}
						}
						
						if (found < 1) {
							sender.sendMessage(ChatColor.RED + "Unable to find a motd with that name.");
						}
						return true;
						
					}else {
						sender.sendMessage(ChatColor.RED + "You don't have permission to do this.");
					}
				}else {
					sender.sendMessage(ChatColor.RED + "Command not found.");
				}
			}
		}
		
		return false;
	}

}
