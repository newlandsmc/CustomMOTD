package com.semivanilla.custommotd.manager.wrapper;

import com.semivanilla.custommotd.CustomMOTD;
import com.semivanilla.custommotd.config.Config;
import com.semivanilla.custommotd.config.MOTDConfig;

public class MOTDWrapper {

    private final String title;
    private String line1;
    private String line2;
    private final boolean restricted;
    private boolean active;
    private Integer counter;

    private final MOTDConfig config;
    private boolean dirty = false;

    public MOTDWrapper(MOTDConfig motdConfig) {
        this.title = motdConfig.getMotdTitle();
        this.line1 = motdConfig.getLine1();
        this.line2 = motdConfig.getLine2();
        this.restricted = motdConfig.isRestricted();
        this.active = motdConfig.isActive();
        this.counter = motdConfig.getCounter();
        this.config = motdConfig;
    }

    public MOTDWrapper(String Title, String Line1, String Line2, boolean restricted, boolean Active) {
        this.title = Title;
        this.line1 = Line1;
        this.line2 = Line2;
        this.restricted = restricted;
        this.active = Active;
        this.config = MOTDConfig.getConfig(title);
        this.dirty = true;
        this.counter = 0;
        save();
    }

    public String getTitle() {
        return title;
    }

    public String getLine1() {
        return line1;
    }

    public String getLine2() {
        return line2;
    }

    public void setLine1(String input) {
        line1 = input;
        dirty = true;
    }

    public void setLine2(String input) {
        line2 = input;
        dirty = true;
    }

    public boolean isRestricted() {
        return restricted;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean status) {
        active = status;
        dirty = true;
    }

    public Integer getCounter() {
        return counter;
    }

    public void resetCounter() {
        counter = 0;
        dirty = true;
    }

    public void changeCounter(Integer count) {
        counter += count;
        if (counter < 0) counter = 0;
        dirty = true;
    }

    public void save() {
        if (dirty) {
            config.setLine1(line1);
            config.setLine2(line2);
            config.setActive(active);
            config.setRestricted(restricted);
            config.setCounter(counter);
            config.save();
            dirty = false;
        }
    }

    public String toString() {
        return "Title: " + this.getTitle() + "\n" + "Line1: " + this.getLine1() + "\n" + "Line2: " + this.getLine2() + "\n" + "Active: " + this.isActive() + "\n" + "Counter: " + this.getCounter();
    }

}
