package com.codenamexpyz.objects.SoundObjects;

import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;

public class SoundTickable extends MovingSoundInstance {
    private final PlayerEntity victim;

    public SoundTickable(PlayerEntity victim, SoundEvent soundEvent, SoundCategory soundCategory) {
        super(soundEvent, soundCategory, SoundInstance.createRandom());
		this.victim = victim;
		this.volume = 0.1f;
		this.pitch = 1.0f;
		this.repeat = true;
        this.updatePosition();
    }

    @Override
    public void tick() {
        if (this.victim == null || this.victim.isRemoved() || this.victim.isDead()) {
            this.setDone();
            return;
        }

        this.updatePosition();
    }

    @Override
	public boolean shouldAlwaysPlay() {
		return true;
	}

    public void setVolume(double volume) {
        this.volume = (float)volume;
    }

    private void updatePosition() {
        this.x = this.victim.getX();
        this.y = this.victim.getX();
        this.z = this.victim.getX();
    }
}
