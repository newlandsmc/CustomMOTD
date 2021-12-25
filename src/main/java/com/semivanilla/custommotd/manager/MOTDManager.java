package com.semivanilla.custommotd.manager;

import com.semivanilla.custommotd.CustomMOTD;
import com.semivanilla.custommotd.config.Config;
import com.semivanilla.custommotd.config.MOTDConfig;
import com.semivanilla.custommotd.manager.wrapper.MOTDWrapper;

import java.io.File;
import java.util.*;

public class MOTDManager {


    private final List<MOTDWrapper> motds = new ArrayList<>();
    private MOTDWrapper activeMOTD;
    private MOTDWrapper counterMOTD;
    private boolean enableCounter = false;

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
        enableCounter = getMOTD(Config.counterMOTD) != null;
        if(!enableCounter) {
            CustomMOTD.getInstance().getLogger().severe("motd counter disabled because motd for " + Config.counterMOTD + " was not found.");
        }
    }

    public void unloadMotds() {
        getMotds().clear();
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
        if (enableCounter && Config.counter != 0) return counterMOTD;
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

    public void changeCounter(int count) {
        int counter = Config.counter;
        counter += count;
        if (counter < 0) counter = 0;
//        if (counter > 0) activateMOTD(getMOTD(Config.counterMOTD));
        if (Config.counter != counter) Config.setCounter(counter);
        updateCounterMOTD();
    }

    public void updateCounterMOTD() {
        counterMOTD =  getMotds().stream()
                .filter(MOTDWrapper::isRestricted)
                .max(Comparator.comparing(MOTDWrapper::getWeight)).orElse(activeMOTD);
    }

}
