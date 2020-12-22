package com.Hardstyles.mod.module.mods;

import com.Hardstyles.mod.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.potion.Effects;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class FullBright extends Module {
    public FullBright(){
        super("FullBright");
    }
    Minecraft mc = Minecraft.getInstance();


    @Override
    public void onDisable() {
        mc.player.removeActivePotionEffect(Effects.NIGHT_VISION);
        mc.gameSettings.gamma = 1f;
    }

    @Override
    public void onRenderTick(TickEvent.RenderTickEvent world) {
        if(mc.player != null)
           // mc.player.addPotionEffect(new EffectInstance(Effects.NIGHT_VISION, 5,0));
            mc.gameSettings.gamma = 100f;
    }
}
