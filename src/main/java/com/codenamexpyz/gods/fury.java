package com.codenamexpyz.gods;

import static com.codenamexpyz.ArcadiaParticlesClient.config;
import static com.codenamexpyz.ArcadiaParticlesClient.mc;

import org.joml.Vector2d;

import java.awt.Color;

import com.codenamexpyz.objects.ParticleBurn;
import com.codenamexpyz.objects.SoundObjects.SoundMaker;
import com.codenamexpyz.utils.Rendering.MonochromeShader;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.DustColorTransitionParticleEffect;
import net.minecraft.particle.ParticleEffect;

public class fury extends GodBase{
    //Unique heartbeat variables
    private double i = 0; //Heartbeat count
    private double k = 0; //For the fading
    private boolean close = false;
    private int modifier = 4; //Increases or decreases fade speed

    //Objects used. Only needs to be declaired once for ticking sake.
    private ParticleBurn<? extends ParticleEffect> cloak = new ParticleBurn<DustColorTransitionParticleEffect>(godEntity, new DustColorTransitionParticleEffect(new Color(1, 1, 1).getRGB(), new Color(192,192,192).getRGB(), 0.5f), 10);

    @Override
    protected void godEffect(PlayerEntity player) {
        double dist = new Vector2d(mc.player.getX(), mc.player.getZ()).distance(loc.x, loc.z);
        double maxDist = 40;
        double fadeVal = 180;

        if (dist < maxDist && !close) { //First fade
            k += modifier;
            if (k >= fadeVal) {
                close = true;
                k = fadeVal;
            }
        } else if (k > 0 && dist < maxDist) { //Finish fade if it's not done
            k += modifier;
            if (k >= fadeVal) {
                close = true;
                k = fadeVal;
            }
        } else if (dist > maxDist && close) { //Last fade
            k -= modifier;
            if (k <= 0) {
                close = false;
                k = 0;
            }
        } else if (k < fadeVal && dist > maxDist) { //Finish more fade if it's not done
            k -= modifier;
            if (k <= 0) {
                close = false;
                k = 0;
            }
        }

        MonochromeShader.setOpacity((float)(k/fadeVal)) ; //Maybe will work!

        if (mc.player.getHealth() <= 2.0) { //Heartbeat effects
            SoundMaker.heatbeat((float)((k/fadeVal) * ((double)config.accessibilitySettings.furyHeartbeat/100)), i, 80);
        } else SoundMaker.heatbeat((float)((k/fadeVal) * ((double)config.accessibilitySettings.furyHeartbeat/100)), i, 40);
        
        cloak.tick();
        
        i++;
        i = i % 120;
    }
}
