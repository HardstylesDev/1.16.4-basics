package com.Hardstyles.mod.module;

import com.Hardstyles.mod.module.mods.CoordsHud;
import com.Hardstyles.mod.module.mods.FullBright;
import com.Hardstyles.mod.module.mods.ToggleSprint;

import java.util.*;

public class ModuleManager {
    public static final Class<? extends Module>[] MODULES = new Class[]{CoordsHud.class, FullBright.class, ToggleSprint.class};
    private final Map<Class<? extends Module>, Module> modules = new HashMap<Class<? extends Module>, Module>();
    private final List<Class<? extends Module>> enabledModules = new ArrayList<Class<? extends Module>>();

    public ModuleManager() {

        for (Class<? extends Module> moduleClass : MODULES) {
            try {
                this.modules.put(moduleClass, moduleClass.getConstructor().newInstance());
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    public void enableModule(Module module) {
        if (!this.enabledModules.remove(module.getClass())) {
            this.enabledModules.add(module.getClass());

            module.setEnabled(!module.isEnabled());
        }
    }


    public <T extends Module> T getModule(Class<T> clazz) {
        return (T) this.modules.get(clazz);
    }

    public Module getModule(String name) {
        for (Module module : this.modules.values()) {
            if (!module.getName().equals(name)) continue;
            return module;
        }
        return null;
    }

    public Set<Module> getEnabledModules() {
        HashSet<Module> enabledModules = new HashSet<Module>();
        for (Class<? extends Module> moduleClazz : this.enabledModules) {
            enabledModules.add(this.getModule(moduleClazz));
        }
        return enabledModules;
    }

    public Set<Module> getModules() {
        HashSet<Module> allModules = new HashSet<Module>();
        for (Module module : this.modules.values()) {
            allModules.add(module);
        }
        return allModules;
    }
}
