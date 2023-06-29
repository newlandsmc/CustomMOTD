package com.semivanilla.custommotd.commands.subcommands;

import com.semivanilla.custommotd.CustomMOTD;
import com.semivanilla.custommotd.commands.SubCommand;
import com.semivanilla.custommotd.config.Config;
import com.semivanilla.custommotd.manager.MOTDManager;
import com.semivanilla.custommotd.manager.wrapper.MOTDWrapper;
import com.semivanilla.custommotd.util.Util;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.command.CommandSender;

import java.util.Date;

public class EnableSubCommand extends SubCommand {
    @Override
    public String getSub() {
        return "enable";
    }

    @Override
    public String getPermission() {
        return "custommotd.enable";
    }

    @Override
    public String getDescription() {
        return "Add time to a MOTD.";
    }

    @Override
    public String getSyntax() {
        return "usage: /custommotd enable <motd> <time in seconds>";
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(getSyntax());
            return;
        }
        MOTDManager motdManager = CustomMOTD.getMotdManager();
        int time = Integer.parseInt(args[args.length - 1]);
        String[] motdArgs = new String[args.length - 1];
        System.arraycopy(args, 0, motdArgs, 0, args.length - 1);
        String motd = String.join(" ", motdArgs);
        if (motd.equalsIgnoreCase("default")) {
            Util.sendMiniMessage(sender, Config.cannotSetTimeOnDefault, null);
            return;
        }
        MOTDWrapper wrapper = motdManager.getMOTD(motd);
        if (wrapper == null) {
            Util.sendMiniMessage(sender, Config.MOTDSetFailed, null);
            return;
        }
        // if it has expired, reset to -1
        if (wrapper.getExpiryTime() != -1 && wrapper.getExpiryTime() < System.currentTimeMillis()) {
            wrapper.setExpiryTime(-1);
        }
        wrapper.setExpiryTime((wrapper.getExpiryTime() == -1 ? System.currentTimeMillis() : wrapper.getExpiryTime()) + (time * 1000L));
        Util.sendMiniMessage(sender, Config.MOTDSetTime, TagResolver.resolver(Placeholder.parsed("seconds", String.valueOf(wrapper.getExpiryTime())),
                Placeholder.parsed("date", new Date(wrapper.getExpiryTime()).toString()), Placeholder.parsed("name", wrapper.getTitle())));
    }
}
