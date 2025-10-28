package com.codenamexpyz.objects.FlareSpellAssets;

import org.jetbrains.annotations.Nullable;
import org.joml.Vector3d;

import com.codenamexpyz.utils.Rotator;

import net.minecraft.client.particle.Particle;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.math.Vec3d;

import static com.codenamexpyz.ArcadiaParticlesClient.mc;

public class FlareSpellCircle <T extends ParticleEffect> {
    Vec3d velocity;
    double radius;

    Vec3d rotation;
    public Vec3d offset;

    double totalParticles;

    private Vec3d color;
    private T particle;

    public Vec3d baseOffset;

    public FlareSpellCircle(@Nullable Vec3d offset, T particle, Vec3d color, double totalParticles, double radius, Vec3d velocity, Vec3d rotation) {
        this.offset = offset != null ? offset : Vec3d.ZERO;
        this.color = color;
        this.particle = particle;
        this.baseOffset = this.offset;
        this.totalParticles = totalParticles;
        this.radius = radius;
        this.velocity = velocity;
        this.rotation = rotation;
    }

    public void spawnParticle(Vec3d raytraceResult, double count) {
        Vec3d center = (raytraceResult.add(new Vec3d(0, 1, 0))); //Should add the target, in theory

        Rotator Globalrotator = new Rotator();

        Globalrotator.rotateYQuaternion((float)Math.toRadians(velocity.getY()*count));
        Globalrotator.rotateXQuaternion((float)Math.toRadians(velocity.getX()*count));
        Globalrotator.rotateZQuaternion((float)Math.toRadians(velocity.getZ()*count));

        for (int i = 0; i < totalParticles; i++) { //Makes a circle based on the number of particles wanted
            double angleRad = Math.toRadians(i*(360/totalParticles));

            Vector3d point = new Vector3d(
                    Math.sin(angleRad) * radius,
                    0,
                    Math.cos(angleRad) * radius
            );

            Rotator rotator = new Rotator();

            rotator.rotateYQuaternion((float)Math.toRadians(rotation.getY()));
            rotator.rotateXQuaternion((float)Math.toRadians(rotation.getX()));
            rotator.rotateZQuaternion((float)Math.toRadians(rotation.getZ()));

            Vector3d rotPoint = rotator.rotateVectorQuaternion(point);

            Vector3d rot = Globalrotator.rotateVectorQuaternion(rotPoint.add(offset.getX(), offset.getY(), offset.getZ()));

            Vec3d location = new Vec3d(
                    rot.x + center.getX(),
                    rot.y + center.getY(),
                    rot.z + center.getZ()
            );
            
            Particle particle = mc.particleManager.addParticle(this.particle, location.x, location.y, location.z, 0, 0, 0);
            particle.setMaxAge(0);
            particle.scale(5);
            particle.setColor((float)color.x/255, (float)color.y/255, (float)color.z/255);
        }
    }
}