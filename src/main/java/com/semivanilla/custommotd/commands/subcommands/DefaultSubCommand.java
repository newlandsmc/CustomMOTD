package com.semivanilla.custommotd.commands.subcommands;

import com.semivanilla.custommotd.CustomMOTD;
import com.semivanilla.custommotd.commands.SubCommand;
import com.semivanilla.custommotd.config.Config;
import com.semivanilla.custommotd.manager.wrapper.MOTDWrapper;
import com.semivanilla.custommotd.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.Template;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

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
        return "usage: /custommotd default";
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        MOTDWrapper wrapper = CustomMOTD.getMotdManager().getActiveMOTD();
        if (wrapper == null) {
            sender.sendMessage("MOTD is already the default.");
            return;
        }
        CustomMOTD.getMotdManager().activateMOTD(null);
        sender.sendMessage("MOTD is now the default.");
    }

}
