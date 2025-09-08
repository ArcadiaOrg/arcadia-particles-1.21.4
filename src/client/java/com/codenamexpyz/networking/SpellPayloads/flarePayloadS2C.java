package com.codenamexpyz.networking.SpellPayloads;

import org.joml.Vector3d;

import com.codenamexpyz.networking.codecHelper;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;

public record flarePayloadS2C(Vector3d playerPos, Vector3d raytracePos) implements codecHelper {
    public static final Id<flarePayloadS2C> id = codecHelper.id("arcadiaplugin", "flarespells2c");
    public static final PacketCodec<? super RegistryByteBuf, flarePayloadS2C> CODEC = 
        PacketCodec.tuple(
            codecHelper.VECTOR_3D, flarePayloadS2C::playerPos,
            codecHelper.VECTOR_3D, flarePayloadS2C::raytracePos,
            flarePayloadS2C::new
        );

    @Override
    public Id<flarePayloadS2C> getId() {
        return id;
    }
}
