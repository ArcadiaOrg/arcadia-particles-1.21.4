package com.codenamexpyz.utils;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import net.minecraft.client.render.Camera;

public class RenderUtils {
    public static Matrix4f cameraRelativeWorldpos(Camera camera, Vector3f worldPosition) {
        Matrix4f world = new Matrix4f().translate(worldPosition.x, worldPosition.y, worldPosition.z);

        Vector3f cameraPos = new Vector3f((float) camera.getPos().x, (float) camera.getPos().y, (float)camera.getPos().z);

        Matrix4f view = new Matrix4f();

        view.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);

        return new Matrix4f(view).mul(world);
    }
}
