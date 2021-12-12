package com.semivanilla.custommotd.commands;

import com.semivanilla.custommotd.config.Config;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// TODO: switch to brigadier.
public class MOTDCommand implements CommandExecutor, TabCompleter  {

    public static List<String> commands = new ArrayList<>();

    public MOTDCommand() {
        SubCommand.loadCommands();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(Config.CommandHelp);
            return true;
        }

        for (SubCommand command : SubCommand.getCommands()) {
            if (!command.getSub().equalsIgnoreCase(args[0])) {
                continue;
            }

            if (command.getPermission() != null) {
                if (!sender.hasPermission(command.getPermission())) {
                    sender.sendMessage(Config.NoPermission);
                    return true;
                }
            }

            command.onCommand(sender, args);
            return true;
        }
        sender.sendMessage(Config.CommandHelp);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            StringUtil.copyPartialMatches(args[0], commands, completions);
        }
        Collections.sort(completions);
        return completions;
    }
}
