package com.codenamexpyz.objects.DaelinShapes;

import static com.codenamexpyz.ArcadiaParticlesClient.mc;

import java.awt.Color;

import org.joml.Matrix4f;
import org.joml.Vector2d;
import org.joml.Vector3f;

import com.codenamexpyz.utils.RenderUtils;

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
    protected void updateSides() {
        Matrix4f viewMat = RenderUtils.cameraRelativeWorldpos(mc.gameRenderer.getCamera(), this.midpoint);
        viewMat.rotateY((float)(Math.log(spinStrength*count) + randVal));

        double topOff = (Math.sqrt(4) / 40);

        double[][] offsetsSide = new double[4][2];
        double[][] offsetsTop = new double[4][2];
        
        offsetsSide[0][0] = topOff; offsetsSide[0][1] = 0; //Top Right
        offsetsSide[1][0] = -topOff; offsetsSide[1][1] = 0; //Top Left
        offsetsSide[2][0] = topOff; offsetsSide[2][1] = 0; //Back Left
        offsetsSide[3][0] = -topOff; offsetsSide[3][1] = 0; //Back Right

        offsetsTop[0][0] = -topOff; offsetsTop[0][1] = 0; //Top Right
        offsetsTop[1][0] = topOff; offsetsTop[1][1] = 0; //Top Left
        offsetsTop[2][0] = -topOff; offsetsTop[2][1] = 0; //Back Left
        offsetsTop[3][0] = topOff; offsetsTop[3][1] = 0; //Back Right

        for (int i = 0; i < 2; i++) { 
            Matrix4f viewMatLeft = new Matrix4f(viewMat);
            Matrix4f viewMatRight = new Matrix4f(viewMat);
            Matrix4f viewMatTop = new Matrix4f(viewMat);
            Matrix4f viewMatBottom = new Matrix4f(viewMat);

            double rot = (i == 1) ? 180 : 0; //Flip for other side
            if (i == 1) { //For the bottom, since attachement is with angles.
                offsetsSide = flipMatrix(offsetsSide);
                offsetsTop = flipMatrix(offsetsTop);
            }

            viewMatLeft.translate(1f, 0, 0f);
            viewMatLeft.rotateY((float)Math.toRadians(90));
            viewMatLeft.rotateX((float)Math.toRadians(-90 + rot));
            drawSide(viewMatLeft, new Vector2d(1, 0.05), offsetsSide);

            viewMatRight.translate(-1f, 0, 0f);
            viewMatRight.rotateY((float)Math.toRadians(90));
            viewMatRight.rotateX((float)Math.toRadians(-90 + rot));
            drawSide(viewMatRight, new Vector2d(1, 0.05), offsetsTop);

            viewMatTop.translate(0f, 0, 1f);
            viewMatTop.rotateX((float)Math.toRadians(-90 + rot));
            drawSide(viewMatTop, new Vector2d(1, 0.05), offsetsSide);

            viewMatBottom.translate(0f, 0, -1f);
            viewMatBottom.rotateX((float)Math.toRadians(-90 + rot));
            drawSide(viewMatBottom, new Vector2d(1, 0.05), offsetsTop);
        }
    }
}
