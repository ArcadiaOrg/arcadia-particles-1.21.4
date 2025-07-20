package com.codenamexpyz.objects;

import org.joml.Vector3d;

import net.minecraft.client.particle.Particle;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.util.math.Vec3d;

import com.codenamexpyz.utils.Rotator;

import static com.codenamexpyz.ArcadiaParticlesClient.mc;

public class ParticleTrail {
    public ParticleTrail(Vec3d pos, Vec3d rot, SimpleParticleType particleType) {
        double yRot = -rot.getY();

        for (double i = -0.2; i <= 0.2; i += 0.1) {
            Rotator rotator = new Rotator();

            rotator.rotateYQuaternion((float)(Math.toRadians(yRot)));

            Vector3d newRot = rotator.rotateVectorQuaternion(new Vector3d(i, 0, 0));
            
            Particle particle = mc.particleManager.addParticle(particleType, pos.getX() + newRot.x, pos.getY() + newRot.y, pos.getZ() + newRot.z, 0, 0, 0);

            particle.scale(1.5f);
            particle.setMaxAge(20);
            particle.setVelocity(0,0,0);
        }
    }

    public ParticleTrail(Vec3d pos, Vec3d rot, SimpleParticleType particleType, int duration) {
        double yRot = -rot.getY();

        for (double i = -0.2; i <= 0.2; i += 0.1) {
            Rotator rotator = new Rotator();

            rotator.rotateYQuaternion((float)(Math.toRadians(yRot)));

            Vector3d newRot = rotator.rotateVectorQuaternion(new Vector3d(i, 0, 0));
            
            Particle particle = mc.particleManager.addParticle(particleType, pos.getX() + newRot.x, pos.getY() + newRot.y, pos.getZ() + newRot.z, 0, 0, 0);

            particle.scale(1.5f);
            particle.setMaxAge(duration);
            particle.setVelocity(0,0,0);
        }
    }

    public ParticleTrail(Vec3d pos, Vec3d rot, SimpleParticleType particleType, int duration, float scale) {
        double yRot = -rot.getY();

        for (double i = -0.2; i <= 0.2; i += 0.1) {
            Rotator rotator = new Rotator();

            rotator.rotateYQuaternion((float)(Math.toRadians(yRot)));

            Vector3d newRot = rotator.rotateVectorQuaternion(new Vector3d(i, 0, 0));
            
            Particle particle = mc.particleManager.addParticle(particleType, pos.getX() + newRot.x, pos.getY() + newRot.y, pos.getZ() + newRot.z, 0, 0, 0);

            particle.scale(scale);
            particle.setMaxAge(duration);
            particle.setVelocity(0,0,0);
        }
    }

    public ParticleTrail(Vec3d pos, PlayerEntity player, SimpleParticleType particleType, int duration, float groundScale, float flyingScale) {
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
        }
    }
}
