package com.codenamexpyz.utils;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

import static com.codenamexpyz.ArcadiaParticlesClient.mc;

public class PlayerUtils {
    public static List<PlayerEntity> getNearbyPlayers() { //Unique method to get nearby players via the client
        Iterable<Entity> entities = mc.world.getEntities();
        List<PlayerEntity> playerList = new ArrayList<>();

        for (Entity entity : entities) {
            if (entity.isPlayer()) {
                playerList.add((PlayerEntity)entity);
            }
        }

        return playerList;
    }
}