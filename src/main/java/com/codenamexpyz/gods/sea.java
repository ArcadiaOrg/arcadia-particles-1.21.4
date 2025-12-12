package com.codenamexpyz.gods;

import java.util.Arrays;
import java.util.List;

import java.awt.Color;

import com.codenamexpyz.objects.CircleParticleObject;
import com.codenamexpyz.objects.ParticleAura;
import com.codenamexpyz.objects.StaticParticles;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Vec3d;

public class sea extends GodBase {
    //Particle and color collections
    private final List<ParticleEffect> particleList = Arrays.asList(ParticleTypes.DRIPPING_DRIPSTONE_WATER);
    private final List<ParticleEffect> haloParticleList = Arrays.asList(ParticleTypes.END_ROD);
    private final List<Color> colorList = Arrays.asList(new Color(0/255, 255/255, 255/255), new Color(255/255, 255/255, 255/255)); 
    
    //Objects used. Only needs to be declaired once for ticking sake.
    private CircleParticleObject halo = new CircleParticleObject(loc.add(0, 2, 0), haloParticleList, colorList, 6, 0.5, 0.5, new Vec3d(0, godYaw(), 0), new Vec3d(0, 0.1, 0), false);
    private ParticleAura waterAura = new ParticleAura(loc, new Vec3d(3, 3, 3), new Vec3d(0, 0.1, 0), particleList, 5000000);

    @Override
    protected void godEffect(PlayerEntity player) {
        waterAura.tick(loc);
        halo.setRotation(new Vec3d(0, godYaw(), 0)).tick(loc.add(0, 2, 0));
        StaticParticles.UniqueParticleTrail(loc, player, ParticleTypes.END_ROD, null, 30, 1f, 0);
    }
}
