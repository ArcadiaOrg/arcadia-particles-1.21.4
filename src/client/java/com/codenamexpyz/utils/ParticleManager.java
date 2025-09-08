package com.codenamexpyz.utils;

import java.util.Arrays;
import java.util.List;

import com.codenamexpyz.gods.bygones;
import com.codenamexpyz.gods.sky;
import com.codenamexpyz.objects.CircleParticleObject;
import com.codenamexpyz.objects.StarParticles.StarManager;
import com.codenamexpyz.objects.StarParticles.StarParticle;
import com.codenamexpyz.gods.earth;
import com.codenamexpyz.gods.fury;
import com.codenamexpyz.gods.sea;
import com.codenamexpyz.gods.decay;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Vec3d;

//import static com.codenamexpyz.ArcadiaParticlesClient.mc;

public class ParticleManager {
    public static void handleParticles() {
        List<PlayerEntity> nearbyPlayerEntities = PlayerUtils.getNearbyPlayers();
        
        for (PlayerEntity player : nearbyPlayerEntities) { //Check for if the god is nearby. If not, it doesn't call the particles.
            if (player.getName().getLiteralString().equals("Rasheairy")) {
                bygones.triggerParticles(nearbyPlayerEntities, player);
            }

            if (player.getName().getLiteralString().equals("IDKnows")) {
                sky.triggerParticles(nearbyPlayerEntities, player);
            }

            if (player.getName().getLiteralString().equals("ZombieDwarf")) {
                earth.triggerParticles(nearbyPlayerEntities, player);
            }

            if (player.getName().getLiteralString().equals("ssiba1_")) {
                decay.triggerParticles(nearbyPlayerEntities, player);
            }

            if (player.getName().getLiteralString().equals("zeteri_art")) {
                sea.triggerParticles(nearbyPlayerEntities, player);
            }

            if (player.getName().getLiteralString().equals("WadeBox")) {
                fury.triggerParticles(nearbyPlayerEntities, player);
            }

            if (player.getName().getLiteralString().equals("xpyz")) { //for testing mc.player.getName().getString()
                if (StarManager.getNumOfStars() < 3) {
                    StarManager.addStar(
                        new StarParticle<ParticleEffect>(
                            new Vec3d(0, 0, 0), 
                            Arrays.asList(
                                new CircleParticleObject(new Vec3d(0, 0, 0), Arrays.asList(ParticleTypes.ENCHANT), null, 45, 0.05, 0.0675, new Vec3d(0, 0, 0), new Vec3d(0.04, 0.04, 0.04), false),
                                new CircleParticleObject(new Vec3d(0, 0, 0), Arrays.asList(ParticleTypes.ENCHANT), null, 45, 0.05, 0.0675, new Vec3d(90, 0, 0), new Vec3d(0.04, 0.04, 0.04), false),
                                new CircleParticleObject(new Vec3d(0, 0, 0), Arrays.asList(ParticleTypes.ENCHANT), null, 45, 0.05, 0.0675, new Vec3d(0, 0, 90), new Vec3d(0.04, 0.04, 0.04), false)
                            ), 
                            ParticleTypes.ELECTRIC_SPARK
                        ), 
                    player);
                } 

                StarManager.tick(player);
            }
        }
    }
}