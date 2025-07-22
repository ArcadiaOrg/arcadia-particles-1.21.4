package com.codenamexpyz.objects;

import net.minecraft.client.particle.Particle;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.math.Vec3d;

import java.util.concurrent.ThreadLocalRandom;

import org.joml.Vector3d;

import com.codenamexpyz.utils.Rotator;

import static com.codenamexpyz.ArcadiaParticlesClient.mc;

public class ParticleSplash <T extends ParticleEffect> {
    private final Vec3d loc; //Activation location
    private final T particleType; //Particle to use
    private final double circleStep; //How many particles per ring
    private double increment; //How fast it jumps to the next increment
    private final float scale; //Particle size
    private double radius; //Starting point for the splash
    private double maxSize; //Max size for the ring
    private final Vec3d rotation; //Any fancy rotations

    public ParticleSplash(Vec3d loc, T particleType, double radius, double circleStep) {
        this.loc = loc;
        this.particleType = particleType;
        this.circleStep = circleStep;
        this.increment = 0.1;
        this.scale = 1;
        this.radius = radius;
        this.maxSize = Math.PI + 0.3;
        this.rotation = new Vec3d(0, 0, 0);
    }
    
    public ParticleSplash(Vec3d loc, T particleType, double radius, double circleStep, float scale) {
        this.loc = loc;
        this.particleType = particleType;
        this.circleStep = circleStep;
        this.increment = 0.1;
        this.scale = scale;
        this.radius = radius;
        this.maxSize = Math.PI + 0.3;
        this.rotation = new Vec3d(0, 0, 0);
    }

    public ParticleSplash(Vec3d loc, T particleType, double circleStep, double increment, double radius, float scale, Vec3d rotation) {
        this.loc = loc;
        this.particleType = particleType;
        this.circleStep = circleStep;
        this.increment = increment;
        this.scale = scale;
        this.radius = radius;
        this.maxSize = Math.PI + 0.3;
        this.rotation = rotation;
    }

    public boolean tick() {
        if (this.radius > this.maxSize) return true; //Kills it when it goes over the max

        for (int i = 0; i < 360; i += this.circleStep) {
            ThreadLocalRandom rand = ThreadLocalRandom.current();
            int chance = Math.max(1, (int)(this.radius));
            int randNum = rand.nextInt(chance); //Should make the landing slightly unique

            if (randNum == 0) { //Random
                double angle = Math.toRadians(i);
                double xOffset = Math.cos(angle) * (this.radius);
                double zOffset = Math.sin(angle) * (this.radius);

                Rotator rotator = new Rotator();
                
                rotator.rotateYQuaternion((float)(Math.toRadians(this.rotation.getY())));
                rotator.rotateXQuaternion((float)(Math.toRadians(this.rotation.getX())));
                rotator.rotateZQuaternion((float)(Math.toRadians(this.rotation.getZ())));
                
                Vector3d rot = rotator.rotateVectorQuaternion(new Vector3d(xOffset, 0, zOffset)); //Not really used, for potential future cases
                
                double particleX = this.loc.getX() + rot.x;
                double particleY = this.loc.getY() + rot.y;
                double particleZ = this.loc.getZ() + rot.z;

                if (this.radius < ((3*Math.PI)/2)) {
                    particleY -= (Math.sin(2*this.radius)/3); //Sin wave jump
                }

                Particle particle = mc.particleManager.addParticle(this.particleType, particleX, particleY, particleZ, 0, 0, 0);
                particle.setMaxAge(6);
                particle.scale(this.scale);
            }
        }

        radius += increment; //Increases the explode size
        return false; //Keeps it running
    }
}
