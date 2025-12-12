package com.codenamexpyz.objects.DaelinShapes;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.joml.Matrix4f;
import org.joml.Vector2d;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.BuiltBuffer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.util.Identifier;
import net.minecraft.client.render.VertexFormats;

public abstract class Shape {
    protected Vector3f midpoint;
    protected Color color;
    protected float fadeSpeed;
    protected int count = 0;

    private float fadeVal = 255;
    private float backPos = 0.0f;

    private List<BuiltBuffer> compiledShape = new ArrayList<>();

    private void drawSide(Matrix4f viewMatrix, Vector2d cornerSize) {
        Tessellator tessellator = Tessellator.getInstance(); 
        BufferBuilder bufferBuilder = tessellator.begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
        
        bufferBuilder.vertex(viewMatrix, (float)cornerSize.x, -(float)cornerSize.y, backPos).texture(0, 1).color(color.getRed()/255, color.getGreen()/255, color.getBlue()/255, fadeVal/255);
        bufferBuilder.vertex(viewMatrix, (float)cornerSize.x, (float)cornerSize.y, backPos).texture(0, 0).color(color.getRed()/255, color.getGreen()/255, color.getBlue()/255, fadeVal/255);
        bufferBuilder.vertex(viewMatrix, -(float)cornerSize.x, (float)cornerSize.y, backPos).texture(1, 0).color(color.getRed()/255, color.getGreen()/255, color.getBlue()/255, fadeVal/255);
        bufferBuilder.vertex(viewMatrix, -(float)cornerSize.x, -(float)cornerSize.y, backPos).texture(1, 1).color(color.getRed()/255, color.getGreen()/255, color.getBlue()/255, fadeVal/255);

        BuiltBuffer buf = bufferBuilder.endNullable();
        if (buf != null) compiledShape.add(buf);
    }

    private void drawShape(Matrix4f viewMatrix, Vector2d cornerSize) { //Flips it for both sizes
        drawSide(viewMatrix.rotateX((float)Math.toRadians(90)), cornerSize);
        drawSide(new Matrix4f(viewMatrix).rotateX((float)Math.toRadians(180)), cornerSize);
    }

    private boolean isDead() {
        fadeVal = Math.max(0, fadeVal - fadeSpeed);
        if (fadeVal < 0) return true;
        return false;
    }

    protected abstract Matrix4f viewMatrix();
    protected abstract Identifier shapeTexture();

    public boolean tick() {
        compiledShape.clear();
        drawShape(viewMatrix(), new Vector2d(1, 1));

        if (compiledShape.isEmpty()) return true; //Checks if the abstraction does anything.
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(ShaderProgramKeys.POSITION_TEX_COLOR);
        RenderSystem.setShaderTexture(0, shapeTexture());
        RenderSystem.enableDepthTest();
        RenderSystem.depthFunc(GL11.GL_LEQUAL);
        RenderSystem.depthMask(true);

        for (BuiltBuffer buffer : compiledShape) {
            BufferRenderer.drawWithGlobalProgram(buffer);
        }
        
        RenderSystem.disableBlend();
        RenderSystem.depthFunc(GL11.GL_LEQUAL);

        count++;
        return isDead();
    }
}
