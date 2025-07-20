package com.codenamexpyz.utils;

import static com.codenamexpyz.ArcadiaParticlesClient.mc;

import java.util.List;

import com.codenamexpyz.gods.bygones;
import com.codenamexpyz.gods.sky;
import com.codenamexpyz.gods.earth;
import com.codenamexpyz.gods.fury;
import com.codenamexpyz.gods.sea;
import com.codenamexpyz.gods.decay;

import net.minecraft.entity.player.PlayerEntity;

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

            /*if (player.getName().getLiteralString().equals("")) {
                fury.triggerParticles(nearbyPlayerEntities, player);
            }*/

            if (player.getName().getLiteralString().equals(mc.player.getName().getString())) { //for testing
                fury.triggerParticles(nearbyPlayerEntities, player);
            }
        }
    }
}