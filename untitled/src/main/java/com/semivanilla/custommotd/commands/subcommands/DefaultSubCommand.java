package com.semivanilla.custommotd.commands.subcommands;

import com.semivanilla.custommotd.commands.SubCommand;
import org.bukkit.command.CommandSender;

public class DefaultSubCommand extends SubCommand {

    @Override
    public String getSub() {
        return "default";
    }

    @Override
    public String getPermission() {
        return "custommotd.apply";
    }

    @Override
    public String getDescription() {
        return "Activate the default motd from server.properties.";
    }

    @Override
    public String getSyntax() {
        return "/custommotd default";
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {

    }

}
