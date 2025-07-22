package com.codenamexpyz.gods;

import java.util.Arrays;
import java.util.List;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Vec3d;

import com.codenamexpyz.objects.ParticleAura;

import static com.codenamexpyz.ArcadiaParticlesClient.mc;

public class decay {
    private static final List<ParticleEffect> particleList = Arrays.asList(ParticleTypes.MYCELIUM);

    public static void triggerParticles(List<PlayerEntity> viewerList, PlayerEntity godEntity) {
        Vec3d loc = godEntity.getPos();

        if (!mc.player.getName().equals(godEntity.getName())) {
            for (PlayerEntity player : viewerList) { //This needs to be made more efficient, I will do it some year.
                if (player.getName().getLiteralString().equals(godEntity.getName().getString())) {
                    if (godEntity.isOnGround()) {
                        new ParticleAura(loc, new Vec3d(3, 3, 3), new Vec3d(0, 0.05, 0), particleList, 250000).tick(); //Ground vs non-ground logic
                    } else new ParticleAura(loc, new Vec3d(3, 3, 3), new Vec3d(0, 0.035, 0), particleList, 1250000).tick();
                }
            }
        }
    }
}