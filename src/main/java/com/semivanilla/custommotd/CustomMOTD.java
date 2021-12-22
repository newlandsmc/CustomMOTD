package com.semivanilla.custommotd;

import com.semivanilla.custommotd.commands.MOTDCommand;
import com.semivanilla.custommotd.config.Config;
import com.semivanilla.custommotd.listener.PingListener;
import com.semivanilla.custommotd.manager.MOTDManager;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class CustomMOTD extends JavaPlugin {

    private static CustomMOTD instance;
    private final MOTDManager motdManager;

    public CustomMOTD() {
        motdManager = new MOTDManager();
    }

    @Override
    public void onEnable() {
        instance = this;
        Config.init();

        getMotdManager().loadMotds();

        registerListener(new PingListener());
        getCommand("custommotd").setExecutor(new MOTDCommand());
    }

    @Override
    public void onDisable() {
        getMotdManager().saveMotds();
        getMotdManager().unloadMotds();
        instance = null;
    }

    public void registerListener(Listener... listeners) {
        for (Listener listener : listeners) {
            getServer().getPluginManager().registerEvents(listener, this);
        }
    }

    public static CustomMOTD getInstance() {
        return instance;
    }

    public static MOTDManager getMotdManager() {
        return instance.motdManager;
    }
}
