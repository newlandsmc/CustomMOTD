package com.semivanilla.custommotd.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.semivanilla.custommotd.commands.subcommands.*;
import org.bukkit.command.CommandSender;

public abstract class SubCommand {

    private static HashMap<String, SubCommand> commands;

    public abstract String getSub();
    public abstract String getPermission();
    public abstract String getDescription();
    public abstract String getSyntax();

    public abstract void onCommand(CommandSender sender, String[] args);

    public static void loadCommands() {
        commands = new HashMap<>();

        loadCommand(
                new HelpSubCommand(),
                new ApplySubCommand(), new CheckSubCommand(), new CreateSubCommand(),
                new DefaultSubCommand(), new ListSubCommand(), new CounterSubCommand(),
                new ReloadSubCommand());
    }

    private static void loadCommand(SubCommand ... subs) {
        for (SubCommand sub: subs) {
            commands.put(sub.getSub(), sub);
            MOTDCommand.commands.add(sub.getSub());
        }
    }

    public static HashMap<String, SubCommand> getCommands() {
        return commands;
    }
}
