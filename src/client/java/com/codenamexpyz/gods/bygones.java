package com.codenamexpyz.gods;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.awt.Color;

import org.joml.Vector3d;

import com.codenamexpyz.utils.Rotator;
import com.codenamexpyz.objects.BlockParticleAura;

import net.minecraft.block.Blocks;
import net.minecraft.client.particle.Particle;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.DustColorTransitionParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Vec3d;

import static com.codenamexpyz.ArcadiaParticlesClient.mc;

public class bygones {
    private static final List<BlockStateParticleEffect> particleList = Arrays.asList(new BlockStateParticleEffect(ParticleTypes.BLOCK, Blocks.GRAY_STAINED_GLASS.getDefaultState()), new BlockStateParticleEffect(ParticleTypes.BLOCK, Blocks.LIGHT_GRAY_STAINED_GLASS.getDefaultState()));

    public static void triggerParticles(List<PlayerEntity> viewerList, PlayerEntity godEntity) {
        Vec3d loc = godEntity.getPos();
        float playerYaw = godEntity.getHeadYaw();

        if (!mc.player.getName().equals(godEntity.getName())) {
            for (PlayerEntity player : viewerList) { //This needs to be made more efficient, I will do it some year.
                if (player.getName().getLiteralString().equals(godEntity.getName().getString())) {
                    haloHandle(loc, playerYaw);
                    visorHandle(player);
                    new BlockParticleAura(loc, new Vec3d(3, 3, 3), particleList, 500000);
                    ParticleTrail(loc, player);
                }
            }
        }
    }

    private static void haloHandle(Vec3d loc, float playerYaw) {
        double radius = 0.5;
        double circleStep = 4;
        double xRot = -20;
        double yRot= -playerYaw;
        double zRot = 0;
        double heightMod = 2;
        float scale = 0.2f;

        for (int i = 0; i < 360; i += circleStep) {
            double angle = Math.toRadians(i);
            double xOffset = Math.cos(angle) * radius;
            double zOffset = Math.sin(angle) * radius;

            Rotator rotator = new Rotator();
            
            rotator.rotateYQuaternion((float)(Math.toRadians(yRot)));
            rotator.rotateXQuaternion((float)(Math.toRadians(xRot)));
            rotator.rotateZQuaternion((float)(Math.toRadians(zRot)));
            
            Vector3d rot = rotator.rotateVectorQuaternion(new Vector3d(xOffset, 0, zOffset));
            
            double particleX = loc.getX() + rot.x;
            double particleY = loc.getY() + heightMod + rot.y;
            double particleZ = loc.getZ() + rot.z;

            ThreadLocalRandom rand = ThreadLocalRandom.current(); //drip logic
            int randNum = rand.nextInt(500);

            Particle particle = mc.particleManager.addParticle(particleList.get(rand.nextInt(particleList.size())), particleX, particleY, particleZ, 0, 0, 0);
            particle.setMaxAge(0);
            particle.scale(scale);

            if (randNum == 499) {
                particle.setMaxAge(40);
            }
        }
    }

    private static void visorHandle(PlayerEntity player) { 
        Vec3d loc = player.getEyePos();

        double pitch = player.getPitch();
        double yRot= -player.getYaw();
        double zRot = 0;
        double coefficient = 0.05;
        int randVal = 1000;
        double xOffset = 0;
        double yOffset = 0;

        for (double i = -0.25; i <= 0.25; i += 0.01) {
            for (double j = -0.25; j <= 0.25; j += 0.01) { 
                Rotator rotator = new Rotator();

                rotator.rotateYQuaternion((float)(Math.toRadians(yRot)));
                rotator.rotateXQuaternion((float)(Math.toRadians(pitch)));
                rotator.rotateZQuaternion((float)(Math.toRadians(zRot)));

                if (pitch >= 0) {
                    xOffset = 4*(Math.toRadians(pitch) * coefficient);
                    yOffset = -xOffset/2;
                } else {
                    xOffset = 3*(Math.toRadians(pitch) * coefficient);
                    yOffset = xOffset;
                }

                Vector3d rot = rotator.rotateVectorQuaternion(new Vector3d(i, j - yOffset, 0.3 + xOffset));

                double particleX = loc.getX() + rot.x;
                double particleY = loc.getY() + rot.y;
                double particleZ = loc.getZ() + rot.z;

                ThreadLocalRandom rand = ThreadLocalRandom.current(); //Random Visor Logic
                int randNum = rand.nextInt(randVal);
    
                if (randNum == (randVal-1)) {
                    Particle particle = mc.particleManager.addParticle(new DustColorTransitionParticleEffect(new Color(1, 1, 1).getRGB(), new Color(192,192,192).getRGB(), (float)0.2), particleX, particleY, particleZ, 0, 0, 0);
                    particle.setMaxAge(40);
                    particle.setVelocity(0,0,0);
                }
            }
        }
    }

    private static void ParticleTrail(Vec3d loc, PlayerEntity player) { //Unique case, requires its own function, not a standardized one
        if (player.isOnGround()) {
            ThreadLocalRandom rand = ThreadLocalRandom.current();
            int randNum = rand.nextInt(2);

            double yRot= -player.getYaw();

            for (double i = -0.2; i <= 0.2; i += 0.1) {
                Rotator rotator = new Rotator();

                rotator.rotateYQuaternion((float)(Math.toRadians(yRot)));

                Vector3d rot = rotator.rotateVectorQuaternion(new Vector3d(i, 0, 0));
                
                if (randNum != 1) {
                    mc.execute(() -> {
                        Particle particle = mc.particleManager.addParticle(particleList.get(rand.nextInt(particleList.size())), loc.x + rot.x, loc.y + rot.y, loc.z + rot.z, 0, 0, 0);
                        
                        particle.setMaxAge(20);
                        particle.setVelocity(0,0,0);
                    });
                }
            }
        }
    }
}
