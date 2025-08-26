package com.codenamexpyz.networking;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record hasModPayload(Boolean hasMod) implements CustomPayload {
    public static final Identifier ID = Identifier.of("arcadiaparticles", "hasmodc2s"); //Identifier
    public static final Id<hasModPayload> id = new Id<>(ID); //Custom payload packet ID

    public static final PacketCodec<? super RegistryByteBuf, hasModPayload> CODEC = 
        PacketCodec.tuple(
            PacketCodecs.BOOLEAN, hasModPayload::hasMod,
            hasModPayload::new
        );

    @Override
    public Id<? extends CustomPayload> getId() {
        return id;
    }
    
}
