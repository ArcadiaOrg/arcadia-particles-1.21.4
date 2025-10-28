package com.codenamexpyz.objects.SoundObjects;

import static com.codenamexpyz.ArcadiaParticlesClient.mc;

import org.joml.Vector3d;

import com.codenamexpyz.ArcadiaParticles;

import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;

public class SoundMaker {
    public static void heatbeat(float Volume, double count, double activationTime) {
        if (count % activationTime + 1 == activationTime - 50 || count % activationTime + 1 == activationTime) { //Double beat, 50 frames apart for beating 
            mc.world.playSound(mc.player, mc.player.getBlockPos(), SoundEvents.BLOCK_NOTE_BLOCK_BASEDRUM.value(), SoundCategory.MASTER, Volume/4, 0.5f);
        }
    }

    public static void divJump(float Volume, Vector3d coords) {
        mc.world.playSound(mc.player, new BlockPos(new Vec3i((int)coords.x, (int)coords.y, (int)coords.z)), ArcadiaParticles.divEvent, SoundCategory.PLAYERS, Volume/4, 1.0f);
    }
}
