package com.codenamexpyz.objects;

import static com.codenamexpyz.ArcadiaParticlesClient.mc;

import java.util.concurrent.ThreadLocalRandom;

import org.joml.Vector3d;

import com.codenamexpyz.utils.Rotator;

import net.minecraft.client.particle.Particle;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleEffect;

public class ParticleBurn <T extends ParticleEffect> {
    private final PlayerEntity player;
    private final T particleType;
    private final int randVal;

    public ParticleBurn(PlayerEntity player, T particleType, int randVal) {
        this.player = player;
        this.particleType = particleType;
        this.randVal = randVal;
    }

    public void tick() {
        for (double i = -0.4; i <= 0.4; i += 0.1) { 
            Rotator rotator = new Rotator();
            rotator.rotateYQuaternion((float)(Math.toRadians(-player.getYaw())));

            Vector3d newRot = rotator.rotateVectorQuaternion(new Vector3d(i, 0, 0));

            ThreadLocalRandom rand = ThreadLocalRandom.current();
            int randNum = rand.nextInt(randVal);
        
            if (randNum == (randVal-1)) {
                Particle particle = mc.particleManager.addParticle(particleType, player.getX() + newRot.x, player.getY() + 1.4 + newRot.y, player.getZ() + newRot.z, 0, 0, 0);
                particle.setMaxAge(80 - rand.nextInt(41));
                particle.setVelocity(0, 0.03 - (rand.nextInt(20) * 0.001), 0);
            }
        }
    }
}
