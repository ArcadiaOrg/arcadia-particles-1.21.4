package com.codenamexpyz.networking.SpellPayloads;

import org.joml.Vector3d;

import com.codenamexpyz.networking.codecHelper;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;

public record generalSpellPayloadS2C(char[] spellName, Vector3d playerPos, Vector3d raytracePos) implements codecHelper {
    public static final Id<generalSpellPayloadS2C> id = codecHelper.id("arcadiaplugin", "spells2c");
    public static final PacketCodec<? super RegistryByteBuf, generalSpellPayloadS2C> CODEC = 
        PacketCodec.tuple(
            codecHelper.CHARS, generalSpellPayloadS2C::spellName,
            codecHelper.VECTOR_3D, generalSpellPayloadS2C::playerPos,
            codecHelper.VECTOR_3D, generalSpellPayloadS2C::raytracePos,
            generalSpellPayloadS2C::new
        );

    @Override
    public Id<generalSpellPayloadS2C> getId() {
        return id;
    }
}

