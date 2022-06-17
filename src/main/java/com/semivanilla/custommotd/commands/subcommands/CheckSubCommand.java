package com.semivanilla.custommotd.commands.subcommands;

import com.semivanilla.custommotd.CustomMOTD;
import com.semivanilla.custommotd.commands.SubCommand;
import com.semivanilla.custommotd.config.Config;
import com.semivanilla.custommotd.manager.wrapper.MOTDWrapper;
import com.semivanilla.custommotd.util.Util;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

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
        return "usage: /custommotd check";
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        String motd = "default";
        MOTDWrapper wrapper = CustomMOTD.getMotdManager().getActiveMOTD();
        if (wrapper != null) {
            motd = wrapper.getTitle();
        }
        TagResolver templates = TagResolver.resolver(
                Placeholder.parsed("motd", motd)
        );
        Util.sendMiniMessage(sender, Config.commandCheck, templates);
    }

}
