package com.codenamexpyz.objects.DaelinShapes;

import static com.codenamexpyz.ArcadiaParticlesClient.MOD_ID;
import static com.codenamexpyz.ArcadiaParticlesClient.mc;

import java.awt.Color;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import com.codenamexpyz.utils.RenderUtils;

import net.minecraft.util.Identifier;

public class Square extends Shape {
    private float spinStrength;
    private double randVal = 0;

    public Square(Vector3f loc, Color color, float fadeSpeed, float spinStrength) {
        this.midpoint = loc;
        this.color = color;
        this.fadeSpeed = fadeSpeed;
        this.spinStrength = spinStrength;
        this.randVal = Math.random() * 100.0;
    }

    @Override
    protected Matrix4f viewMatrix() {
        Matrix4f viewMat = RenderUtils.cameraRelativeWorldpos(mc.gameRenderer.getCamera(), this.midpoint);
        viewMat.rotateY((float)(Math.log(spinStrength*count) + randVal));
        return viewMat;
    }

    @Override
    protected Identifier shapeTexture() {
        return Identifier.of(MOD_ID, "textures/shapes/square.png");
    }
}
