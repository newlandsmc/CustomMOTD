package com.semivanilla.custommotd.commands.subcommands;

import com.semivanilla.custommotd.CustomMOTD;
import com.semivanilla.custommotd.commands.SubCommand;
import com.semivanilla.custommotd.manager.wrapper.MOTDWrapper;
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
        return "usage: /custommotd apply <name>";
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(getSyntax());
            return;
        }
        String motd = String.join(" ", args);
        MOTDWrapper wrapper = CustomMOTD.getMotdManager().getMOTD(motd);
        if (wrapper == null) {
            sender.sendMessage("There's no motd by this name. " + motd);
            return;
        }
        if (wrapper.isRestricted()) {
            sender.sendMessage("You can not set this motd.");
            return;
        }
        CustomMOTD.getMotdManager().activateMOTD(wrapper);
        sender.sendMessage("The new motd is " + wrapper.getTitle());
    }

}
