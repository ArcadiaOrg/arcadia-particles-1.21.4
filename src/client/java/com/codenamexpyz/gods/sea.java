package com.codenamexpyz.gods;

import java.util.Arrays;
import java.util.List;

import java.awt.Color;

import com.codenamexpyz.objects.CircleParticleObject;
import com.codenamexpyz.objects.ParticleAura;
import com.codenamexpyz.objects.ParticleTrail;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Vec3d;

import static com.codenamexpyz.ArcadiaParticlesClient.mc;

public class sea {
    private static final List<ParticleEffect> particleList = Arrays.asList(ParticleTypes.DRIPPING_DRIPSTONE_WATER);
    private static final List<ParticleEffect> haloParticleList = Arrays.asList(ParticleTypes.END_ROD);
    private static final List<Color> colorList = Arrays.asList(new Color(0/255, 255/255, 255/255), new Color(255/255, 255/255, 255/255)); 
    private static int i = 0; //Count

    public static void triggerParticles(List<PlayerEntity> viewerList, PlayerEntity godEntity) {
        Vec3d loc = godEntity.getPos();

        if (!mc.player.getName().equals(godEntity.getName())) {
            for (PlayerEntity player : viewerList) { //This needs to be made more efficient, I will do it some year.
                if (player.getName().getLiteralString().equals(godEntity.getName().getString())) {
                    new ParticleAura(loc, new Vec3d(3, 3, 3), new Vec3d(0, 0.1, 0), particleList, 1000000);
                    new ParticleTrail(loc, player, ParticleTypes.FALLING_WATER, 10, 0, 0);
                    new CircleParticleObject(loc.add(0, 2, 0), haloParticleList, colorList, 2, 1, 0.5, new Vec3d(0,-player.getYaw(),0), i);
                    i++;
                    i = i % 360;
                }
            }
        }
    }
}
