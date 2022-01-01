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
import java.util.stream.Collectors;

public class ListSubCommand extends SubCommand {

    @Override
    public String getSub() {
        return "list";
    }

    @Override
    public String getPermission() {
        return "custommotd.list";
    }

    @Override
    public String getDescription() {
        return "List all available MOTD";
    }

    @Override
    public String getSyntax() {
        return "usage: /custommotd list";
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        List<String> titles = CustomMOTD.getMotdManager().getMotds().stream().filter(motdWrapper -> !motdWrapper.isRestricted()).map(MOTDWrapper::getTitle).collect(Collectors.toList());
        titles.add("default");
        List<Template> templates = new ArrayList<>(List.of(
                Template.template("list", String.join(", ", titles))
        ));
        Util.sendMiniMessage(sender, Config.CommandList, templates);
    }

}
