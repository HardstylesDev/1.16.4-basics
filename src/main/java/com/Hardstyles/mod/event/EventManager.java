package com.Hardstyles.mod.event;

import com.Hardstyles.mod.ForgeMod;
import com.Hardstyles.mod.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.IEventListener;


public class EventManager implements IEventListener {
    Minecraft mc = Minecraft.getInstance();
    public EventManager() {

    }

    public void invoke(Event event) {

        if (event instanceof TickEvent.RenderTickEvent) {
            this.onRender((TickEvent.RenderTickEvent) event);
        }
    }

    private void onRender(TickEvent.RenderTickEvent event) {

        if (mc.world != null) {
            for (Module mod : ForgeMod.moduleManager.getEnabledModules()) {
                mod.onRenderTick(event);
            }
        }
    }
}
