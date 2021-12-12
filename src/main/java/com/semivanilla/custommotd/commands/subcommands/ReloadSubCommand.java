package com.semivanilla.custommotd.commands.subcommands;

import com.semivanilla.custommotd.CustomMOTD;
import com.semivanilla.custommotd.commands.SubCommand;
import com.semivanilla.custommotd.config.Config;
import org.bukkit.command.CommandSender;

public class ReloadSubCommand extends SubCommand {

    @Override
    public String getSub() {
        return "reload";
    }

    @Override
    public String getPermission() {
        return "custommotd.reload";
    }

    @Override
    public String getDescription() {
        return "Reload plugin configuration.";
    }

    @Override
    public String getSyntax() {
        return "/custommotd reload";
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        Config.init();
        CustomMOTD.getInstance().getMotdManager().loadMotds();
    }

}
