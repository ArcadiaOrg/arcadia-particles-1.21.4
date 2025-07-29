package com.codenamexpyz.gods;

import static com.codenamexpyz.ArcadiaParticlesClient.mc;

import java.util.List;

import org.joml.Vector2d;

import java.awt.Color;

import com.codenamexpyz.objects.ParticleBurn;
import com.codenamexpyz.utils.RenderManager;
import com.codenamexpyz.utils.SoundMaker;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.DustColorTransitionParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.math.Vec3d;

public class fury {
    //Unique heartbeat variables
    private static double i = 0; //Heartbeat count
    private static double k = 0; //For the fading
    private static boolean close = false;

    //Objects used. Only needs to be declaired once for ticking sake.
    private static ParticleBurn<? extends ParticleEffect> cloak;

    public static void triggerParticles(List<PlayerEntity> viewerList, PlayerEntity godEntity) {
        Vec3d loc = godEntity.getPos();

        cloak = new ParticleBurn<DustColorTransitionParticleEffect>(godEntity, new DustColorTransitionParticleEffect(new Color(1, 1, 1).getRGB(), new Color(192,192,192).getRGB(), 0.5f), 100);

        if (!mc.player.getName().equals(godEntity.getName())) {
            for (PlayerEntity player : viewerList) { //This needs to be made more efficient, I will do it some year.
                if (player.getName().getLiteralString().equals(godEntity.getName().getString())) {
                    double dist = new Vector2d(mc.player.getX(), mc.player.getZ()).distance(loc.x, loc.z);
                    double maxDist = 40;
                    double fadeVal = 180;

                    if (dist < maxDist && !close) { //First fade
                        RenderManager.applyMonochromeShader(mc.getFramebuffer(), (float)(k/fadeVal));
                        k++;
                        if (k >= fadeVal) {
                            close = true;
                            k = fadeVal;
                        }
                    } else if (k > 0 && dist < maxDist) { //Finish fade if it's not done
                        RenderManager.applyMonochromeShader(mc.getFramebuffer(), (float)(k/fadeVal));
                        k++;
                        if (k >= fadeVal) {
                            close = true;
                            k = fadeVal;
                        }
                    } else if (dist > maxDist && close) { //Last fade
                        RenderManager.applyMonochromeShader(mc.getFramebuffer(), (float)(k/fadeVal));
                        k--;
                        if (k <= 0) {
                            close = false;
                            k = 0;
                        }
                    } else if (k < fadeVal && dist > maxDist) { //Finish more fade if it's not done
                        RenderManager.applyMonochromeShader(mc.getFramebuffer(), (float)(k/fadeVal));
                        k--;
                        if (k <= 0) {
                            close = false;
                            k = 0;
                        }
                    } else if (dist < maxDist && close) { //When close to the player
                        RenderManager.applyMonochromeShader(mc.getFramebuffer(), 1.0f);
                    }

                    if (mc.player.getHealth() <= 2.0) { //Heartbeat effects
                        SoundMaker.heatbeat((float)(k/fadeVal), i, 400);
                    } else SoundMaker.heatbeat((float)(k/fadeVal), i, 200);
                    
                    cloak.tick();
                    
                    i++;
                    i = i % 1200;
                }
            }
        }
    }
}
