package com.codenamexpyz.gods;

import static com.codenamexpyz.ArcadiaParticlesClient.mc;

import java.util.List;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;

public abstract class GodBase { // THE GLORIOUS ABSTRACTION
    protected PlayerEntity godEntity = mc.player; //For init, so it isn't null
    protected Vec3d loc = godEntity.getPos();

    protected boolean isOnGround() {
        return godEntity.isOnGround();
    }

    protected double godYaw() {
        return -godEntity.getYaw();
    }

    public void triggerParticles(List<PlayerEntity> viewerList, PlayerEntity E) { 
        godEntity = E;
        loc = godEntity.getPos();

        for (PlayerEntity player : viewerList) { //This needs to be made more efficient, I will do it some year.
            if (player.getName().getLiteralString().equals(godEntity.getName().getString())) {
                godEffect(player);
            }
        }
    }

    protected abstract void godEffect(PlayerEntity player);
}
