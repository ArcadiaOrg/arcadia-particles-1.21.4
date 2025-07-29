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
    //Particle collection
    private static final List<ParticleEffect> particleList = Arrays.asList(ParticleTypes.MYCELIUM);

    //Objects used. Only needs to be declaired once for ticking sake.
    private static ParticleAura decayingAura;

    public static void triggerParticles(List<PlayerEntity> viewerList, PlayerEntity godEntity) {
        decayingAura = new ParticleAura(godEntity.getPos(), new Vec3d(3, 3, 3), new Vec3d(0, (godEntity.isOnGround()) ? 0.05 : 0.035, 0), particleList, 250000); //decaying aura based on ground or not

        if (!mc.player.getName().equals(godEntity.getName())) {
            for (PlayerEntity player : viewerList) { //This needs to be made more efficient, I will do it some year.
                if (player.getName().getLiteralString().equals(godEntity.getName().getString())) {
                    decayingAura.tick();
                }
            }
        }
    }
}