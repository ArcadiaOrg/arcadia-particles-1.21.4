package com.codenamexpyz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codenamexpyz.config.ArcadiaParticlesConfig;
import com.codenamexpyz.networking.packetManager;
import com.codenamexpyz.utils.Keybinds;
import com.codenamexpyz.utils.Managers.PlayerEffectManager;
import com.codenamexpyz.utils.Managers.SpellManager;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;

public class ArcadiaParticlesClient implements ClientModInitializer {
	public static final String MOD_ID = "arcadia-particles";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static MinecraftClient mc;
	public static ArcadiaParticlesConfig config;

	@Override
	public void onInitializeClient() {
		mc = MinecraftClient.getInstance();

		AutoConfig.register(ArcadiaParticlesConfig.class, PartitioningSerializer.wrap(JanksonConfigSerializer::new));
        config = AutoConfig.getConfigHolder(ArcadiaParticlesConfig.class).getConfig();

		new packetManager();
		Keybinds.register();
		
		WorldRenderEvents.LAST.register(context -> {	
			if (mc.world != null && !mc.isPaused()) { //Particle case
				PlayerEffectManager.handleParticles(); //particles
				SpellManager.tick();
			}
		});
	}
}