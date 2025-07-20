package com.codenamexpyz.gods;

import static com.codenamexpyz.ArcadiaParticlesClient.mc;

import java.util.Arrays;
import java.util.List;

import org.joml.Vector2d;

import java.awt.Color;

import com.codenamexpyz.objects.BlockParticleAura;
import com.codenamexpyz.objects.CircleParticleObject;
import com.codenamexpyz.objects.ParticleAura;
import com.codenamexpyz.objects.ParticleTrail;
import com.codenamexpyz.utils.RenderManager;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Vec3d;

public class fury {
    private static final List<ParticleEffect> particleList = Arrays.asList(ParticleTypes.DRIPPING_DRIPSTONE_WATER);
    private static final List<ParticleEffect> haloParticleList = Arrays.asList(ParticleTypes.END_ROD);
    private static final List<Color> colorList = Arrays.asList(new Color(0/255, 255/255, 255/255), new Color(255/255, 255/255, 255/255)); 
    private static int i = 0; //Count
    private static double k = 0; //For the fading
    private static boolean close = false;

    public static void triggerParticles(List<PlayerEntity> viewerList, PlayerEntity godEntity) {
        Vec3d loc = godEntity.getPos();

        if (!mc.player.getName().equals(godEntity.getName())) {
            for (PlayerEntity player : viewerList) { //This needs to be made more efficient, I will do it some year.
                if (player.getName().getLiteralString().equals(godEntity.getName().getString())) {
                    double dist = new Vector2d(mc.player.getX(), mc.player.getZ()).distance(loc.x, loc.z);
                    double fadeVal = 180;

                    if (dist < 10 && !close) { //First fade
                        RenderManager.applyMonochromeShader(mc.getFramebuffer(), (float)(k/fadeVal));
                        k++;
                        if (k >= fadeVal) {
                            close = true;
                            k = fadeVal;
                        }
                    } else if (k > 0 && dist < 10) { //Finish fade if it's not done
                        RenderManager.applyMonochromeShader(mc.getFramebuffer(), (float)(k/fadeVal));
                        k++;
                        if (k >= fadeVal) {
                            close = true;
                            k = fadeVal;
                        }
                    } else if (dist > 10 && close) { //Last fade
                        RenderManager.applyMonochromeShader(mc.getFramebuffer(), (float)(k/fadeVal));
                        k--;
                        if (k <= 0) {
                            close = false;
                            k = 0;
                        }
                    } else if (k < fadeVal && dist > 10) { //Finish more fade if it's not done
                        RenderManager.applyMonochromeShader(mc.getFramebuffer(), (float)(k/fadeVal));
                        k--;
                        if (k <= 0) {
                            close = false;
                            k = 0;
                        }
                    } else if (dist < 10 && close) { //When close to the plauer
                        RenderManager.applyMonochromeShader(mc.getFramebuffer(), 1.0f);
                    }
                    
                    
                    i++;
                    i = i % 360;
                }
            }
        }
    }
}
