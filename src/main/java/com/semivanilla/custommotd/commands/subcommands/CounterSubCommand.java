package com.semivanilla.custommotd.commands.subcommands;

import com.semivanilla.custommotd.CustomMOTD;
import com.semivanilla.custommotd.commands.SubCommand;
import com.semivanilla.custommotd.config.Config;
import com.semivanilla.custommotd.manager.wrapper.MOTDWrapper;
import com.semivanilla.custommotd.util.Util;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;

public class CounterSubCommand extends SubCommand {

    private static final List<String> completions = Arrays.asList("increase", "decrease");

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
            Util.sendMiniMessage(sender, Config.MOTDSetFailed, null);
            return;
        }
        TagResolver placeholders = TagResolver.resolver(
                Placeholder.parsed("motd", wrapper.getTitle())
        );
        switch (option.toLowerCase()) {
            case "increase", "up" -> {
                wrapper.changeCounter(+1);
                CustomMOTD.getMotdManager().updateCounterMOTD();
                Util.sendMiniMessage(sender, Config.counterIncreased, placeholders);
            }
            case "decrease", "down" -> {
                wrapper.changeCounter(-1);
                CustomMOTD.getMotdManager().updateCounterMOTD();
                Util.sendMiniMessage(sender, Config.counterDecreased, placeholders);
            }
        }
    }

    @Override
    public List<String> getCompletions() {
        return completions;
    }

}
