package com.codenamexpyz.objects.SoundObjects;

import static com.codenamexpyz.ArcadiaParticlesClient.mc;

import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;

public class SoundMaker {
    public static void heatbeat(float Volume, double count, double activationTime) {
        if (count % activationTime + 1 == activationTime - 50 || count % activationTime + 1 == activationTime) { //Double beat, 50 frames apart for beating 
            mc.world.playSound(mc.player, mc.player.getBlockPos(), SoundEvents.BLOCK_NOTE_BLOCK_BASEDRUM.value(), SoundCategory.MASTER, Volume/4, 0.5f);
        }
    }
}
