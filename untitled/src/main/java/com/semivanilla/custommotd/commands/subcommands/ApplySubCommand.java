package com.semivanilla.custommotd.commands.subcommands;

import com.semivanilla.custommotd.commands.SubCommand;
import org.bukkit.command.CommandSender;

public class ApplySubCommand extends SubCommand {

    @Override
    public String getSub() {
        return "apply";
    }

    @Override
    public String getPermission() {
        return "custommotd.apply";
    }

    @Override
    public String getDescription() {
        return "Activate a custom motd to be shown in the server status list.";
    }

    @Override
    public String getSyntax() {
        return "/custommotd apply <name>";
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {

    }

}
