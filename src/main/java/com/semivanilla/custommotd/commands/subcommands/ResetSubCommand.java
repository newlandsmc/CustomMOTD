package com.semivanilla.custommotd.commands.subcommands;

import com.semivanilla.custommotd.CustomMOTD;
import com.semivanilla.custommotd.commands.SubCommand;
import com.semivanilla.custommotd.manager.wrapper.MOTDWrapper;
import org.bukkit.command.CommandSender;

public class ResetSubCommand extends SubCommand {

    @Override
    public String getSub() {
        return "reset";
    }

    @Override
    public String getPermission() {
        return "custommotd.reset";
    }

    @Override
    public String getDescription() {
        return "Revert to default motd and reset any trigger that causes a custommotd to activate.";
    }

    @Override
    public String getSyntax() {
        return "usage: /custommotd reset";
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        CustomMOTD.getMotdManager().resetMotds();
        sender.sendMessage("All triggers that cause a custom motd to activate have been reset."); // TODO : Load from config
    }

}
