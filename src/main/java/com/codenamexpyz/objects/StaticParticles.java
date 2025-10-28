package com.codenamexpyz.objects;

import static com.codenamexpyz.ArcadiaParticlesClient.mc;

import java.util.List;
import java.util.Random;

import org.jetbrains.annotations.Nullable;
import org.joml.Vector3d;

import com.codenamexpyz.utils.Rotator;

import net.minecraft.client.particle.Particle;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.math.Vec3d;

public class StaticParticles {
    private static final Random rand = new Random();
    
    public static <T extends ParticleEffect>void UniqueParticleTrail(Vec3d pos, PlayerEntity player, T particleType, @Nullable Vec3d color, int duration, float groundScale, float flyingScale) { //Unique case
        double yRot = -player.getBodyYaw();

        for (double i = -0.2; i <= 0.2; i += 0.1) {
            Rotator rotator = new Rotator();

            rotator.rotateYQuaternion((float)(Math.toRadians(yRot)));

            Vector3d newRot = rotator.rotateVectorQuaternion(new Vector3d(i, 0, 0));

            Particle particle = mc.particleManager.addParticle(particleType, pos.getX() + newRot.x, pos.getY() + newRot.y, pos.getZ() + newRot.z, 0, 0, 0);

            if (player.isOnGround()) { //Relative scaling
                particle.scale(groundScale);
            } else {
                particle.scale(flyingScale);
            }

            particle.setMaxAge(duration);
            particle.setVelocity(0,0,0);
            if (color != null) particle.setColor((float)color.x/255, (float)color.y/255, (float)color.z/255);
        }
    }

    public static void ParticleJet(Vec3d pos, List<? extends ParticleEffect> particleList, @Nullable Vec3d color, int amount, int duration) {
        for (int i = 0; i < amount; i ++) {
            for (ParticleEffect particleType : particleList) {
                int shouldFlipX = (rand.nextBoolean()) ? -1 : 1; //I am lazy, but there *has* to be a better way to do this.
                int shouldFlipZ = (rand.nextBoolean()) ? -1 : 1;
                Particle particle = mc.particleManager.addParticle(particleType, pos.getX(), pos.getY(), pos.getZ(), rand.nextDouble() * shouldFlipX * 0.1, -rand.nextDouble() * 0.3, rand.nextDouble() * shouldFlipZ * 0.1);

                particle.setMaxAge(duration);
                particle.scale(0.3f);
                if (color != null) particle.setColor((float)color.x/255, (float)color.y/255, (float)color.z/255);
            }
        }
        
    }
}
