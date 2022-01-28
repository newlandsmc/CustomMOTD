package com.semivanilla.custommotd.commands;

import com.semivanilla.custommotd.config.Config;
import com.semivanilla.custommotd.util.Util;
import net.kyori.adventure.text.minimessage.Template;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MOTDCommand implements CommandExecutor, TabCompleter  {

    public static List<String> commands = new ArrayList<>();

    public MOTDCommand() {
        SubCommand.loadCommands();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            Util.sendMiniMessage(sender, Config.commandUsage, null);
            return true;
        }
        String command = args[0];
        if (commands.contains(command)) {
            SubCommand subCommand = SubCommand.getCommands().get(command);
            if (subCommand.getPermission() != null) {
                if (!sender.hasPermission(subCommand.getPermission())) {
                    Util.sendMiniMessage(sender, Config.noPermission, List.of(Template.template("permission", subCommand.getPermission())));
                    return true;
                }
            }
            subCommand.onCommand(sender, Arrays.copyOfRange(args,1, args.length));
            return true;
        }
        Util.sendMiniMessage(sender, Config.commandUsage, null);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            StringUtil.copyPartialMatches(args[0], commands, completions);
        } else if (args.length > 1) {
            String command = args[0];
            if (commands.contains(command)) {
                SubCommand subCommand = SubCommand.getCommands().get(command);
                StringUtil.copyPartialMatches(args[1], subCommand.getCompletions(), completions);
            }
        }
        Collections.sort(completions);
        return completions;
    }
}
