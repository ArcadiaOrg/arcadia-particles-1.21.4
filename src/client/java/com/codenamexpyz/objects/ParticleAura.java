package com.codenamexpyz.objects;

import net.minecraft.client.particle.Particle;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.math.Vec3d;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.jetbrains.annotations.Nullable;

import static com.codenamexpyz.ArcadiaParticlesClient.mc;

public class ParticleAura {
    private final static int maxAge = 5;
    private final Vec3d loc;
    private final Vec3d radius;
    private final Vec3d veloc;
    private final List<? extends ParticleEffect> particleList;
    private final List<Vec3d> colorList;
    private final int randVal;
    


    public ParticleAura(Vec3d loc, Vec3d radius, Vec3d veloc, List<? extends ParticleEffect> particleList, int randVal) {
        this.loc = loc;
        this.radius = radius;
        this.veloc = veloc;
        this.particleList = particleList;
        this.colorList = null;
        this.randVal = randVal;
    }

    public ParticleAura(Vec3d loc, Vec3d radius, Vec3d veloc, List<? extends ParticleEffect> particleList, @Nullable List<Vec3d> colorList, int randVal) {
        this.loc = loc;
        this.radius = radius;
        this.veloc = veloc;
        this.particleList = particleList;
        this.colorList = colorList;
        this.randVal = randVal;
    }

    public void tick() {
        for (double i = -radius.x; i <= radius.x; i += 0.1) {
            for (double j = -radius.y; j <= radius.y; j += 0.1) {
                for (double k = -radius.z; k <= radius.z; k += 0.1) {
                    ThreadLocalRandom rand = ThreadLocalRandom.current();
                    int randNum = rand.nextInt(randVal);
                    int randPar = rand.nextInt(particleList.size());
        
                    if (randNum == (randVal-1)) {
                        Particle particle = mc.particleManager.addParticle(particleList.get(randPar), loc.x + k, loc.y + j + 1, loc.z + i, 0, 0, 0);
                        if (colorList != null) particle.setColor((float)colorList.get(randPar).getX(), (float)colorList.get(randPar).getY(), (float)colorList.get(randPar).getZ());
                        particle.setMaxAge(maxAge);
                        particle.setVelocity(veloc.getX(),veloc.getY(),veloc.getZ());
                    }
                }
            }
        }
    }
}