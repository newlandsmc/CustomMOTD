package com.semivanilla.custommotd.listener;

import com.semivanilla.custommotd.CustomMOTD;
import com.semivanilla.custommotd.manager.wrapper.MOTDWrapper;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class PingListener implements Listener {

    @EventHandler
    public void onPing(ServerListPingEvent event) {
        MOTDWrapper wrapper = CustomMOTD.getMotdManager().getActiveMOTD();
        if (wrapper != null) {
            MiniMessage miniMessage = MiniMessage.miniMessage();
            Component motd = miniMessage.parse(wrapper.getLine1())
                    .append(Component.newline())
                    .append(miniMessage.parse(wrapper.getLine2()));
            event.motd(motd);
        }
    }

}
