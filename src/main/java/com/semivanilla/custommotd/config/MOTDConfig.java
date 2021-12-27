package com.semivanilla.custommotd.config;

import com.google.common.base.Throwables;
import com.semivanilla.custommotd.CustomMOTD;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class MOTDConfig extends YamlConfiguration {

    private static final Map<String, MOTDConfig> configs = new HashMap<>();
    private final String motdTitle;
    private final File file;
    private final Object saveLock = new Object();

    public static Map<String, MOTDConfig> getConfigs() {
        synchronized (configs) {
            return configs;
        }
    }

    public static MOTDConfig getConfig(String title) {
        synchronized (configs) {
            return configs.computeIfAbsent(title, k -> new MOTDConfig(title));
        }
    }

    private MOTDConfig(String title) {
        super();
        this.motdTitle = title;
        this.file = new File(CustomMOTD.getInstance().getDataFolder(), "motd" + File.separator + motdTitle + ".yml");
        if (!file.exists()) {
            save();
        }
        reload();
    }

    public void reload() {
        synchronized (saveLock) {
            try {
                load(file);
            } catch (Exception ex) {
                Throwables.throwIfUnchecked(ex.getCause());
            }
        }
    }

    public void save() {
        synchronized (saveLock) {
            try {
                save(file);
            } catch (Exception ex) {
                Throwables.throwIfUnchecked(ex.getCause());
            }
        }
    }

    public void delete() {
        synchronized (saveLock) {
            if (!file.delete()) {
                CustomMOTD.getInstance().getLogger().severe("Could not delete file! (" + motdTitle + ".yml)");
            }
        }
    }

    public String getMotdTitle() {
        return motdTitle;
    }

    public String getLine1() {
        return getString("line1", "This is the first line.");
    }

    public void setLine1(String line1) {
        set("line1", line1);
    }

    public String getLine2() {
        return getString("line2", "This is the second line.");
    }

    public void setLine2(String line2) {
        set("line2", line2);
    }

    public boolean isRestricted() {
        return getBoolean("restricted", false);
    }

    public void setRestricted(boolean restricted) {
        set("restricted", restricted);
    }

    public boolean isActive() {
        return getBoolean("active", false);
    }

    public void setActive(boolean active) {
        set("active", active);
    }

    public Integer getCounter() {
        return getInt("counter",0);
    }

    public void setCounter(int counter) {
        set("counter", counter);
    }
}
