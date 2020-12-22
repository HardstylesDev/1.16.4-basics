package com.Hardstyles.mod.input;

import com.Hardstyles.mod.ForgeMod;
import com.Hardstyles.mod.gui.ModGui;
import com.Hardstyles.mod.module.Module;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class KeyInputHandler {
    boolean press = false;

    @SubscribeEvent
    public void onKeyInput(TickEvent.ClientTickEvent event) {
        if (ForgeMod.sprint.isKeyDown() && ForgeMod.sprint.isPressed()) {
            Module module = ForgeMod.moduleManager.getModule("ToggleSprint");
            ForgeMod.moduleManager.enableModule(module);
        }
        if (ForgeMod.gui.isKeyDown() && ForgeMod.gui.isPressed()) {
            ModGui.open();
        }
    }
}
