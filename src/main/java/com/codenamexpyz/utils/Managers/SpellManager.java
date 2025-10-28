package com.codenamexpyz.utils.Managers;

import java.util.ArrayList;
import java.util.List;

import com.codenamexpyz.objects.DaelinShapes.Shape;
import com.codenamexpyz.objects.FlareSpellAssets.FlareSpellCircleReticule;


public class SpellManager {
    private static List<Shape> divShapes = new ArrayList<>();
    private static List<FlareSpellCircleReticule> reticules = new ArrayList<>();

    public static void tick() {
        divShapes.removeIf(Shape::tick);
        reticules.removeIf(FlareSpellCircleReticule::tick);
    }

    public static void addShape(Shape shape) {
        divShapes.add(shape);
    }

    public static void addreticule(FlareSpellCircleReticule reticule) {
        reticules.add(reticule);
    }

    public static List<FlareSpellCircleReticule> getFlareReticules() {
        return reticules;
    }
}
