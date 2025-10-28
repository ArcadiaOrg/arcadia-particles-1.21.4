package com.codenamexpyz.networking.hasModPayloads;

import com.codenamexpyz.networking.codecHelper;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

public record hasModPayloadS2C(Boolean hasMod) implements codecHelper {
    public static final Id<hasModPayloadS2C> id = codecHelper.id("arcadiaplugin", "hasmodrequests2c");
    public static final PacketCodec<? super RegistryByteBuf, hasModPayloadS2C> CODEC = 
        PacketCodec.tuple(
            PacketCodecs.BOOLEAN, hasModPayloadS2C::hasMod,
            hasModPayloadS2C::new
        );

    @Override
    public Id<hasModPayloadS2C> getId() {
        return id;
    }
}
