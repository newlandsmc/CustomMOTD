package com.semivanilla.custommotd.commands;

import java.util.ArrayList;
import java.util.List;

import com.semivanilla.custommotd.commands.subcommands.*;
import org.bukkit.command.CommandSender;

public abstract class SubCommand {

    private static List<SubCommand> commands;

    public abstract String getSub();
    public abstract String getPermission();
    public abstract String getDescription();
    public abstract String getSyntax();

    public abstract void onCommand(CommandSender sender, String[] args);

    public static void loadCommands() {
        commands = new ArrayList<SubCommand>();

        loadCommand(new ApplySubCommand(), new CheckSubCommand(), new CreateSubCommand(),
                    new DefaultSubCommand(), new HelpSubCommand(), new ListSubCommand(),
                    new ReloadSubCommand());
    }

    private static void loadCommand(SubCommand ... subs) {
        for (SubCommand sub: subs) {
            commands.add(sub);
            MOTDCommand.commands.add(sub.getSub());
        }
    }

    public static List<SubCommand> getCommands() {
        return commands;
    }
}
