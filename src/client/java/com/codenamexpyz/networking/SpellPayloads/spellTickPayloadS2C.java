package com.codenamexpyz.networking.SpellPayloads;

import org.joml.Vector3d;

import com.codenamexpyz.networking.codecHelper;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

public record spellTickPayloadS2C(String spellName, Vector3d playerPos, Vector3d raytracePos) implements codecHelper {
    public static final Id<spellTickPayloadS2C> id = codecHelper.id("arcadiaplugin", "spellticks2c");
    public static final PacketCodec<? super RegistryByteBuf, spellTickPayloadS2C> CODEC = 
        PacketCodec.tuple(
            PacketCodecs.STRING, spellTickPayloadS2C::spellName,
            codecHelper.VECTOR_3D, spellTickPayloadS2C::playerPos,
            codecHelper.VECTOR_3D, spellTickPayloadS2C::raytracePos,
            spellTickPayloadS2C::new
        );

    @Override
    public Id<spellTickPayloadS2C> getId() {
        return id;
    }
}

