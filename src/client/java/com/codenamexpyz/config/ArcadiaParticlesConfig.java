package com.codenamexpyz.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;

@Config(name = "arcadia-particles")
public class ArcadiaParticlesConfig extends PartitioningSerializer.GlobalData {
    
    @ConfigEntry.Category("God Settings")
    @ConfigEntry.Gui.TransitiveObject
    public GodSettings godSettings = new GodSettings();

    @ConfigEntry.Category("Accessibility Settings")
    @ConfigEntry.Gui.TransitiveObject
    public AccessibilitySettings accessibilitySettings = new AccessibilitySettings();

    @ConfigEntry.Category("Player and Packet Settings")
    @ConfigEntry.Gui.TransitiveObject
    public PlayerPacketSettings playerPacketSettings = new PlayerPacketSettings();

    @Config(name = "GodSettings")
    public static class GodSettings implements ConfigData { 
        @ConfigEntry.Gui.Tooltip(count = 1)
        public boolean toggleBygones = true;

        @ConfigEntry.Gui.Tooltip(count = 1)
        public boolean toggleDecay = true;

        @ConfigEntry.Gui.Tooltip(count = 1)
        public boolean toggleEarth = true;

        @ConfigEntry.Gui.Tooltip(count = 1)
        public boolean toggleFury = true;

        @ConfigEntry.Gui.Tooltip(count = 1)
        public boolean toggleSea = true;

        @ConfigEntry.Gui.Tooltip(count = 1)
        public boolean toggleSky = true;
    }

    @Config(name = "AccessibilitySettings")
    public static class AccessibilitySettings implements ConfigData { 
        @ConfigEntry.Gui.Tooltip(count = 1)
        @ConfigEntry.BoundedDiscrete(max = 100)
        public int furyHeartbeat = 100;

        @ConfigEntry.Gui.Tooltip(count = 1)
        @ConfigEntry.BoundedDiscrete(max = 100)
        public int bygonesWhispers = 100;

        @ConfigEntry.Gui.Tooltip(count = 1)
        public boolean toggleMonochrome = true;
    }

    @Config(name = "PlayerPacketSettings")
    public static class PlayerPacketSettings implements ConfigData { 
        @ConfigEntry.Gui.Tooltip(count = 1)
        public boolean toggleArtifice = true;
    }
}