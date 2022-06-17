package com.semivanilla.custommotd.listener;

import com.semivanilla.custommotd.CustomMOTD;
import com.semivanilla.custommotd.manager.wrapper.MOTDWrapper;
import com.semivanilla.custommotd.util.Util;
import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class PingListener implements Listener {

    @EventHandler
    public void onPing(ServerListPingEvent event) {
        MOTDWrapper wrapper = CustomMOTD.getMotdManager().getActiveMOTD();
        if (wrapper != null) {
            Component motd = Util.parseMiniMessage(wrapper.getLine1(), null)
                    .append(Component.newline())
                    .append(Util.parseMiniMessage(wrapper.getLine2(), null));
            event.motd(motd);
        }
    }

}
