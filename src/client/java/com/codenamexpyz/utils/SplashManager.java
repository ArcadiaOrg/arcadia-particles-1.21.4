package com.codenamexpyz.utils;

import java.util.ArrayList;
import java.util.List;

import com.codenamexpyz.objects.ParticleSplash;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.particle.ParticleEffect;

public class SplashManager {
    private static final List<ParticleSplash<? extends ParticleEffect>> Splash = new ArrayList<>();

    public static void init() {
        WorldRenderEvents.END.register(client -> {
            Splash.removeIf(ParticleSplash::tick);
        });
    }

    public static void addSplash(ParticleSplash<? extends ParticleEffect> splash) {
        Splash.add(splash);
    }
}
