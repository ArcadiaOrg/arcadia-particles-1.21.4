package com.codenamexpyz.gods;

import java.util.Arrays;
import java.util.List;
import java.awt.Color;

import org.joml.Vector3d;

import net.minecraft.client.particle.Particle;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Vec3d;

import com.codenamexpyz.utils.Rotator;
import com.codenamexpyz.objects.ParticleSplash;
import com.codenamexpyz.objects.ParticleTrail;

import static com.codenamexpyz.ArcadiaParticlesClient.mc;

public class sky {
    private static final List<ParticleEffect> particleList = Arrays.asList(ParticleTypes.END_ROD, ParticleTypes.ELECTRIC_SPARK);
    private static final List<Color> colorList = Arrays.asList(new Color(255/255, 255/255, 0), new Color(255/255, 255/255, 255/255)); //Gold and white

    //For landing tracking
    private static double timeOffGround = 0;

    public static void triggerParticles(List<PlayerEntity> viewerList, PlayerEntity godEntity) {
        Vec3d loc = godEntity.getPos();
        float playerYaw = godEntity.getHeadYaw();

        if (!mc.player.getName().equals(godEntity.getName())) {
            for (PlayerEntity player : viewerList) { //This needs to be made more efficient, I will do it some year.
                if (player.getName().getLiteralString().equals(godEntity.getName().getString())) {
                    haloHandle(loc, playerYaw);
                    new ParticleTrail(loc, player, ParticleTypes.CLOUD, 20, 0.4f, 1.5f);

                    if (godEntity.isOnGround() && (timeOffGround > 14)) {
                        new ParticleSplash(loc.add(0, 0.7, 0), new Vec3d(0, 0, 0), ParticleTypes.CLOUD, 0, 1, 1f);
                    }

                    if (!godEntity.isOnGround()) { //This is a crazy check that makes it only run once, and only if it's a sizable fall
                        timeOffGround++;
                    } else {
                        timeOffGround = 0;
                    }
                }
            }
        }
    }

    private static void haloHandle(Vec3d loc, float playerYaw) { //This is for the crown of determination, which rests upon the worthy head of a friend.
        double radius = 0.5;
        double circleStep = 13;
        double xRot = 0;
        double yRot= -playerYaw;
        double zRot = 0;
        double heightMod = 2;
        float scale = 0.5f;

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

            int particleType = (1 + i/(int)circleStep) % 2;
            int colorType = (i/(int)circleStep) % 2;
            
            Particle particle = mc.particleManager.addParticle(particleList.get(particleType), particleX, particleY, particleZ, 0, 0, 0);
            particle.setMaxAge(0);
            particle.scale(scale);

            if (colorType == 0) {
                Color heldColor = colorList.get(colorType);
                particle.setColor(heldColor.getRed(), heldColor.getGreen(), heldColor.getBlue());
            } else {
                Color heldColor = colorList.get(colorType);
                particle.setColor(heldColor.getRed(), heldColor.getGreen(), heldColor.getBlue());
            }
        }
        
    }
}
