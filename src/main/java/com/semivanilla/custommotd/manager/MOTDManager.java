package com.semivanilla.custommotd.manager;

import com.semivanilla.custommotd.CustomMOTD;
import com.semivanilla.custommotd.config.Config;
import com.semivanilla.custommotd.config.MOTDConfig;
import com.semivanilla.custommotd.manager.wrapper.MOTDWrapper;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class MOTDManager {


    private List<MOTDWrapper> motds = new ArrayList<>();
    private MOTDWrapper activeMOTD;
    private MOTDWrapper counterMOTD;

    public MOTDManager() {

    }

    public void loadMotds() {
        unloadMotds();

        File[] files = new File(CustomMOTD.getInstance().getDataFolder(), "motd")
                            .listFiles((dir, name) -> name.endsWith(".yml"));

        if (files == null) {
            files = new File[0];
        }

        for (File file : files) {
            int dotIndex = file.getName().lastIndexOf('.');
            String fileName = file.getName().substring(0, dotIndex);
            MOTDConfig.getConfig(fileName);
        }

        TreeMap<String, MOTDConfig> treeMap = new TreeMap<>(MOTDConfig.getConfigs());
        for (Map.Entry<String, MOTDConfig> entry : treeMap.entrySet()) {
            MOTDConfig motdConfig = entry.getValue();
            addMotd(new MOTDWrapper(motdConfig));
        }
        updateCounterMOTD();
        getActiveMOTD();
    }

    public void unloadMotds() {
        activeMOTD = null;
        counterMOTD = null;
        getMotds().clear();
        MOTDConfig.clearConfigs();
    }

    public void resetMotds() {
        activeMOTD = null;
        counterMOTD = null;
        getMotds().forEach(motdWrapper -> {
            if (motdWrapper.isActive()) motdWrapper.setActive(false);
            if (motdWrapper.getCounter() > 0) motdWrapper.resetCounter();
            motdWrapper.setExpiryTime(-1);
            motdWrapper.save();
        });
    }

    public void addMotd(MOTDWrapper wrapper) {
        getMotds().add(wrapper);
    }

    public void saveMotds() {
        getMotds().forEach(MOTDWrapper::save);
    }

    public List<MOTDWrapper> getMotds() {
        return motds;
    }

    public MOTDWrapper getActiveMOTD() {
        if (activeMOTD == null) {
            getMotds().stream()
                    .filter(MOTDWrapper::isActive) // Filter out all inactive MOTDs
                    .findFirst() // Get the first one
                    .ifPresent(wrapper -> activeMOTD = wrapper); // Set it as the active MOTD
        }
        if (Config.enableCounter && counterMOTD != null && activeMOTD == null) return counterMOTD;
        if (activeMOTD == null) {
            return getMotds().stream()
                    .filter(motdWrapper -> {
                        return motdWrapper.getExpiryTime() > 0 && !motdWrapper.isExpired(); // Filter out all expired MOTDs
                    })
                    .findFirst().orElse(null);
        }
        return activeMOTD;
    }

    public MOTDWrapper getMOTD(String title) {
        return getMotds().stream()
                .filter(motdWrapper -> motdWrapper.getTitle().equalsIgnoreCase(title))
                .distinct().findFirst().orElse(null);
    }

    public void activateMOTD(MOTDWrapper motd) {
        if (motd == null && activeMOTD != null) {
            activeMOTD.setActive(false);
            activeMOTD = null;
            saveMotds();
        } else if (motd != null) {
            if(activeMOTD != null)
                activeMOTD.setActive(false);
            motd.setActive(true);
            activeMOTD = motd;
            saveMotds();
        }
    }

    public void updateCounterMOTD() {
        List<MOTDWrapper> countermotds = getMotds().stream()
                .filter(MOTDWrapper::isRestricted)
                .filter(motdWrapper -> motdWrapper.getCounter() > 0)
                .toList();
        if (countermotds.isEmpty()) counterMOTD = null;
        if (countermotds.size() > 1) counterMOTD = getMOTD(Config.counterMOTD);
        if (countermotds.size() == 1) counterMOTD = countermotds.get(0);
    }

    public boolean isDefaultMOTD(MOTDWrapper wrapper) {
        if (wrapper == null) return true;
        if (wrapper.getTitle().equalsIgnoreCase(Config.defaultMOTD)) return true;
        return false;
    }

    public void activateDefaultMOTD() {
        MOTDWrapper wrapper = getMOTD(Config.defaultMOTD);
        activateMOTD(wrapper);
    }

}
