package com.codenamexpyz.gods;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.awt.Color;

import org.joml.Vector2d;
import org.joml.Vector3d;

import com.codenamexpyz.utils.Rotator;
import com.codenamexpyz.objects.CircleParticleObject;
import com.codenamexpyz.objects.ParticleAura;
import com.codenamexpyz.objects.ParticleTrail;
import com.codenamexpyz.objects.WhisperSoundTickable;

import net.minecraft.block.Blocks;
import net.minecraft.client.particle.Particle;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.DustColorTransitionParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.Vec3d;

import static com.codenamexpyz.ArcadiaParticlesClient.mc;
import static com.codenamexpyz.ArcadiaParticles.whispersEvent;

public class bygones {
    private static ThreadLocalRandom rand = ThreadLocalRandom.current();
    private static final List<BlockStateParticleEffect> particleList = Arrays.asList(new BlockStateParticleEffect(ParticleTypes.BLOCK, Blocks.GRAY_STAINED_GLASS.getDefaultState()), new BlockStateParticleEffect(ParticleTypes.BLOCK, Blocks.LIGHT_GRAY_STAINED_GLASS.getDefaultState()));
    private static WhisperSoundTickable whisper;
    private static boolean once = true;
    private static boolean faded = false;
    private static double i = 0; //Count
    private static double k = 0; //For the fading

    public static void triggerParticles(List<PlayerEntity> viewerList, PlayerEntity godEntity) {
        Vec3d loc = godEntity.getPos();

        if (!mc.player.getName().equals(godEntity.getName())) {
            for (PlayerEntity player : viewerList) { //This needs to be made more efficient, I will do it some year.
                if (player.getName().getLiteralString().equals(godEntity.getName().getString())) {
                    double dist = new Vector2d(mc.player.getX(), mc.player.getZ()).distance(0, 0);
                    double maxDist = 40;
                    double fadeVal = 180;
                    double modifier = 0.15;

                    if (once) {
                        whisper = new WhisperSoundTickable(player, whispersEvent, SoundCategory.MASTER);
                        once = !once;
                    }

                    if (dist < maxDist && !mc.getSoundManager().isPlaying(whisper)) {
                        mc.getSoundManager().play(whisper);
                    }

                    if (mc.getSoundManager().isPlaying(whisper) && whisper.getVolume() <= 0) {
                        mc.getSoundManager().stop(whisper);
                    }

                    if (dist < maxDist && !faded) { //First fade
                        whisper.setVolume((modifier*k)/fadeVal);
                        k++;
                        if (k >= fadeVal) {
                            faded = true;
                            k = fadeVal;
                        }
                    } else if (k > 0 && dist < maxDist) { //Finish fade if it's not done
                        whisper.setVolume((modifier*k)/fadeVal);
                        k++;
                        if (k >= fadeVal) {
                            faded = true;
                            k = fadeVal;
                        }
                    } else if (dist > maxDist && faded) { //Last fade
                        whisper.setVolume((modifier*k)/fadeVal);
                        k--;
                        if (k <= 0) {
                            faded = false;
                            k = 0;
                        }
                    } else if (k < fadeVal && dist > maxDist) { //Finish more fade if it's not done
                        whisper.setVolume((modifier*k)/fadeVal);
                        k--;
                        if (k <= 0) {
                            faded = false;
                            k = 0;
                        }
                    }
                    
                    new CircleParticleObject(loc.add(0, 2, 0), particleList, null, 60, 0, 0.2, 0.5, new Vec3d(-20, -godEntity.getHeadYaw(), 0), i, true);
                    visorHandle(godEntity);
                    new ParticleAura(loc, new Vec3d(3, 3, 3), new Vec3d(0, 0, 0), particleList, 3000000).tick();;
                    if (godEntity.isOnGround() && rand.nextInt(5) == 0) new ParticleTrail(loc, -godEntity.getYaw(), particleList, true, 20); //Ground trail handle
                }
            }
        }

        i++;
        i = i % 360;
    }

    private static void visorHandle(PlayerEntity player) {  //Unique
        Vec3d loc = player.getEyePos();

        double pitch = player.getPitch();
        double yRot= -player.getYaw();
        double zRot = 0;
        double coefficient = 0.05;
        int randVal = 2500;
        double xOffset = 0;
        double yOffset = 0;

        for (double i = -0.25; i <= 0.25; i += 0.01) {
            for (double j = -0.25; j <= 0.25; j += 0.01) { 
                Rotator rotator = new Rotator();

                rotator.rotateYQuaternion((float)(Math.toRadians(yRot)));
                rotator.rotateXQuaternion((float)(Math.toRadians(pitch)));
                rotator.rotateZQuaternion((float)(Math.toRadians(zRot)));

                if (pitch >= 0) { //HELL
                    xOffset = 4*(Math.toRadians(pitch) * coefficient);
                    yOffset = -xOffset/2;
                } else { //FREE ME
                    xOffset = 3*(Math.toRadians(pitch) * coefficient);
                    yOffset = xOffset;
                }

                Vector3d rot = rotator.rotateVectorQuaternion(new Vector3d(i, j - yOffset, 0.3 + xOffset)); //Rotation moment

                double particleX = loc.getX() + rot.x;
                double particleY = loc.getY() + rot.y;
                double particleZ = loc.getZ() + rot.z;
    
                if (rand.nextInt(randVal) == (randVal-1)) { //Random Visor Logic
                    Particle particle = mc.particleManager.addParticle(new DustColorTransitionParticleEffect(new Color(1, 1, 1).getRGB(), new Color(192,192,192).getRGB(), 0.2f), particleX, particleY, particleZ, 0, 0, 0);
                    particle.setMaxAge(40);
                    particle.setVelocity(0,0,0);
                }
            }
        }
    }
}
