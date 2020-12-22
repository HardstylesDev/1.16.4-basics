package com.Hardstyles.mod.module.mods;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ArmorStatus {
    Minecraft mc = Minecraft.getInstance();
    public static boolean enabled = true;
// yea
    public void onDisable() {
        InputMappings.Input sprintKeyBind = mc.gameSettings.keyBindSprint.getKey();
        KeyBinding.setKeyBindState(sprintKeyBind, false);
        KeyBinding.onTick(sprintKeyBind);
    }

    @SubscribeEvent
    public void renderScreen(TickEvent.RenderTickEvent e) {

    }
}
