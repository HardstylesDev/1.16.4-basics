package com.Hardstyles.mod.gui;

import com.Hardstyles.mod.ForgeMod;
import com.Hardstyles.mod.module.Module;
import com.Hardstyles.mod.module.mods.CoordsHud;
import com.Hardstyles.mod.module.mods.FullBright;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;


public class ModGui extends Screen {

    private static final int WIDTH = 179;
    private static final int HEIGHT = 151;

    // private ResourceLocation GUI = new ResourceLocation(MyTutorial.MODID, "textures/gui/spawner_gui.png");


    public ModGui() {
        super(new TranslationTextComponent("screen.mytutorial.spawn"));
    }

    @Override
    protected void init() {
        int relX = (this.width - WIDTH) / 2;
        int relY = (this.height - HEIGHT) / 2;

        addButton(new Button(relX + 10, relY + 10, 160, 20, new StringTextComponent("Coords Hud " + status(ForgeMod.moduleManager.getModule("Coords HUD"))), button -> click(1)));
        addButton(new Button(relX + 10, relY + 37, 160, 20, new StringTextComponent("ToggleSprint " + status(ForgeMod.moduleManager.getModule("ToggleSprint"))), button -> click(2)));
        addButton(new Button(relX + 10, relY + 64, 160, 20, new StringTextComponent("FullBright " + status(ForgeMod.moduleManager.getModule("FullBright"))), button -> click(3)));
        addButton(new Button(relX + 10, relY + 91, 160, 20, new StringTextComponent("Sheep"), button -> click(4)));
        addButton(new Button(relX + 10, relY + 118, 160, 20, new StringTextComponent("Chicken"), button -> click(5)));
        addButton(new Button(relX + 10, relY + 172, 160, 20, new StringTextComponent("Chicken"), button -> click(-1)));
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    private void click(int id) {

        System.out.println(id);
        switch (id) {
            case -1:
                minecraft.displayGuiScreen(null);
                ForgeMod.send("Close GUI");
                break;

            case 1:
                Module module = ForgeMod.moduleManager.getModule("Coords HUD");
                ForgeMod.moduleManager.enableModule(module);
                ForgeMod.send("Coordsmod set to " + module.isEnabled());

                open();
                break;
            case 2:
                Module module2 = ForgeMod.moduleManager.getModule("ToggleSprint");
                ForgeMod.moduleManager.enableModule(module2);

                open();
                break;
            case 3:
                Module module3 = ForgeMod.moduleManager.getModule("FullBright");
                ForgeMod.moduleManager.enableModule(module3);

                open();
                break;
            default:
                minecraft.player.sendMessage(new StringTextComponent("defaulted"), minecraft.player.getUniqueID());

        }

    }
     //   Networking.sendToServer(new PacketSpawn(new ResourceLocation(id), minecraft.player.dimension, minecraft.player.getPosition()));

    @Override
    public void render(MatrixStack matrixStack,int mouseX, int mouseY, float partialTicks) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        //this.minecraft.getTextureManager().bindTexture(GUI);
        int relX = (this.width - WIDTH) / 2;
        int relY = (this.height - HEIGHT) / 2;

        super.render(matrixStack, mouseX, mouseY, partialTicks);

    }


    public static void open() {
        Minecraft.getInstance().displayGuiScreen(new ModGui());
    }

    public String status(Module module){
        if(module.isEnabled())
            return TextFormatting.DARK_GREEN + "Enabled";
        else
            return TextFormatting.RED + "Disabled";
    }
}