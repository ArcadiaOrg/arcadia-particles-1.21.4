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
    //Particle and color collections
    private static final List<ParticleEffect> particleList = Arrays.asList(ParticleTypes.DRIPPING_DRIPSTONE_WATER);
    private static final List<ParticleEffect> haloParticleList = Arrays.asList(ParticleTypes.END_ROD);
    private static final List<Color> colorList = Arrays.asList(new Color(0/255, 255/255, 255/255), new Color(255/255, 255/255, 255/255)); 
    
    //Objects used. Only needs to be declaired once for ticking sake.
    private static CircleParticleObject halo;
    private static ParticleAura waterAura;

    public static void triggerParticles(List<PlayerEntity> viewerList, PlayerEntity godEntity) {
        Vec3d loc = godEntity.getPos();

        halo = new CircleParticleObject(loc.add(0, 2, 0), haloParticleList, colorList, 6, 0.5, 0.5, new Vec3d(0,-godEntity.getYaw(),0), new Vec3d(0, 1, 0), false);
        waterAura = new ParticleAura(loc, new Vec3d(3, 3, 3), new Vec3d(0, 0.1, 0), particleList, 5000000);

        if (!mc.player.getName().equals(godEntity.getName())) {
            for (PlayerEntity player : viewerList) { //This needs to be made more efficient, I will do it some year.
                if (player.getName().getLiteralString().equals(godEntity.getName().getString())) {
                    waterAura.tick();
                    halo.tick(loc);
                    ParticleTrail.UniqueParticleTrail(loc, player, ParticleTypes.END_ROD, null, 30, 1f, 0);
                }
            }
        }
    }
}
