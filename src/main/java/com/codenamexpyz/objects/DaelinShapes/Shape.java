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
import net.minecraft.client.render.VertexFormats;

public abstract class Shape {
    protected Vector3f midpoint;
    protected Color color;
    protected float fadeSpeed;
    protected int count = 0;

    private List<BuiltBuffer> compiledShape = new ArrayList<>();
    private float fadeVal = 255;
    private float backPos = 0.0f;

    protected void drawSide(Matrix4f viewMatrix, Vector2d cornerSize, double[][] vertexOffset) { //[y][x]
        Tessellator tessellator = Tessellator.getInstance(); 
        BufferBuilder bufferBuilder = tessellator.begin(DrawMode.QUADS, VertexFormats.POSITION_COLOR);

        bufferBuilder.vertex(viewMatrix, (float)cornerSize.x + (float)vertexOffset[0][0], -(float)cornerSize.y + (float)vertexOffset[0][1], backPos).color(color.getRed()/255, color.getGreen()/255, color.getBlue()/255, fadeVal/255); //bl
        bufferBuilder.vertex(viewMatrix, (float)cornerSize.x + (float)vertexOffset[1][0], (float)cornerSize.y + (float)vertexOffset[1][1], backPos).color(color.getRed()/255, color.getGreen()/255, color.getBlue()/255, fadeVal/255); //tl
        bufferBuilder.vertex(viewMatrix, -(float)cornerSize.x + (float)vertexOffset[2][0], (float)cornerSize.y + (float)vertexOffset[2][1], backPos).color(color.getRed()/255, color.getGreen()/255, color.getBlue()/255, fadeVal/255); //tr
        bufferBuilder.vertex(viewMatrix, -(float)cornerSize.x + (float)vertexOffset[3][0], -(float)cornerSize.y + (float)vertexOffset[3][1], backPos).color(color.getRed()/255, color.getGreen()/255, color.getBlue()/255, fadeVal/255); //br

        BuiltBuffer buf = bufferBuilder.endNullable();
        if (buf != null) compiledShape.add(buf);
    }

    protected void drawSide(Matrix4f viewMatrix, Vector2d cornerSize, Vector2d vertexOffset) {
        Tessellator tessellator = Tessellator.getInstance(); 
        BufferBuilder bufferBuilder = tessellator.begin(DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        
        bufferBuilder.vertex(viewMatrix, (float)cornerSize.x + (float)vertexOffset.x, -(float)cornerSize.y + (float)vertexOffset.y, backPos).color(color.getRed()/255, color.getGreen()/255, color.getBlue()/255, fadeVal/255);
        bufferBuilder.vertex(viewMatrix, (float)cornerSize.x + (float)vertexOffset.x, (float)cornerSize.y + (float)vertexOffset.y, backPos).color(color.getRed()/255, color.getGreen()/255, color.getBlue()/255, fadeVal/255);
        bufferBuilder.vertex(viewMatrix, -(float)cornerSize.x + (float)vertexOffset.x, (float)cornerSize.y + (float)vertexOffset.y, backPos).color(color.getRed()/255, color.getGreen()/255, color.getBlue()/255, fadeVal/255);
        bufferBuilder.vertex(viewMatrix, -(float)cornerSize.x + (float)vertexOffset.x, -(float)cornerSize.y + (float)vertexOffset.y, backPos).color(color.getRed()/255, color.getGreen()/255, color.getBlue()/255, fadeVal/255);

        BuiltBuffer buf = bufferBuilder.endNullable();
        if (buf != null) compiledShape.add(buf);
    }

    protected abstract void updateSides();

    private boolean isDead() {
        fadeVal = Math.max(0, fadeVal - fadeSpeed);
        if (fadeVal < 0) return true;
        return false;
    }

    public boolean tick() {
        compiledShape.clear();
        updateSides(); //Called first to update empty list

        if (compiledShape.isEmpty()) return true; //Checks if the abstraction does anything.
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);
        RenderSystem.enableDepthTest();
        RenderSystem.depthFunc(GL11.GL_LEQUAL);
        RenderSystem.depthMask(true);

        for (BuiltBuffer buffer : compiledShape) {
            BufferRenderer.drawWithGlobalProgram(buffer);
        }
        
        RenderSystem.disableBlend();
        RenderSystem.depthFunc(GL11.GL_LEQUAL);

        count += 1;
        return isDead();
    }

    protected static double[][] flipMatrix(double[][] matrix) {
        double[][] flipped = new double[4][2];

        flipped[0][0] = -matrix[0][0]; flipped[0][1] = -matrix[0][1]; //Top Right
        flipped[1][0] = -matrix[1][0]; flipped[1][1] = -matrix[1][1]; //Top Left
        flipped[2][0] = -matrix[2][0]; flipped[2][1] = -matrix[2][1]; //Back Left
        flipped[3][0] = -matrix[3][0]; flipped[3][1] = -matrix[3][1]; //Back Right

        return flipped;
    }
}
