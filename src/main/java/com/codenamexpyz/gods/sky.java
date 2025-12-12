package com.codenamexpyz.gods;

import java.util.Arrays;
import java.util.List;

import java.awt.Color;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.util.math.Vec3d;

import com.codenamexpyz.objects.CircleParticleObject;
import com.codenamexpyz.objects.ParticleSplash;
import com.codenamexpyz.objects.StaticParticles;
import com.codenamexpyz.utils.Managers.PlayerEffectManager;

public class sky extends GodBase {
    //Particle and color collections
    private final List<ParticleEffect> particleList = Arrays.asList(ParticleTypes.END_ROD, ParticleTypes.ELECTRIC_SPARK);
    private final List<Color> colorList = Arrays.asList(new Color(255/255, 255/255, 0), new Color(255/255, 255/255, 255/255)); //Gold and white

    //Objects used. Only needs to be declaired once for ticking sake.
    private CircleParticleObject halo = new CircleParticleObject(loc.add(0, 2, 0), particleList, colorList, 26, 0.5, 0.5, new Vec3d(0, godYaw(), 0), false);

    //For landing tracking
    private double timeOffGround = 0;

    @Override
    protected void godEffect(PlayerEntity player) {
        halo.setRotation(new Vec3d(0, godYaw(), 0)).tick(loc.add(0, 2, 0));
        StaticParticles.UniqueParticleTrail(loc, player, ParticleTypes.CLOUD, null, 20, 0.4f, 1.5f);

        if (godEntity.isOnGround() && (timeOffGround >= 13)) { 
            PlayerEffectManager.addSplash(new ParticleSplash<SimpleParticleType>(loc.add(0, 0.7, 0), ParticleTypes.CLOUD, 4, 0.03, 0, 0.75f, new Vec3d(0, 0, 0)));
        }

        timeOffGround = (godEntity.isOnGround()) ? 0 : timeOffGround + 1; //This is a crazy check that makes it only run once, and only if it's a sizable fall
    }
}
