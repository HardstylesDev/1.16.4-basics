package com.Hardstyles.mod;

import com.Hardstyles.mod.event.EventManager;
import com.Hardstyles.mod.input.KeyInputHandler;
import com.Hardstyles.mod.module.ModuleManager;
import com.Hardstyles.mod.module.mods.CoordsHud;
import com.Hardstyles.mod.module.mods.FullBright;
import com.Hardstyles.mod.module.mods.ToggleSprint;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.EventBus;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventListener;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

import java.lang.reflect.Field;
import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@net.minecraftforge.fml.common.Mod("examplemod")
public class ForgeMod {
    public static final String MODID = "xd";
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();
    public static EventManager eventManager;
    public static ModuleManager moduleManager;

    public ForgeMod() {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new ToggleSprint());
        MinecraftForge.EVENT_BUS.register(new KeyInputHandler());
        MinecraftForge.EVENT_BUS.register(new FullBright());
        MinecraftForge.EVENT_BUS.register(new CoordsHud());
    }

    private void setup(final FMLCommonSetupEvent event) {
        // some preinit code
        moduleManager = new ModuleManager();
        eventManager = new EventManager();

        registerEvents();
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        registerKeyBindings();
        LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().gameSettings);
    }

    public static final KeyBinding sprint =  new KeyBinding("key.test", KeyConflictContext.UNIVERSAL, InputMappings.Type.KEYSYM, GLFW.GLFW_KEY_J, "key.categories.ToggleSprint");
    public static final KeyBinding gui =  new KeyBinding("key.test", KeyConflictContext.UNIVERSAL, InputMappings.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT_SHIFT, "key.categories.GUI");

    private void registerKeyBindings() {
        ClientRegistry.registerKeyBinding(sprint);
        ClientRegistry.registerKeyBinding(gui);

    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
        // some example code to dispatch IMC to another mod
        InterModComms.sendTo("examplemod", "helloworld", () -> {
            LOGGER.info("Hello world from the MDK");
            return "Hello world";
        });
    }

    private void processIMC(final InterModProcessEvent event) {
        // some example code to receive and process InterModComms from other mods
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m -> m.getMessageSupplier().get()).
                collect(Collectors.toList()));
    }
    // You can use SubscribeEvent and let the Event Bus discover methods to call


    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @net.minecraftforge.fml.common.Mod.EventBusSubscriber(bus = net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
            // register a new block here
            LOGGER.info("HELLO from Register Block");
            Minecraft.getInstance().gameSettings.autoJump = false;
        }
    }

    public static void send(String msg) {
        Minecraft.getInstance().player.sendMessage(new StringTextComponent(msg), Minecraft.getInstance().player.getUniqueID());
    }

    public void registerEvents() {
        try {
            Field field = EventBus.class.getDeclaredField("busID");
            field.setAccessible(true);
            this.registerEvent(TickEvent.RenderTickEvent.class, eventManager, EventPriority.HIGH, field);


        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    private void registerEvent(Class<? extends Event> classEvent, IEventListener listener, EventPriority priority, Field field) {
        try {
            classEvent.newInstance().getListenerList().register(field.getInt((Object) FMLJavaModLoadingContext.get().getModEventBus()), priority, listener);
            classEvent.newInstance().getListenerList().register(field.getInt((Object) MinecraftForge.EVENT_BUS), priority, listener);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
