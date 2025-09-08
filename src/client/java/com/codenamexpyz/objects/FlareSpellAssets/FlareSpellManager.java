package com.codenamexpyz.objects.FlareSpellAssets;

import java.util.ArrayList;
import java.util.List;
    
public class FlareSpellManager {
    public static List<FlareSpellCircleReticule> reticules = new ArrayList<>();
    public static boolean reticulesAdded = false;

    public static void addreticule(FlareSpellCircleReticule reticule) {
        reticules.add(reticule);
    }
}
