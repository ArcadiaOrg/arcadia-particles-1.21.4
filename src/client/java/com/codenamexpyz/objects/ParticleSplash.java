package com.codenamexpyz.objects;

import net.minecraft.client.particle.Particle;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.util.math.Vec3d;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import org.joml.Vector3d;

import com.codenamexpyz.utils.Rotator;

import static com.codenamexpyz.ArcadiaParticlesClient.mc;

public class ParticleSplash {
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    private double rad = 3; //Exists for the sake of the scheduler

    public ParticleSplash(Vec3d loc, SimpleParticleType particleType, double radius, double circleStep) {
        this.rad = radius;
        double increment = 0.1;
        double maxSize = 3;
        
        scheduler.scheduleAtFixedRate( () -> {
            if (this.rad > maxSize) {
                scheduler.shutdownNow();
            }

            for (int i = 0; i < 360; i += circleStep) {
                ThreadLocalRandom rand = ThreadLocalRandom.current();
                int chance = Math.max(1, (int)(this.rad * 2));
                int randNum = rand.nextInt(chance); //Should make the landing slightly unique, may need to add 

                if (randNum == 0) {
                    double angle = Math.toRadians(i);
                    double xOffset = Math.cos(angle) * (this.rad);
                    double zOffset = Math.sin(angle) * (this.rad);
                        
                    double particleX = loc.getX() + xOffset;
                    double particleY = loc.getY();
                    double particleZ = loc.getZ() + zOffset;

                    if (this.rad > 2) {
                        particleY += Math.sin(this.rad)/2;
                    }
    
                    Particle particle = mc.particleManager.addParticle(particleType, particleX, particleY, particleZ, 0, 0, 0);
                    particle.setMaxAge(6);
                }
            }

            rad += increment;
        }, 0, 6, TimeUnit.MILLISECONDS);
    }
    
    public ParticleSplash(Vec3d loc, SimpleParticleType particleType, double radius, double circleStep, float scale) {
        this.rad = radius;
        double increment = 0.1;
        double maxSize = 3;
        
        scheduler.scheduleAtFixedRate( () -> {
            if (this.rad > maxSize) {
                scheduler.shutdownNow();
            }

            for (int i = 0; i < 360; i += circleStep) {
                ThreadLocalRandom rand = ThreadLocalRandom.current();
                int chance = Math.max(1, (int)(this.rad * 2));
                int randNum = rand.nextInt(chance); //Should make the landing slightly unique, may need to add 

                if (randNum == 0) {
                    double angle = Math.toRadians(i);
                    double xOffset = Math.cos(angle) * (this.rad);
                    double zOffset = Math.sin(angle) * (this.rad);
                        
                    double particleX = loc.getX() + xOffset;
                    double particleY = loc.getY();
                    double particleZ = loc.getZ() + zOffset;

                    if (this.rad > 2) {
                        particleY += Math.sin(this.rad)/2;
                    }
    
                    Particle particle = mc.particleManager.addParticle(particleType, particleX, particleY, particleZ, 0, 0, 0);
                    particle.setMaxAge(6);
                    particle.scale(scale);
                }
            }

            rad += increment;
        }, 0, 6, TimeUnit.MILLISECONDS);
    }

    public ParticleSplash(Vec3d loc, Vec3d rotation, SimpleParticleType particleType, double radius, double circleStep, float scale) {
        this.rad = radius;
        double increment = 0.1;
        double maxSize = 3;
        
        scheduler.scheduleAtFixedRate( () -> {
            if (this.rad > maxSize) {
                scheduler.shutdownNow();
            }

            for (int i = 0; i < 360; i += circleStep) {
                ThreadLocalRandom rand = ThreadLocalRandom.current();
                int chance = Math.max(1, (int)(this.rad * 2));
                int randNum = rand.nextInt(chance); //Should make the landing slightly unique, may need to add 

                if (randNum == 0) {
                    double angle = Math.toRadians(i);
                    double xOffset = Math.cos(angle) * (this.rad);
                    double zOffset = Math.sin(angle) * (this.rad);
    
                    Rotator rotator = new Rotator();
                    
                    rotator.rotateYQuaternion((float)(Math.toRadians(rotation.getY())));
                    rotator.rotateXQuaternion((float)(Math.toRadians(rotation.getX())));
                    rotator.rotateZQuaternion((float)(Math.toRadians(rotation.getZ())));
                    
                    Vector3d rot = rotator.rotateVectorQuaternion(new Vector3d(xOffset, 0, zOffset));
                    
                    double particleX = loc.getX() + rot.x;
                    double particleY = loc.getY() + rot.y;
                    double particleZ = loc.getZ() + rot.z;

                    if (this.rad > 2) {
                        particleY += Math.sin(this.rad)/5;
                    }
    
                    Particle particle = mc.particleManager.addParticle(particleType, particleX, particleY, particleZ, 0, 0, 0);
                    particle.setMaxAge(6);
                    particle.scale(scale);
                }
            }

            rad += increment;
        }, 0, 6, TimeUnit.MILLISECONDS);
    }
}
