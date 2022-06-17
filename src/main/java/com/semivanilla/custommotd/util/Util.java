package com.semivanilla.custommotd.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.command.CommandSender;

public class Util {

    public static Component parseMiniMessage(String message, TagResolver templates) {
        MiniMessage miniMessage = MiniMessage.miniMessage();
        if (templates == null) {
            return miniMessage.deserialize(message);
        } else {
            return miniMessage.deserialize(message, templates);
        }
    }

    public static void sendMiniMessage(CommandSender sender, String message, TagResolver templates) {
        Component component = parseMiniMessage(message, templates);
        sender.sendMessage(component);
    }

}
