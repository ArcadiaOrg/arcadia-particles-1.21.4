package com.codenamexpyz.utils.Rendering;

import org.ladysnake.satin.api.event.ShaderEffectRenderCallback;
import org.ladysnake.satin.api.experimental.ReadableDepthFramebuffer;
import org.ladysnake.satin.api.managed.ManagedShaderEffect;
import org.ladysnake.satin.api.managed.ShaderEffectManager;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;

public abstract class AbstractPostShader implements ShaderEffectRenderCallback, ClientTickEvents.EndTick{ //Based entirely on the orbital railgun mod
    protected final MinecraftClient client = MinecraftClient.getInstance();

    protected final ManagedShaderEffect SHADER = ShaderEffectManager.getInstance().manage(getIdentifier());

    protected abstract Identifier getIdentifier();
    protected abstract boolean shouldRender();

    protected int ticks = 0;

    @Override
    public void onEndTick(MinecraftClient minecraftClient) {
        if (shouldRender()) {
            ticks++;
        } else {
            ticks = 0;
        }
    }

    @Override
    public void renderShaderEffects(float tickDelta) {
        if (shouldRender()) {
            SHADER.render(tickDelta);
        }
    }
}
