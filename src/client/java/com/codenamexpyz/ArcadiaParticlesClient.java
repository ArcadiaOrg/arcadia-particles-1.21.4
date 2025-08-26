package com.codenamexpyz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codenamexpyz.networking.packetManager;
import com.codenamexpyz.utils.Keybinds;
import com.codenamexpyz.utils.ParticleManager;
import com.codenamexpyz.utils.SplashManager;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.client.MinecraftClient;

public class ArcadiaParticlesClient implements ClientModInitializer {
	public static final String MOD_ID = "arcadia-particles";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static MinecraftClient mc;

	@Override
	public void onInitializeClient() {
		mc = MinecraftClient.getInstance();
		SplashManager.init(); //Splash handling

		new packetManager();
		Keybinds.register();
		
		WorldRenderEvents.END.register(context -> {	
			if (mc.world != null && !mc.isPaused()) { 
				ParticleManager.handleParticles(); //particles
			}
		});

		ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
			packetManager.hasMod(true);
		});

		ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
			packetManager.hasMod(false);
		});
	}
}