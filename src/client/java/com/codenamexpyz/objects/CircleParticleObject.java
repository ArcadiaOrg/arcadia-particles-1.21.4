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
    public CircleParticleObject(Vec3d loc, List<? extends ParticleEffect> particleList, @Nullable List<Color> colorList, double totalParticles, double speed, double scale, double radius, Vec3d rotation, double count, boolean withDrip) {
        for (int i = 0; i < totalParticles; i++) { //Makes a circle based on the number of particles wanted
            Rotator rotator = new Rotator();
            ThreadLocalRandom rand = ThreadLocalRandom.current(); //Randomizer

            double angleRad = Math.toRadians(i*(360/totalParticles)); //Circle math

            double x = (Math.sin(angleRad) * radius);
            double z = (Math.cos(angleRad) * radius);

            rotator.rotateYQuaternion((float)Math.toRadians((speed*count) + rotation.getY())); //Spins it
            rotator.rotateXQuaternion((float)Math.toRadians(rotation.getX()));
            rotator.rotateZQuaternion((float)Math.toRadians(rotation.getZ()));

            Vector3d rot = rotator.rotateVectorQuaternion(new Vector3d(x, 0, z)); //Final rotations

            int particleType = i % particleList.size(); //Particle alternating

            Particle particle = mc.particleManager.addParticle(particleList.get(particleType), rot.x + loc.getX(), rot.y + loc.getY(), rot.z + loc.getZ(), 0, 0, 0);
            particle.setMaxAge((withDrip && rand.nextInt(500) == 499) ? 40 : 0); //Max age drip effect
            particle.scale((float)scale); //scale nonsense

            if (colorList != null) { //Colorlist logic
                Color heldColor = colorList.get(i % colorList.size());
                particle.setColor(heldColor.getRed(), heldColor.getGreen(), heldColor.getBlue());
            }
        }
    }
}
