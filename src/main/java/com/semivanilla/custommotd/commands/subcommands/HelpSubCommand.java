package com.semivanilla.custommotd.commands.subcommands;

import com.semivanilla.custommotd.commands.SubCommand;
import org.bukkit.command.CommandSender;

public class HelpSubCommand extends SubCommand {

    @Override
    public String getSub() {
        return "help";
    }

    @Override
    public String getPermission() {
        return "custommotd.command";
    }

    @Override
    public String getDescription() {
        return "List all subcommands and it's descriptions.";
    }

    @Override
    public String getSyntax() {
        return "/custommotd help";
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {

    }

}
