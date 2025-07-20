package com.codenamexpyz.utils;

import static com.codenamexpyz.ArcadiaParticlesClient.MOD_ID;
import static com.codenamexpyz.ArcadiaParticlesClient.mc;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gl.Defines;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.ShaderProgramKey;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;

public class RenderManager {
	private static ShaderProgramKey monochromeKey = new ShaderProgramKey(Identifier.of(MOD_ID, "post/monochrome"), VertexFormats.POSITION_TEXTURE, Defines.EMPTY);

    public static void applyMonochromeShader(Framebuffer inputFramebuffer, float Opacity) {
		RenderSystem.assertOnRenderThread();
		GlStateManager._colorMask(true, true, true, false);
		GlStateManager._disableDepthTest();
		GlStateManager._depthMask(false);
		GlStateManager._viewport(0, 0, mc.getWindow().getWidth(), mc.getWindow().getHeight());

		RenderSystem.setShaderTexture(0, inputFramebuffer.getColorAttachment());
		RenderSystem.setShader(monochromeKey);
		if (RenderSystem.getShader().getUniform("floated") != null) RenderSystem.getShader().getUniform("floated").set(Opacity);

		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder buffer = tessellator.begin(VertexFormat.DrawMode.QUADS, VertexFormats.BLIT_SCREEN);
		
		buffer.vertex(-1.0F, -1.0F, 0.0F).texture(0, 0)
			  .vertex(1.0F, -1.0F, 0.0F).texture(1, 0)
			  .vertex(1.0F, 1.0F, 0.0F).texture(1, 1)
			  .vertex(-1.0F, 1.0F, 0.0F).texture(0, 1);
				
		BufferRenderer.drawWithGlobalProgram(buffer.end());
		GlStateManager._depthMask(true);
		GlStateManager._colorMask(true, true, true, true);
	}
}
