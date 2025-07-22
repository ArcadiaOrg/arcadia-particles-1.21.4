package com.codenamexpyz;

import net.fabricmc.api.ModInitializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArcadiaParticles implements ModInitializer {
	public static final String MOD_ID = "arcadia-particles";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	private static final Identifier whispersID = Identifier.of(MOD_ID, "decay_whispers");
	public static SoundEvent whispersEvent = SoundEvent.of(whispersID);

	@Override
	public void onInitialize() {
		Registry.register(Registries.SOUND_EVENT, whispersID, whispersEvent);
	}
}