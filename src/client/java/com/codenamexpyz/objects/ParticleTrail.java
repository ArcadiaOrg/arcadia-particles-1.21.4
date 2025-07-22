package com.codenamexpyz.objects;

import org.jetbrains.annotations.Nullable;
import org.joml.Vector3d;

import net.minecraft.client.particle.Particle;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.math.Vec3d;

import com.codenamexpyz.utils.Rotator;

import static com.codenamexpyz.ArcadiaParticlesClient.mc;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ParticleTrail {
    private ThreadLocalRandom rand = ThreadLocalRandom.current();
    private final Vec3d pos;
    private final double yRot;
    private final List<? extends ParticleEffect> particleList;
    private final boolean withRandom;
    private final int duration;
    private final float scale;

    public ParticleTrail(Vec3d pos, double yRot, List<? extends ParticleEffect> particleList) {
        this.pos = pos;
        this.yRot = yRot;
        this.particleList = particleList;
        this.withRandom = false;
        this.duration = 20;
        this.scale = 1.0f;
    }

    public ParticleTrail(Vec3d pos, double yRot, List<? extends ParticleEffect> particleList, boolean withRandom) {
        this.pos = pos;
        this.yRot = yRot;
        this.particleList = particleList;
        this.withRandom = withRandom;
        this.duration = 20;
        this.scale = 1.0f;
    }

    public ParticleTrail(Vec3d pos, double yRot, List<? extends ParticleEffect> particleList, boolean withRandom , int duration) {
        this.pos = pos;
        this.yRot = yRot;
        this.particleList = particleList;
        this.withRandom = withRandom;
        this.duration = duration;
        this.scale = 1.0f;
    }

    public ParticleTrail(Vec3d pos, double yRot, List<? extends ParticleEffect> particleList, boolean withRandom , int duration, float scale) {
        this.pos = pos;
        this.yRot = yRot;
        this.particleList = particleList;
        this.withRandom = withRandom;
        this.duration = duration;
        this.scale = scale;
    }

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
            if (color != null) particle.setColor((float)color.x, (float)color.y, (float)color.z);
        }
    }

    public void tick() {
        for (double i = -0.2; i <= 0.2; i += 0.1) {
            Rotator rotator = new Rotator();

            rotator.rotateYQuaternion((float)(Math.toRadians(yRot)));

            Vector3d newRot = rotator.rotateVectorQuaternion(new Vector3d(i, 0, 0));
            
            Particle particle = mc.particleManager.addParticle(particleList.get(rand.nextInt(particleList.size())), pos.getX() + newRot.x, pos.getY() + newRot.y, pos.getZ() + newRot.z, 0, 0, 0);

            particle.scale((withRandom && rand.nextInt(2) == 0) ? 0.0f : scale);
            particle.setMaxAge(duration);
            particle.setVelocity(0,0,0);
        }
    }
}
