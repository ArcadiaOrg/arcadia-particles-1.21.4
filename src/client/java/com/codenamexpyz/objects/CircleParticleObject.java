package com.codenamexpyz.objects;

import java.awt.Color;
import java.util.List;

import org.joml.Vector3d;

import com.codenamexpyz.utils.Rotator;

import net.minecraft.client.particle.Particle;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.math.Vec3d;

import static com.codenamexpyz.ArcadiaParticlesClient.mc;

public class CircleParticleObject {
    public CircleParticleObject(Vec3d loc, List<ParticleEffect> particleList, List<Color> colorList, double totalParticles, double speed, double radius, Vec3d rotation, double count) {
        for (int i = 0; i < totalParticles; i++) { //Makes a circle based on the number of particles wanted
            Rotator rotator = new Rotator();

            double angleRad = Math.toRadians(i*(360/totalParticles)); //I forgot this in the main code you gotta be kidding me

            double x = (Math.sin(angleRad) * radius);
            double z = (Math.cos(angleRad) * radius);

            rotator.rotateYQuaternion((float)Math.toRadians((speed*count) + rotation.getY()));
            rotator.rotateXQuaternion((float)Math.toRadians(rotation.getX()));
            rotator.rotateZQuaternion((float)Math.toRadians(rotation.getZ()));

            Vector3d rot = rotator.rotateVectorQuaternion(new Vector3d(x, 0, z));

            int particleType = i % particleList.size();
            int colorType = i % colorList.size();

            Particle particle = mc.particleManager.addParticle(particleList.get(particleType), rot.x + loc.getX(), rot.y + loc.getY(), rot.z + loc.getZ(), 0, 0, 0);
            particle.setMaxAge(0);
            particle.scale(1);

            Color heldColor = colorList.get(colorType);
            particle.setColor(heldColor.getRed(), heldColor.getGreen(), heldColor.getBlue());
        }
    }
}
