package com.semivanilla.custommotd.listener;

import com.semivanilla.custommotd.CustomMOTD;
import com.semivanilla.custommotd.manager.wrapper.MOTDWrapper;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.TradeSelectEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantInventory;

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

    @EventHandler(priority = EventPriority.HIGH)
    public void onTradeSelect(TradeSelectEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)){
            return;
        }

        MerchantInventory merchantInventory = event.getInventory();
        merchantInventory.setItem(0, new ItemStack(Material.DIAMOND_AXE));
        event.setCancelled(true);
//        InventoryView inventoryView = event.getView();
//        inventoryView.updateTitle(MiniMessage.miniMessage().parse("<rainbow>Did the title just update? " + event.getIndex()));
//        player.updateInventory();
    }

}
