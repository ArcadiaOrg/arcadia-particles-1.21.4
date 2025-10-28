package com.codenamexpyz.utils;

import static com.codenamexpyz.ArcadiaParticlesClient.config;
import static com.codenamexpyz.ArcadiaParticlesClient.mc;

import java.awt.Color;
import java.util.List;
import java.util.Random;

import org.joml.Vector3d;
import org.lwjgl.glfw.GLFW;

import com.codenamexpyz.objects.StaticParticles;
import com.codenamexpyz.objects.DaelinShapes.Pentagon;
import com.codenamexpyz.objects.DaelinShapes.Square;
import com.codenamexpyz.objects.DaelinShapes.Triangle;
import com.codenamexpyz.objects.SoundObjects.SoundMaker;
import com.codenamexpyz.utils.Managers.SpellManager;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.particle.ParticleTypes;

public class Keybinds {
    public static KeyBinding sendPacketTrue;
    public static KeyBinding sendPacketFalse;

    public static void registerKeyInputs() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if(sendPacketTrue.wasPressed()) {
                if (!config.playerPacketSettings.toggleDoubleRender) return;
                Random random = new Random();
                switch (random.nextInt(3)) {
                    case 0:
                        SpellManager.addShape(new Square(mc.player.getPos().toVector3f(), Color.RED, 4f, 3f));
                        break;
                    case 1:
                        SpellManager.addShape(new Triangle(mc.player.getPos().toVector3f(), Color.RED, 4f, 3f));
                        break;
                    default:
                        SpellManager.addShape(new Pentagon(mc.player.getPos().toVector3f(), Color.RED, 4f, 3f));
                        break;
                }
                
                StaticParticles.ParticleJet(mc.player.getPos(), List.of(ParticleTypes.CLOUD), null, 50, 20);
                SoundMaker.divJump(((float)config.accessibilitySettings.doubleJumpSound/100f), new Vector3d(mc.player.getPos().toVector3f()));
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