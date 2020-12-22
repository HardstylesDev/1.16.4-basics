package com.Hardstyles.mod.module;

import net.minecraftforge.event.TickEvent;

public class Module {

    private final String name;
    private boolean enabled;


    public Module(String name) {
        this.name = name;

    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        if (this.enabled) {
            this.onEnable();
        } else {
            this.onDisable();
        }
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public String getName() {
        return this.name;
    }

    public void onEnable() {
    }
    public void onRenderTick(TickEvent.RenderTickEvent e) {
    }


    public void onDisable() {
    }
}
