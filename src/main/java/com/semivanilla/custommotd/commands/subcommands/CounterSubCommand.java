package com.semivanilla.custommotd.commands.subcommands;

import com.semivanilla.custommotd.CustomMOTD;
import com.semivanilla.custommotd.commands.SubCommand;
import com.semivanilla.custommotd.manager.wrapper.MOTDWrapper;
import org.bukkit.command.CommandSender;

public class CounterSubCommand extends SubCommand {

    @Override
    public String getSub() {
        return "counter";
    }

    @Override
    public String getPermission() {
        return "custommotd.counter";
    }

    @Override
    public String getDescription() {
        return "Change the override counter value.";
    }

    @Override
    public String getSyntax() {
        return "usage: /custommotd count increase|decrease";
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(getSyntax());
            return;
        }
        String option = args[0];
        switch (option.toLowerCase()) {
            case "increase":
                CustomMOTD.getMotdManager().changeCounter(+1);
                break;
            case "decrease":
                CustomMOTD.getMotdManager().changeCounter(-1);
                break;
        }
    }

}
