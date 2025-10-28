package com.codenamexpyz.objects.DaelinShapes;

import static com.codenamexpyz.ArcadiaParticlesClient.mc;

import java.awt.Color;

import org.joml.Matrix4f;
import org.joml.Vector2d;
import org.joml.Vector3f;

import com.codenamexpyz.utils.RenderUtils;

public class Triangle extends Shape {
    private float spinStrength;
    private double randVal = 0;

    public Triangle(Vector3f loc, Color color, float fadeSpeed, float spinStrength) {
        this.midpoint = loc;
        this.color = color;
        this.fadeSpeed = fadeSpeed;
        this.spinStrength = spinStrength;
        this.randVal = Math.random() * 100.0;
    }

    @Override
    protected void updateSides() {
        Matrix4f viewMat = RenderUtils.cameraRelativeWorldpos(mc.gameRenderer.getCamera(), this.midpoint);
        viewMat.rotateY((float)(Math.log(spinStrength*count) + randVal));

        double topOff = (Math.sqrt(3) / 20);

        double[][] offsets = new double[4][2];

        offsets[0][0] = -topOff; offsets[0][1] = 0; //Top Right
        offsets[1][0] =  topOff; offsets[1][1] = 0; //Top Left
        offsets[2][0] = -topOff; offsets[2][1] = 0; //Back Left
        offsets[3][0] = topOff; offsets[3][1] = 0; //Back Right

        for (int i = 0; i < 2; i ++) {
            Matrix4f viewMatLeft = new Matrix4f(viewMat);
            Matrix4f viewMatRight = new Matrix4f(viewMat);
            Matrix4f viewMatBottom = new Matrix4f(viewMat);

            double rot = (i == 1) ? 180 : 0;
            if (i == 1) offsets = flipMatrix(offsets);

            viewMatLeft.translate(-0.5f, 0, 0f);
            viewMatLeft.rotateY((float)Math.toRadians(60));
            viewMatLeft.rotateX((float)Math.toRadians(-90 + rot));
            drawSide(viewMatLeft, new Vector2d(1, 0.05), offsets); //Left

            viewMatRight.translate(0.5f, 0, 0f);
            viewMatRight.rotateY((float)Math.toRadians(120));
            viewMatRight.rotateX((float)Math.toRadians(-90 + rot));
            drawSide(viewMatRight, new Vector2d(1, 0.05), flipMatrix(offsets)); //Right

            viewMatBottom.translate(0, 0, (float)topOff*10);
            viewMatBottom.rotateX((float)Math.toRadians(-90 + rot));
            drawSide(viewMatBottom, new Vector2d(1, 0.05), flipMatrix(offsets)); //Bottom
        }
    }
}
