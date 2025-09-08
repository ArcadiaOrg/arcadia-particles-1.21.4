package com.codenamexpyz.utils;

import org.lwjgl.glfw.GLFW;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

public class Keybinds {
    public static KeyBinding sendPacketTrue;
    public static KeyBinding sendPacketFalse;


    public static void registerKeyInputs() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if(sendPacketTrue.wasPressed()) {
                //packetManager.hasMod(true);
            }
            if(sendPacketFalse.wasPressed()) {
                //packetManager.hasMod(false);
            }
        });
    }

    public static void register() {
        sendPacketTrue = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.codenamexpyz.sendTestTrue", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_Z, "category.codenamexpyz.ArcadiaParticles"));
        sendPacketFalse = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.codenamexpyz.sendTestFalse", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_X, "category.codenamexpyz.ArcadiaParticles"));
        registerKeyInputs();
    }
}