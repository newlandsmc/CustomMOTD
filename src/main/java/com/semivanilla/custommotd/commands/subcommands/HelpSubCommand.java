package com.semivanilla.custommotd.commands.subcommands;

import com.semivanilla.custommotd.commands.SubCommand;
import com.semivanilla.custommotd.config.Config;
import com.semivanilla.custommotd.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.command.CommandSender;

public class HelpSubCommand extends SubCommand {

    @Override
    public String getSub() {
        return "help";
    }

    @Override
    public String getPermission() {
        return "custommotd.command";
    }

    @Override
    public String getDescription() {
        return "List all subcommands and it's descriptions.";
    }

    @Override
    public String getSyntax() {
        return "usage: /custommotd help";
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        final Component[] message = {Util.parseMiniMessage(Config.commandHelpHeader, null)};
        message[0] = message[0].append(Component.newline()).append(Util.parseMiniMessage(Config.commandHelp, null));
        SubCommand.getCommands().forEach((sub, command) -> {
            if (command != null) {
                if (sender.hasPermission(command.getPermission())) {
                    message[0] = message[0].append(Component.newline());
                    TagResolver templates = TagResolver.resolver(
                            Placeholder.parsed("command", command.getSub()),
                            Placeholder.parsed("permission", command.getPermission()),
                            Placeholder.parsed("description", command.getDescription()),
                            Placeholder.parsed("usage", command.getSyntax())
                    );
                    Component info = Util.parseMiniMessage(Config.commandHelpList, templates);
                    message[0] = message[0].append(info);
                }
            }
        });
        message[0] = message[0].append(Component.newline()).append(Util.parseMiniMessage(Config.commandHelpFooter, null));
        sender.sendMessage(message[0]);
    }

}
