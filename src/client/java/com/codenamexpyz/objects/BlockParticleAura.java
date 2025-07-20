package com.codenamexpyz.objects;

import net.minecraft.client.particle.Particle;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.util.math.Vec3d;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static com.codenamexpyz.ArcadiaParticlesClient.mc;

public class BlockParticleAura {
    private final static int maxAge = 5;

    public BlockParticleAura(Vec3d loc, Vec3d radius, List<BlockStateParticleEffect> particleList, int randVal) {
        for (double i = -radius.x; i <= radius.x; i += 0.1) {
            for (double j = -radius.y; j <= radius.y; j += 0.1) {
                for (double k = -radius.z; k <= radius.z; k += 0.1) {
                    ThreadLocalRandom rand = ThreadLocalRandom.current();
                    int randNum = rand.nextInt(randVal);
                    int randPar = rand.nextInt(particleList.size());
        
                    if (randNum == (randVal-1)) {
                        Particle particle = mc.particleManager.addParticle(particleList.get(randPar), loc.x + k, loc.y + j + 1, loc.z + i, 0, 0, 0);
                        particle.setMaxAge(maxAge);
                        particle.setVelocity(0,0.23,0);
                    }
                }
            }
        }
    }
}