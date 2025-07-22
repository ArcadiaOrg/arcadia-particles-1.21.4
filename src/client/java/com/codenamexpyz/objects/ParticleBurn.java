package com.codenamexpyz.objects;

import static com.codenamexpyz.ArcadiaParticlesClient.mc;

import java.util.concurrent.ThreadLocalRandom;

import org.joml.Vector3d;

import com.codenamexpyz.utils.Rotator;

import net.minecraft.client.particle.Particle;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.DustColorTransitionParticleEffect;
import net.minecraft.util.math.Vec3d;

public class ParticleBurn {
    public ParticleBurn(Vec3d pos, PlayerEntity player, DustColorTransitionParticleEffect particleType, int randVal) {
        for (double i = -0.4; i <= 0.4; i += 0.1) { 
            Rotator rotator = new Rotator();
            rotator.rotateYQuaternion((float)(Math.toRadians(-player.getYaw())));

            Vector3d newRot = rotator.rotateVectorQuaternion(new Vector3d(i, 0, 0));

            ThreadLocalRandom rand = ThreadLocalRandom.current();
            int randNum = rand.nextInt(randVal);
        
            if (randNum == (randVal-1)) {
                Particle particle = mc.particleManager.addParticle(particleType, pos.getX() + newRot.x, pos.getY() + newRot.y, pos.getZ() + newRot.z, 0, 0, 0);
                particle.setMaxAge(80 - rand.nextInt(41));
                particle.setVelocity(0, 0.03 - (rand.nextInt(20) * 0.001), 0);
            }
        }
    }
}
