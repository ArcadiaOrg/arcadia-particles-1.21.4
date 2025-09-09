package com.codenamexpyz.objects.FlareSpellAssets;

import java.util.List;

import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.math.Vec3d;

public class FlareSpellCircleReticule {
    List<FlareSpellCircle<? extends ParticleEffect>> flareCircles;
    double sizeCoeff;
    double velocCoeff;
    double initRadius; //Initial radius

    private Vec3d raytraceResult = Vec3d.ZERO;
    private int count;

    public FlareSpellCircleReticule(List<FlareSpellCircle<? extends ParticleEffect>> flareCircles, double sizeCoeff, double velocCoeff) {
        this.flareCircles = flareCircles;
        this.sizeCoeff = sizeCoeff;
        this.velocCoeff = velocCoeff;
        this.initRadius = flareCircles.getFirst().radius;
        this.count = 0;
    }

    public FlareSpellCircleReticule updateData(Vec3d raytraceResult) {
        this.raytraceResult = raytraceResult;
        return this;
    }

    public boolean tick() {
        if (count >= 100) {
            FlareSpellManager.reticulesAdded = false;
            return true;
        };

        spawnParticle(count);

        count++;
        return false;
    }

    public void spawnParticle(int count) {
        double nextRadius = initRadius;
        for (int i = 1; i < flareCircles.size() + 1; i++) {
            FlareSpellCircle<? extends ParticleEffect> circle = flareCircles.get(i - 1);

            circle.radius = nextRadius;

            circle.offset = circle.baseOffset.multiply(i * velocCoeff * Math.abs(Math.sin(Math.toRadians(count * 2)))).add(circle.baseOffset);

            nextRadius = initRadius + (i * sizeCoeff);

            circle.spawnParticle(raytraceResult, count);
        }
    }
}