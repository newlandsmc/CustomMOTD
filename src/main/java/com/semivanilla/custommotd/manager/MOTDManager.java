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
        getActiveMOTD();
        updateCounterMOTD();
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
        if (Config.enableCounter && counterMOTD != null) return counterMOTD;
        if (activeMOTD == null) {
            getMotds().stream()
                    .filter(MOTDWrapper::isActive)
                    .findFirst()
                    .ifPresent(wrapper -> activeMOTD = wrapper);
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

//    public void changeCounter(int count) {
//        int counter = Config.counter;
//        counter += count;
//        if (counter < 0) counter = 0;
//        if (counter > 0) activateMOTD(getMOTD(Config.counterMOTD));
//        if (Config.counter != counter) Config.setCounter(counter);
//        updateCounterMOTD();
//    }

    public void updateCounterMOTD() {
        List<MOTDWrapper> countermotds = getMotds().stream()
                .filter(MOTDWrapper::isRestricted)
                .filter(motdWrapper -> motdWrapper.getCounter() > 0)
                .toList();
        if (countermotds.isEmpty()) counterMOTD = null;
        if (countermotds.size() > 1) counterMOTD = getMOTD(Config.counterMOTD);
        if (countermotds.size() == 1) counterMOTD = countermotds.get(0);
    }

}
