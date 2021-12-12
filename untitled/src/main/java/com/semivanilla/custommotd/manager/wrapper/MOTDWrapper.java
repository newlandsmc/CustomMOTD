package com.semivanilla.custommotd.manager.wrapper;

import com.semivanilla.custommotd.config.MOTDConfig;

public class MOTDWrapper {

    private final String title;
    private String line1;
    private String line2;
    private boolean active;

    private final MOTDConfig config;
    private boolean dirty = false;

    public MOTDWrapper(MOTDConfig motdConfig) {
        this.title = motdConfig.getMotdTitle();
        this.line1 = motdConfig.getLine1();
        this.line2 = motdConfig.getLine2();
        this.active = motdConfig.isActive();
        this.config = motdConfig;
    }

    public MOTDWrapper(String Title, String Line1, String Line2, boolean Active) {
        this.title = Title;
        this.line1 = Line1;
        this.line2 = Line2;
        this.active = Active;
        this.config = MOTDConfig.getConfig(title);
        config.setLine1(line1);
        config.setLine2(line2);
        config.setActive(active);
        config.save();
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

    public void setLine1(String line1) {
        this.line1 = line1;
        this.dirty = true;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
        this.dirty = true;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
        this.dirty = true;
    }

    public void save() {
        if (this.dirty) {
            this.config.setLine1(this.line1);
            this.config.setLine2(this.line2);
            this.config.setActive(this.active);
            this.config.save();
            this.dirty = false;
        }
    }

    public String toString() {
        return "Title: " + this.getTitle() + "\n" + "Line1: " + this.getLine1() + "\n" + "Line2: " + this.getLine2() + "\n" + "Active: " + this.isActive();
    }

}
