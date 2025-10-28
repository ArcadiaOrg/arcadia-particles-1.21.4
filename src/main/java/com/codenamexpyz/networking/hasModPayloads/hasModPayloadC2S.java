package com.codenamexpyz.networking.hasModPayloads;

import com.codenamexpyz.networking.codecHelper;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

public record hasModPayloadC2S(Boolean hasMod) implements codecHelper {
    public static final Id<hasModPayloadC2S> id = codecHelper.id("arcadiaparticles", "hasmodc2s");
    public static final PacketCodec<? super RegistryByteBuf, hasModPayloadC2S> CODEC = 
        PacketCodec.tuple(
            PacketCodecs.BOOLEAN, hasModPayloadC2S::hasMod,
            hasModPayloadC2S::new
        );

    @Override
    public Id<hasModPayloadC2S> getId() {
        return id;
    }
}
