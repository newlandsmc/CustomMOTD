package com.semivanilla.custommotd.config;

import com.google.common.base.Throwables;
import com.semivanilla.custommotd.CustomMOTD;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.logging.Level;

public class Config {

    private static final String HEADER = "";

    private static File CONFIG_FILE;
    public static File CONFIG_PATH;
    public static YamlConfiguration config;

    static int version;
    static boolean verbose;

    public static void init() {
        CONFIG_PATH = CustomMOTD.getInstance().getDataFolder();
        CONFIG_FILE = new File(CONFIG_PATH, "config.yml");
        config = new YamlConfiguration();
        try {
            config.load(CONFIG_FILE);
        } catch (IOException ignore) {
        } catch (InvalidConfigurationException ex) {
            Bukkit.getLogger().log(Level.SEVERE, "Could not load config.yml, please correct your syntax errors", ex);
            Throwables.throwIfUnchecked(ex);
        }
        config.options().header(HEADER);
        config.options().copyDefaults(true);
        verbose = getBoolean("verbose", false);

        version = getInt("config-version", 1);

        readConfig(Config.class, null);
    }

    protected static void log(String s) {
        if (verbose) {
            log(Level.INFO, s);
        }
    }

    static void readConfig(Class<?> clazz, Object instance) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (Modifier.isPrivate(method.getModifiers())) {
                if (method.getParameterTypes().length == 0 && method.getReturnType() == Void.TYPE) {
                    try {
                        method.setAccessible(true);
                        method.invoke(instance);
                    } catch (InvocationTargetException ex) {
                        Throwables.throwIfUnchecked(ex);
                    } catch (Exception ex) {
                        Bukkit.getLogger().log(Level.SEVERE, "Error invoking " + method, ex);
                    }
                }
            }
        }
        saveConfig();
    }

    static void saveConfig() {
        try {
            config.save(CONFIG_FILE);
        } catch (IOException ex) {
            Bukkit.getLogger().log(Level.SEVERE, "Could not save " + CONFIG_FILE, ex);
        }
    }

    private static void set(String path, Object val) {
        config.addDefault(path, val);
        config.set(path, val);
    }

    private static boolean getBoolean(String path, boolean def) {
        config.addDefault(path, def);
        return config.getBoolean(path, config.getBoolean(path));
    }

    private static double getDouble(String path, double def) {
        config.addDefault(path, def);
        return config.getDouble(path, config.getDouble(path));
    }

    private static int getInt(String path, int def) {
        config.addDefault(path, def);
        return config.getInt(path, config.getInt(path));
    }

    private static <T> List getList(String path, T def) {
        config.addDefault(path, def);
        return config.getList(path, config.getList(path));
    }

    private static String getString(String path, String def) {
        config.addDefault(path, def);
        return config.getString(path, config.getString(path));
    }

    protected static void log(Level level, String s) {
        Bukkit.getLogger().log(level, s);
    }

    /** ONLY EDIT BELOW THIS LINE **/
    public static String MOTDSet = "Custom MOTD has been set to <motd>";
    public static String MOTDSetFailed = "There is no motd by this name.";
    public static String MOTDSetRestricted = "You can't set this motd.";
    public static String MOTDSetDefault = "You set the motd to default.";
    public static String MOTDSetDefaultFailed = "Current motd is already default.";
    public static String counterIncreased = "Counter increased for <motd>";
    public static String counterDecreased = "Counter decreased for <motd>";
    public static String currentMOTD = "Custom MOTD is <motd>";
    public static String MOTDReset = "All triggers that cause a custom motd to activate have been reset.";
    public static String NoPermission = "You need to have permission <permission> to run this command.";
    public static String CommandHelp = "Invalid usage please do /custommotd help for more info";
    public static String CommandHelpList = "<command>: <usage> - <description>";
    public static String CommandList = "Available motd: <list>";
    public static String CommandReload = "Reloaded configuration.";
    public static String CommandCheck = "The current active motd is <motd>.";
    private static void messages() {
        MOTDSet = getString("messages.motd.set-motd", MOTDSet);
        MOTDSetFailed = getString("messages.motd.not-a-motd", MOTDSetFailed);
        MOTDSetRestricted = getString("messages.motd.set-motd-restricted", MOTDSetRestricted);
        MOTDSetDefault = getString("messages.motd.set-motd-default", MOTDSetDefault);
        MOTDSetDefaultFailed = getString("messages.motd.set-motd-default-failed", MOTDSetDefaultFailed);
        counterIncreased = getString("messages.motd.counter-increased", counterIncreased);
        counterDecreased = getString("messages.motd.counter-decreased", counterDecreased);
        MOTDReset = getString("messages.motd.reset", MOTDReset);
        currentMOTD = getString("messages.motd.current-motd", currentMOTD);
        NoPermission = getString("messages.command.no-permission", NoPermission);
        CommandHelp = getString("messages.command.command-help", CommandHelp);
        CommandHelpList = getString("messages.command.command-help-list", CommandHelpList);
        CommandList = getString("messages.command.command-list", CommandList);
        CommandCheck = getString("messages.command.command-check", CommandCheck);
    }

    public static String counterMOTD = "multiplenonzeromcmmo";
    public static boolean enableCounter = true;
    private static void motdSettings() {
        enableCounter = getBoolean("counter.enabled", enableCounter);
        counterMOTD = getString("counter.counter-motd", counterMOTD);
    }

}
