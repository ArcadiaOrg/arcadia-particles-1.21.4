package com.codenamexpyz.gods;

import java.util.Arrays;
import java.util.List;

import org.joml.Vector3d;

import net.minecraft.client.particle.Particle;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Vec3d;

import com.codenamexpyz.objects.ParticleAura;
import com.codenamexpyz.utils.Rotator;

import static com.codenamexpyz.ArcadiaParticlesClient.mc;

public class earth {
    private static final List<ParticleEffect> particleList = Arrays.asList(ParticleTypes.SPORE_BLOSSOM_AIR, ParticleTypes.SPORE_BLOSSOM_AIR);

    private static double deg = 0; //Exists for the sake of the counter

    public static void triggerParticles(List<PlayerEntity> viewerList, PlayerEntity godEntity) {
        Vec3d loc = godEntity.getPos();

        if (!mc.player.getName().equals(godEntity.getName())) {
            for (PlayerEntity player : viewerList) { //This needs to be made more efficient, I will do it some year.
                if (player.getName().getLiteralString().equals(godEntity.getName().getString())) {
                    new ParticleAura(loc, new Vec3d(3, 4, 3), new Vec3d(0, 0.21, 0), particleList, uniqueColorAura(loc, godEntity), 1000000).tick();

                    orbit(loc, ParticleTypes.FLAME, godEntity.getYaw(), 1, 180, false, false);
                    orbit(loc, ParticleTypes.RAIN, godEntity.getYaw(), 1, 0, false, false);
                    orbit(loc, ParticleTypes.ASH, godEntity.getYaw(), 1, 180, false, true);
                    orbit(loc, ParticleTypes.WAX_ON, godEntity.getYaw(), 1, 0, false, true);

                    deg += 2;
                    deg = deg % 360;
                }
            }
        }
    }

    private static List<Vec3d> uniqueColorAura(Vec3d loc, PlayerEntity godEntity) {
        double gModP2O = Math.max(Math.min(((loc.getY() + 64)*17)/192, 17), 0); //Green transfer
        double bModP2O = Math.max(Math.min(((loc.getY() + 64)*193)/192, 193), 0); //Blue transfer

        double modG2R = Math.max(Math.min(((loc.getY() + 64)*85)/128, 255), 0); //Transfer from 0 to 255 relative to Y

        String str = godEntity.getWorld().getBiome(godEntity.getBlockPos()).toString();

        if (str.contains("nether") || str.contains("soul") || str.contains("basalt") || str.contains("crimson") || str.contains("warped")) { //Nether biome check, given overworld dimension only
            return Arrays.asList(new Vec3d(255/255,185/255,193/255), new Vec3d(255/255, 0/255, 0));
        }

        return Arrays.asList(new Vec3d(255/255,(165+gModP2O)/255,bModP2O/255), new Vec3d((255-modG2R)/255,modG2R/255,0));// Pink to orange, green to red
    }



    private static void orbit(Vec3d loc, ParticleEffect particleType, double yaw, double radius, double degOffset, boolean invert, boolean rotCircle) { //I'm lazy
        double angle = (invert) ? Math.toRadians(deg + degOffset) : -Math.toRadians(deg + degOffset);
        double xOffset = Math.cos(angle) * radius/2;
        double zOffset = Math.sin(angle) * radius/1.5;

        Rotator rotator = new Rotator();
        
        rotator.rotateYQuaternion((float)(Math.toRadians(-yaw + 90))); //In a circle
        rotator.rotateXQuaternion((float)(Math.toRadians((rotCircle) ? -45 : 45))); //Flips
        rotator.rotateZQuaternion((float)(Math.toRadians(0)));
        
        Vector3d rot = rotator.rotateVectorQuaternion(new Vector3d(xOffset, 0, zOffset));
        
        double particleX = loc.getX() + rot.x;
        double particleY = loc.getY() + 1 + rot.y;
        double particleZ = loc.getZ() + rot.z;

        Particle particle = mc.particleManager.addParticle(particleType, particleX, particleY, particleZ, 0, 0, 0);
        particle.setMaxAge(3);

        
    }
}
