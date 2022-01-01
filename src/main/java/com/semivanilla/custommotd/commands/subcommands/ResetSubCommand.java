package com.semivanilla.custommotd.commands.subcommands;

import com.semivanilla.custommotd.CustomMOTD;
import com.semivanilla.custommotd.commands.SubCommand;
import com.semivanilla.custommotd.config.Config;
import com.semivanilla.custommotd.manager.wrapper.MOTDWrapper;
import com.semivanilla.custommotd.util.Util;
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
        Util.sendMiniMessage(sender, Config.MOTDReset, null);
    }

}
