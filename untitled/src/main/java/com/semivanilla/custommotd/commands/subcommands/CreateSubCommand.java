package com.semivanilla.custommotd.commands.subcommands;

import com.semivanilla.custommotd.commands.SubCommand;
import org.bukkit.command.CommandSender;

public class CreateSubCommand extends SubCommand {

    @Override
    public String getSub() {
        return "create";
    }

    @Override
    public String getPermission() {
        return "custommotd.create";
    }

    @Override
    public String getDescription() {
        return "Create a new MOTD.";
    }

    @Override
    public String getSyntax() {
        return "/custommotd create";
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {

    }

}
