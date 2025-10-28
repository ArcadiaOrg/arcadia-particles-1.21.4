package com.codenamexpyz.objects;

import java.awt.Color;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.jetbrains.annotations.Nullable;
import org.joml.Vector3d;

import com.codenamexpyz.utils.Rotator;

import net.minecraft.client.particle.Particle;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.math.Vec3d;

import static com.codenamexpyz.ArcadiaParticlesClient.mc;

public class CircleParticleObject {
    private Vec3d loc;
    private final List<? extends ParticleEffect> particleList;
    private final List<Color> colorList;
    private final double totalParticles;
    private final double scale;
    private final double radius;
    private Vec3d rotation;
    private final Vec3d rotationVeloc;
    private final boolean withDrip;
    private double count;

    public CircleParticleObject(Vec3d loc, List<? extends ParticleEffect> particleList, @Nullable List<Color> colorList, double totalParticles, double scale, double radius, Vec3d rotation, boolean withDrip) {
        this.loc = loc;
        this.particleList = particleList;
        this.colorList = colorList;
        this.totalParticles = totalParticles;
        this.scale = scale;
        this.radius = radius;
        this.rotation = rotation;
        this.rotationVeloc = new Vec3d(0, 0, 0);
        this.withDrip = withDrip;
        count = 0;
    }

    public CircleParticleObject(Vec3d loc, List<? extends ParticleEffect> particleList, @Nullable List<Color> colorList, double totalParticles, double scale, double radius, Vec3d rotation, Vec3d rotationVeloc, boolean withDrip) {
        this.loc = loc;
        this.particleList = particleList;
        this.colorList = colorList;
        this.totalParticles = totalParticles;
        this.scale = scale;
        this.radius = radius;
        this.rotation = rotation;
        this.rotationVeloc = rotationVeloc;
        this.withDrip = withDrip;
        count = 0;
    }

    public CircleParticleObject setRotation(Vec3d rotation) {
        this.rotation = rotation;
        return this;
    }

    public void tick(Vec3d loc) { //Ticks the object forwards
        this.loc = loc;
        
        for (int i = 0; i < totalParticles; i++) { //Makes a circle based on the number of particles wanted
            Rotator rotator = new Rotator();
            ThreadLocalRandom rand = ThreadLocalRandom.current(); //Randomizer

            double angleRad = Math.toRadians(i*(360/totalParticles)); //Circle math

            double x = (Math.sin(angleRad) * radius);
            double z = (Math.cos(angleRad) * radius);

            rotator.rotateYQuaternion((float)Math.toRadians((rotationVeloc.getY()*count) + rotation.getY())); //Spins it
            rotator.rotateXQuaternion((float)Math.toRadians((rotationVeloc.getX()*count) + rotation.getX()));
            rotator.rotateZQuaternion((float)Math.toRadians((rotationVeloc.getZ()*count) + rotation.getZ()));

            Vector3d rot = rotator.rotateVectorQuaternion(new Vector3d(x, 0, z)); //Final rotations

            int particleType = i % particleList.size(); //Particle alternating

            Particle particle = mc.particleManager.addParticle(particleList.get(particleType), rot.x + this.loc.getX(), rot.y + this.loc.getY(), rot.z + this.loc.getZ(), 0, 0, 0);
            particle.setMaxAge((withDrip && rand.nextInt(500) == 499) ? 40 : 0); //Max age drip effect
            particle.scale((float)scale); //scale nonsense

            if (colorList != null) { //Colorlist logic
                Color heldColor = colorList.get(i % colorList.size());
                particle.setColor(heldColor.getRed(), heldColor.getGreen(), heldColor.getBlue());
            }

            count++;
            count = count % (360/rotationVeloc.getY()); //Y should be the general speed modifier
        }
    }
}
