package com.semivanilla.custommotd.commands.subcommands;

import com.semivanilla.custommotd.commands.SubCommand;
import org.bukkit.command.CommandSender;

public class CheckSubCommand extends SubCommand {

    @Override
    public String getSub() {
        return "check";
    }

    @Override
    public String getPermission() {
        return "custommotd.list";
    }

    @Override
    public String getDescription() {
        return "Show the current active MOTD.";
    }

    @Override
    public String getSyntax() {
        return "/custommotd check";
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {

    }

}
