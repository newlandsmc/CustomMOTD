package com.semivanilla.custommotd.commands.subcommands;

import com.semivanilla.custommotd.CustomMOTD;
import com.semivanilla.custommotd.commands.SubCommand;
import com.semivanilla.custommotd.config.Config;
import com.semivanilla.custommotd.manager.wrapper.MOTDWrapper;
import org.bukkit.command.CommandSender;

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
        return "/custommotd list";
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        List<String> titles = CustomMOTD.getInstance().getMotdManager().getMotds().stream().map(MOTDWrapper::getTitle).collect(Collectors.toList());
        sender.sendMessage(Config.CommandList.replaceAll("<list>", String.join(", ", titles)));
    }

}
