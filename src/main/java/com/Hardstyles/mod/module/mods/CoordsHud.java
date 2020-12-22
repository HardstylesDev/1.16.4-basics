package com.Hardstyles.mod.module.mods;

import com.Hardstyles.mod.ForgeMod;
import com.Hardstyles.mod.gui.ModGui;
import com.Hardstyles.mod.module.Module;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.awt.*;

public class CoordsHud  extends Module {
    public CoordsHud(){
        super("Coords HUD");
    }
    Minecraft mc = Minecraft.getInstance();
    @Override
    public void onDisable() {

    }


    @Override
    public void onRenderTick(TickEvent.RenderTickEvent world) {
        System.out.println(this.isEnabled());
        if ((mc.world != null && mc.player != null) && (mc.currentScreen == null || mc.currentScreen instanceof ModGui)) {

            drawChromaString("[X]:", 1, 3, 0.5);
            mc.fontRenderer.drawStringWithShadow(new MatrixStack(), "" + Math.round(mc.player.getPosX()), mc.fontRenderer.getStringWidth("[X]: ") + 1, 3, Color.WHITE.getRGB());
            drawChromaString("[Y]:", 1, 12, 0.5);
            mc.fontRenderer.drawStringWithShadow(new MatrixStack(), "" + Math.round(mc.player.getPosY()), mc.fontRenderer.getStringWidth("[Y]: ") + 1, 12, Color.WHITE.getRGB());


            drawChromaString("[Z]:", 1, 21, 0.5);
            mc.fontRenderer.drawStringWithShadow(new MatrixStack(), "" + Math.round(mc.player.getPosZ()), mc.fontRenderer.getStringWidth("[Z]: ") + 1, 21, Color.WHITE.getRGB());


            float rot = mc.player.getRotationYawHead();
            drawChromaString("[Direction]:", 1, 39, 0.5);
            mc.fontRenderer.drawStringWithShadow(new MatrixStack(), "" + getDir(rot), mc.fontRenderer.getStringWidth("[Direction]: ") + 1, 39, Color.WHITE.getRGB());

            //drawChromaString("[Y]: " + Math.round(mc.player.getPosY()), 0, 12, 0.5);
            //drawChromaString("[Z]: " + Math.round(mc.player.getPosZ()), 0, 21, 0.5);
            drawChromaString("[FPS]:", 1, 48, 0.5);
            mc.fontRenderer.drawStringWithShadow(new MatrixStack(), "" + mc.debug.split(" fps")[0], mc.fontRenderer.getStringWidth("[FPS]: ") + 1, 48, Color.WHITE.getRGB());

            drawChromaString("[C]:", 1, 57, 0.5);
            mc.fontRenderer.drawStringWithShadow(new MatrixStack(), "" + mc.worldRenderer.getDebugInfoRenders().split("/")[0].replace("C: ", ""), mc.fontRenderer.getStringWidth("[C]: ") + 1, 57, Color.WHITE.getRGB());


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

    private static String getDir(float rot) {
        String dir = "";
        while (rot < -45) rot += 360;
        while (rot > 315) rot -= 360;
        if (rot < -22.5) dir = "SE";
        else if (rot < 22.5) dir = "S";
        else if (rot < 67.5) dir = "SW";
        else if (rot < 112.5) dir = "W";
        else if (rot < 157.5) dir = "NW";
        else if (rot < 202.5) dir = "N";
        else if (rot < 247.5) dir = "NE";
        else if (rot < 292.5) dir = "E";
        else dir = "SE";
        return dir;
    }
}
