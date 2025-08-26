package com.codenamexpyz.networking;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;

public class packetManager {

    public packetManager() {
        PayloadTypeRegistry.playC2S().register(hasModPayload.id, hasModPayload.CODEC);
    }

    public static void hasMod(boolean T) {
        ClientPlayNetworking.send(new hasModPayload(T));
    }
}
