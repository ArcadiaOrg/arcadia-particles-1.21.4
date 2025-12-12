package com.codenamexpyz.gods;

import java.util.Arrays;
import java.util.List;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Vec3d;

import com.codenamexpyz.objects.ParticleAura;

public class decay extends GodBase {
    //Particle collection
    private final List<ParticleEffect> particleList = Arrays.asList(ParticleTypes.MYCELIUM);

    //Objects used. Only needs to be declaired once for ticking sake.
    private ParticleAura decayingAura = new ParticleAura(loc, new Vec3d(3, 3, 3), new Vec3d(0, (isOnGround()) ? 0.05 : 0.035, 0), particleList, 225000);

    @Override
    protected void godEffect(PlayerEntity player) {
        decayingAura.setVelocity(new Vec3d(0, (isOnGround()) ? 0.07 : 0.035, 0)).tick(loc);
    }
}