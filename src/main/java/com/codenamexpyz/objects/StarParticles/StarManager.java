package com.codenamexpyz.objects.StarParticles;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.math.Vec3d;

public class StarManager {
    private static ThreadLocalRandom rand = ThreadLocalRandom.current();
    private static final List<StarParticle<? extends ParticleEffect>> stars = new ArrayList<>();
    private static final Vec3d areaLimit = new Vec3d(0.75, 0.6, 0.75);

    public static void addStar(StarParticle<? extends ParticleEffect> star, PlayerEntity player) {
        Vec3d center = player.getPos().add(0, 1, 0);

        Vec3d offset = new Vec3d(
            rand.nextDouble(areaLimit.getX()),
            rand.nextDouble(areaLimit.getY()),
            rand.nextDouble(areaLimit.getZ())
        );

        Vec3d loc = center.add(offset);

        stars.add(star.setCenter(center).setLocation(loc));
    }

    public static void removeStar() {
        stars.removeFirst();
    }

    public static int getNumOfStars() {
        return stars.size();
    }

    public static void tick(PlayerEntity player) {
        if (stars != null) for (StarParticle<? extends ParticleEffect> star : stars) {
            star.centerMove(player.getPos().add(0, 1, 0));
            
            if (rand.nextInt(3) == 0) star
                .drift(player, new Vec3d(0.004, 0.002, 0.004), areaLimit)
                .tick();            
        }
    }
}
