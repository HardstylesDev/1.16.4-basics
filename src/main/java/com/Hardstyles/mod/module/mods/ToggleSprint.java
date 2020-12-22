package com.Hardstyles.mod.module.mods;

import com.Hardstyles.mod.gui.ModGui;
import com.Hardstyles.mod.module.Module;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.awt.*;

public class ToggleSprint extends Module {

    public ToggleSprint(){
        super("ToggleSprint");
    }
    Minecraft mc = Minecraft.getInstance();


    public void onDisable() {
        InputMappings.Input sprintKeyBind = mc.gameSettings.keyBindSprint.getKey();
        KeyBinding.setKeyBindState(sprintKeyBind, false);
        KeyBinding.onTick(sprintKeyBind);
    }

    @Override
    public void onRenderTick(TickEvent.RenderTickEvent world) {
        if ((mc.world != null && mc.player != null) && (mc.currentScreen == null || mc.currentScreen instanceof ModGui)) {
            InputMappings.Input sprintKeyBind = mc.gameSettings.keyBindSprint.getKey();
            KeyBinding.setKeyBindState(sprintKeyBind, true);
            KeyBinding.onTick(sprintKeyBind);
            if(mc.gameSettings.keyBindSneak.isKeyDown()){
                drawChromaString("[Sneaking (Key held)]", 15, mc.getMainWindow().getScaledHeight() - 15, 0.5);

                return;
            }
            if (InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), mc.gameSettings.keyBindSprint.getKey().getKeyCode()))
                drawChromaString("[Sprint (Key held)]", 15, mc.getMainWindow().getScaledHeight() - 15, 0.5);
            else
                drawChromaString("[Sprint (Toggled)]", 15, mc.getMainWindow().getScaledHeight() - 15, 0.5);

        }
    }


    protected Color getChromaColor(double x, double y, double offsetScale) {
        float v = 2000.0F;
        return new Color(Color.HSBtoRGB((float) (((System.currentTimeMillis()) - x * 10.0D * offsetScale - y * 10.0D * offsetScale) % v) / v, 0.8F, 0.8F));
    }

    protected void drawChromaString(String text, int x, int y, double offsetScale) {
        FontRenderer renderer = mc.fontRenderer;
        for (char c : text.toCharArray()) {
            int i = getChromaColor(x, y, offsetScale).getRGB();

            String tmp = String.valueOf(c);
            renderer.drawStringWithShadow(new MatrixStack(), tmp, x, y, i);
            x += renderer.getStringWidth(tmp);
        }
    }
}
