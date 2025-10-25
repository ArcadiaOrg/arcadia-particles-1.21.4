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
import com.codenamexpyz.objects.ParticleTrail;
import com.codenamexpyz.objects.ParticleSplash.ParticleSplash;
import com.codenamexpyz.objects.ParticleSplash.SplashManager;

import static com.codenamexpyz.ArcadiaParticlesClient.config;
import static com.codenamexpyz.ArcadiaParticlesClient.mc;

public class sky {
    //Particle and color collections
    private static final List<ParticleEffect> particleList = Arrays.asList(ParticleTypes.END_ROD, ParticleTypes.ELECTRIC_SPARK);
    private static final List<Color> colorList = Arrays.asList(new Color(255/255, 255/255, 0), new Color(255/255, 255/255, 255/255)); //Gold and white

    //Objects used. Only needs to be declaired once for ticking sake.
    private static CircleParticleObject halo;

    //For landing tracking
    private static double timeOffGround = 0;

    public static void triggerParticles(List<PlayerEntity> viewerList, PlayerEntity godEntity) {
        Vec3d loc = godEntity.getPos();

        halo = new CircleParticleObject(loc.add(0, 2, 0), particleList, colorList, 26, 0.5, 0.5, new Vec3d(0, -godEntity.getHeadYaw(), 0), false);

        if (config.godSettings.toggleSky) {
            for (PlayerEntity player : viewerList) { //This needs to be made more efficient, I will do it some year
                if (player.getName().getLiteralString().equals(godEntity.getName().getString())) {
                    halo.tick(loc.add(0, 2, 0));
                    ParticleTrail.UniqueParticleTrail(loc, player, ParticleTypes.CLOUD, null, 20, 0.4f, 1.5f);

                    if (godEntity.isOnGround() && (timeOffGround >= 13 + ((mc.getCurrentFps() >= 60) ? 60 : mc.getCurrentFps()))) {  //ITS PER FRAMERATE NOW IT NEEDS TO WORK PLEASEEEE
                        SplashManager.addSplash(new ParticleSplash<SimpleParticleType>(loc.add(0, 0.7, 0), ParticleTypes.CLOUD, 4, 0.1, 0, 0.75f, new Vec3d(0, 0, 0)));
                    }

                    timeOffGround = (godEntity.isOnGround()) ? 0 : timeOffGround + 1; //This is a crazy check that makes it only run once, and only if it's a sizable fall
                }
            }
        }
    }
}
