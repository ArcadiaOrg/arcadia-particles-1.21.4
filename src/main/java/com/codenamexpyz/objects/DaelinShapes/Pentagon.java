package com.codenamexpyz.objects.DaelinShapes;

import static com.codenamexpyz.ArcadiaParticlesClient.mc;

import java.awt.Color;

import org.joml.Matrix4f;
import org.joml.Vector2d;
import org.joml.Vector3f;

import com.codenamexpyz.utils.RenderUtils;

public class Pentagon extends Shape {
    private float spinStrength;
    private double randVal = 0;

    public Pentagon(Vector3f loc, Color color, float fadeSpeed, float spinStrength) {
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

        double topOff = (Math.sqrt(5) / 61.5);

        double[][] offsetsSide = new double[4][2];
        
        offsetsSide[0][0] = topOff; offsetsSide[0][1] = 0; //Top Right
        offsetsSide[1][0] = -topOff; offsetsSide[1][1] = 0; //Top Left
        offsetsSide[2][0] = topOff; offsetsSide[2][1] = 0; //Back Left
        offsetsSide[3][0] = -topOff; offsetsSide[3][1] = 0; //Back Right

        for (int i = 0; i < 2; i++) {
            Matrix4f viewMatOne = new Matrix4f(viewMat);
            Matrix4f viewMatTwo = new Matrix4f(viewMat);
            Matrix4f viewMatThree = new Matrix4f(viewMat);
            Matrix4f viewMatFour = new Matrix4f(viewMat);
            Matrix4f viewMatFive = new Matrix4f(viewMat);

            double rot = (i == 1) ? 180 : 0;
            if (i == 1) offsetsSide = flipMatrix(offsetsSide);

            viewMatOne.translate(1f, 0, 0f);
            viewMatOne.rotateY((float)Math.toRadians(90));
            viewMatOne.rotateX((float)Math.toRadians(-90 - rot));
            drawSide(viewMatOne, new Vector2d(1, 0.05), offsetsSide);

            viewMatTwo.translate(0.049f, 0, -1.309f);
            viewMatTwo.rotateY((float)Math.toRadians(90 + 72));
            viewMatTwo.rotateX((float)Math.toRadians(-90 - rot));
            drawSide(viewMatTwo, new Vector2d(1, 0.05), offsetsSide);

            viewMatThree.translate(-1.48979f, 0, -0.809f);
            viewMatThree.rotateY((float)Math.toRadians(90 + 144));
            viewMatThree.rotateX((float)Math.toRadians(-90 - rot));
            drawSide(viewMatThree, new Vector2d(1, 0.05), offsetsSide);

            viewMatFour.translate(-1.48979f, 0, 0.809f);
            viewMatFour.rotateY((float)Math.toRadians(90 + 216));
            viewMatFour.rotateX((float)Math.toRadians(-90 - rot));
            drawSide(viewMatFour, new Vector2d(1, 0.05), offsetsSide);

            viewMatFive.translate(0.049f, 0, 1.309f);
            viewMatFive.rotateY((float)Math.toRadians(90 + 288));
            viewMatFive.rotateX((float)Math.toRadians(-90 - rot));
            drawSide(viewMatFive, new Vector2d(1, 0.05), offsetsSide);
        }
    }
}
