package com.semivanilla.custommotd.commands.subcommands;

import com.semivanilla.custommotd.CustomMOTD;
import com.semivanilla.custommotd.commands.SubCommand;
import com.semivanilla.custommotd.manager.wrapper.MOTDWrapper;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

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
        String motd =  String.join(" ", Arrays.copyOfRange(args,1, args.length));
        MOTDWrapper wrapper = CustomMOTD.getMotdManager().getMOTD(motd);
        if (wrapper == null) {
            sender.sendMessage("There's no motd by this name. " + motd);
            return;
        }
        switch (option.toLowerCase()) {
            case "increase", "up" -> {
                wrapper.changeCounter(+1);
                CustomMOTD.getMotdManager().updateCounterMOTD();
            }
            case "decrease", "down" -> {
                wrapper.changeCounter(-1);
                CustomMOTD.getMotdManager().updateCounterMOTD();
            }
        }
    }

}
