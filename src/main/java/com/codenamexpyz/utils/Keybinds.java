package com.codenamexpyz.utils;

import java.util.List;

import static com.codenamexpyz.ArcadiaParticlesClient.mc;

import java.util.ArrayList;

import org.lwjgl.glfw.GLFW;

import com.codenamexpyz.customParticles.customParticle;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;


public class Keybinds {
    public static KeyBinding sendPacketTrue;
    public static KeyBinding sendPacketFalse;

    private static List<customParticle> pars = new ArrayList<>();

    public static void registerKeyInputs() {
        WorldRenderEvents.END.register(context -> {	
			if (mc.world != null && !mc.isPaused()) { 
                pars.removeIf(customParticle::tick);
            }
        });

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (sendPacketTrue.isPressed()) {
                
            }
        });
    }

    public static void register() {
        sendPacketTrue = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.codenamexpyz.sendTestTrue", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_Z, "category.codenamexpyz.ArcadiaParticles"));
        sendPacketFalse = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.codenamexpyz.sendTestFalse", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_X, "category.codenamexpyz.ArcadiaParticles"));
        registerKeyInputs();
    }
}