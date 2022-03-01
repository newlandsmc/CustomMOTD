package com.semivanilla.custommotd.commands.subcommands;

import com.semivanilla.custommotd.CustomMOTD;
import com.semivanilla.custommotd.commands.SubCommand;
import com.semivanilla.custommotd.config.Config;
import com.semivanilla.custommotd.manager.MOTDManager;
import com.semivanilla.custommotd.manager.wrapper.MOTDWrapper;
import com.semivanilla.custommotd.util.Util;
import net.kyori.adventure.text.minimessage.Template;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.stream.Collectors;

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
        MOTDManager motdManager = CustomMOTD.getMotdManager();
        String motd = String.join(" ", args);
        if (motd.equalsIgnoreCase("default")) {
            MOTDWrapper wrapper = motdManager.getActiveMOTD();
            if (motdManager.isDefaultMOTD(wrapper)) {
                Util.sendMiniMessage(sender, Config.MOTDSetDefaultFailed, null);
                return;
            }
            motdManager.activateDefaultMOTD();
            Util.sendMiniMessage(sender, Config.MOTDSetDefault, null);
            return;
        }
        MOTDWrapper wrapper = motdManager.getMOTD(motd);
        if (wrapper == null) {
            Util.sendMiniMessage(sender, Config.MOTDSetFailed, null);
            return;
        }

        if (wrapper.isRestricted()) {
            Util.sendMiniMessage(sender, Config.MOTDSetRestricted, null);
            return;
        }
        motdManager.activateMOTD(wrapper);
        Util.sendMiniMessage(sender, Config.MOTDSet, List.of(Template.template("motd", wrapper.getTitle())));
    }

    @Override
    public List<String> getCompletions() {
        return CustomMOTD.getMotdManager().getMotds().stream().filter(motdWrapper -> !motdWrapper.isRestricted()).map(MOTDWrapper::getTitle).collect(Collectors.toList());
    }

}
