package com.codenamexpyz.objects;

import static com.codenamexpyz.ArcadiaParticlesClient.mc;

import java.util.List;
import java.util.Random;

import net.minecraft.client.particle.Particle;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.math.Vec3d;

public class StarParticle <T extends ParticleEffect> {
    private final Random rand = new Random();
    private final List<CircleParticleObject> rings;
    private Vec3d center;
    private Vec3d location;
    private final T centerParticle;
    private Vec3d driftVelocity = Vec3d.ZERO;
    
    public StarParticle(Vec3d center, List<CircleParticleObject> rings, T centerParticle) {
        this.center = center;
        this.location = center;
        this.rings = rings;
        this.centerParticle = centerParticle;
    }

    public void tick() {
        for (CircleParticleObject ring : rings) { //Ring Orbit
            ring.tick(location);
        }

        Particle particle = mc.particleManager.addParticle(centerParticle, location.x, location.y, location.z, 0, 0, 0);
        particle.setMaxAge(1);
        particle.scale(0.125f);
    }

    public StarParticle<T> centerMove(Vec3d center) {
        Vec3d offset = location.subtract(this.center);

        this.center = center;
        this.location = this.center.add(offset);
        return this;
    }

    public StarParticle<T> drift(PlayerEntity player, Vec3d maxVeloc, Vec3d limits) {
        driftVelocity = driftVelocity.add(
            (rand.nextDouble() - 0.5) * maxVeloc.x,
            (rand.nextDouble() - 0.5) * maxVeloc.y,
            (rand.nextDouble() - 0.5) * maxVeloc.z
        );

        driftVelocity = new Vec3d(
            clamp(driftVelocity.x, -maxVeloc.x, maxVeloc.x),
            clamp(driftVelocity.y, -maxVeloc.y, maxVeloc.y),
            clamp(driftVelocity.z, -maxVeloc.z, maxVeloc.z)
        );

        Vec3d newLoc = location.add(driftVelocity);

        if (Math.abs(newLoc.x - this.center.x) > limits.x) driftVelocity = new Vec3d(-driftVelocity.x, driftVelocity.y, driftVelocity.z);
        if (Math.abs(newLoc.y - this.center.y) > limits.y) driftVelocity = new Vec3d(driftVelocity.x, -driftVelocity.y, driftVelocity.z);
        if (Math.abs(newLoc.z - this.center.z) > limits.z) driftVelocity = new Vec3d(driftVelocity.x, driftVelocity.y, -driftVelocity.z);

        this.location = this.location.add(driftVelocity);

        return this;
    }

    public Vec3d getLocation() {
        return this.location;
    }

    public StarParticle<T> setLocation(Vec3d location) {
        this.location = location;
        return this;
    }

    public Vec3d getCenter() {
        return this.center;
    }
    
    public StarParticle<T> setCenter(Vec3d center) {
        this.center = center;
        return this;
    }

    private double clamp(double input, double min, double max) {
        return Math.max(min, Math.min(max, input));
    }
}
