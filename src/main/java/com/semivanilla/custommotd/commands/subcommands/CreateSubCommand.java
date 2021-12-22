package com.semivanilla.custommotd.commands.subcommands;

import com.semivanilla.custommotd.CustomMOTD;
import com.semivanilla.custommotd.commands.SubCommand;
import com.semivanilla.custommotd.conversation.ConversationManager;
import org.bukkit.command.CommandSender;
import org.bukkit.conversations.Conversable;

public class CreateSubCommand extends SubCommand {

    @Override
    public String getSub() {
        return "create";
    }

    @Override
    public String getPermission() {
        return "custommotd.create";
    }

    @Override
    public String getDescription() {
        return "Create a new MOTD.";
    }

    @Override
    public String getSyntax() {
        return "usage: /custommotd create";
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        new ConversationManager(CustomMOTD.getInstance(), (Conversable)sender);
    }

}
