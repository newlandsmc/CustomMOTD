package com.semivanilla.custommotd.manager;

import com.semivanilla.custommotd.CustomMOTD;
import com.semivanilla.custommotd.config.MOTDConfig;
import com.semivanilla.custommotd.manager.wrapper.MOTDWrapper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MOTDManager {

    public CustomMOTD instance;

    private final List<MOTDWrapper> motds = new ArrayList<>();
    private MOTDWrapper activeMOTD;

    public MOTDManager() {
        instance = CustomMOTD.getInstance();
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

    }

    public void unloadMotds() {
        getMotds().clear();
    }

    public void addMotd(MOTDWrapper wrapper) {
        getMotds().add(wrapper);
    }

    public void saveMotds() {
        getMotds().forEach(MOTDWrapper::save);
        unloadMotds();
    }

    public List<MOTDWrapper> getMotds() {
        return motds;
    }

    public MOTDWrapper getActiveMOTD() {
        if (activeMOTD == null) {
            getMotds().stream()
                    .filter(MOTDWrapper::isActive)
                    .findFirst()
                    .ifPresent(wrapper -> activeMOTD = wrapper);
        }
        return activeMOTD;
    }

}
