package com.codenamexpyz.utils.Rendering;

import static com.codenamexpyz.ArcadiaParticlesClient.MOD_ID;
import static com.codenamexpyz.ArcadiaParticlesClient.config;
import static com.codenamexpyz.ArcadiaParticlesClient.mc;

import org.ladysnake.satin.api.managed.uniform.Uniform1f;

import net.minecraft.util.Identifier;

public class MonochromeShader extends AbstractPostShader {
    public static final Identifier MONOCHROME_SHADER = Identifier.of(MOD_ID, "monochrome");
    public static final MonochromeShader INSTANCE = new MonochromeShader();
    private static float opacity = 0f;

    private Uniform1f opacityUniform = SHADER.findUniform1f("opacity");

    @Override
    protected Identifier getIdentifier() {
        return MONOCHROME_SHADER;
    }

    @Override
    protected boolean shouldRender() {
        return mc.world != null && config.accessibilitySettings.toggleMonochrome && config.godSettings.toggleFury;
    }
    
    @Override
    public void renderShaderEffects(float tickDelta) {
        if (shouldRender()) {
            opacityUniform.set(opacity);
        }

        super.renderShaderEffects(tickDelta);
    }

    public static void setOpacity(float val) {
        opacity = val;
    }
}
