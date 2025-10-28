package com.codenamexpyz.utils;

import org.joml.Quaternionf;
import org.joml.Vector3d;
import org.joml.Vector3fc;

public class Rotator {
    private final Quaternionf quaternion;

    public Rotator() { //Create a new Quaternion object
        quaternion = new Quaternionf(); 
    }

    public void rotateXQuaternion(float angle) { //Rotate the Quaternion object on the X
        quaternion.rotateX(angle);
    }

    public void rotateYQuaternion(float angle) { //Rotate the Quaternion object on the Y
        quaternion.rotateY(angle);
    }

    public void rotateZQuaternion(float angle) { //Rotate the Quaternion object on the Z
        quaternion.rotateZ(angle);
    }

    public void rotateQuaterion(float angle, Vector3d axis) { //Other rotation method
        quaternion.rotateAxis(angle, (Vector3fc)axis);
    }

    public Vector3d rotateVectorQuaternion(Vector3d vector) { //The vector to reated via the provided X Y Z rotations
        Vector3d rotatedVector = new Vector3d(0, 0, 0);
        quaternion.transform(vector.x, vector.y, vector.z, rotatedVector);
        return new Vector3d(rotatedVector.x, rotatedVector.y, rotatedVector.z);
    }

    public static Vector3d YRotation(Vector3d loc, double theta) { //Euler angle for rotation for simple rotations.
        double X = loc.x * Math.cos(theta) - loc.z * Math.sin(theta);
        double Y = loc.y;
        double Z = loc.x * Math.sin(theta) + loc.z * Math.cos(theta); 
        return new Vector3d(X, Y, Z);
    }

    public static Vector3d XRotation(Vector3d loc, double theta) { //Euler angle rotation
        double X = loc.x;
        double Y = loc.y * Math.cos(theta) - loc.z * Math.sin(theta);
        double Z = loc.y * Math.sin(theta) + loc.z * Math.cos(theta); 
        return new Vector3d(X, Y, Z);
    }

    public static Vector3d ZRotation(Vector3d loc, double theta) { //Euler angle rotation
        double X = loc.y * Math.cos(theta) - loc.z * Math.sin(theta);
        double Y = loc.y * Math.sin(theta) + loc.z * Math.cos(theta);
        double Z = loc.z;
        return new Vector3d(X, Y, Z);
    }
}