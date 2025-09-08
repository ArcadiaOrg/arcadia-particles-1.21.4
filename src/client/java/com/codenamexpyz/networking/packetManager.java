package com.codenamexpyz.networking;

import java.util.List;

import com.codenamexpyz.networking.SpellPayloads.flarePayloadS2C;
import com.codenamexpyz.networking.hasModPayloads.hasModPayloadC2S;
import com.codenamexpyz.networking.hasModPayloads.hasModPayloadS2C;
import com.codenamexpyz.objects.FlareSpellAssets.FlareSpellCircle;
import com.codenamexpyz.objects.FlareSpellAssets.FlareSpellCircleReticule;
import com.codenamexpyz.objects.FlareSpellAssets.FlareSpellManager;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Vec3d;

public class packetManager {
    public packetManager() {
        /* -------- Client 2 Server channel registration --------- */
        PayloadTypeRegistry.playC2S().register(hasModPayloadC2S.id, hasModPayloadC2S.CODEC);

        /* -------- Server 2 Client channel registration --------- */
        PayloadTypeRegistry.playS2C().register(hasModPayloadS2C.id, hasModPayloadS2C.CODEC);
        ClientPlayNetworking.registerGlobalReceiver(hasModPayloadS2C.id, (payload, context) -> { //FUNNIEST CODE EVER. WHO NEEDS THE PAYLOAD.
            ClientPlayNetworking.send(new hasModPayloadC2S(false)); //Sends a packet, confirming the player has the mod. Wish it could be empty, but alas.
        });

        PayloadTypeRegistry.playS2C().register(flarePayloadS2C.id, flarePayloadS2C.CODEC);
        ClientPlayNetworking.registerGlobalReceiver(flarePayloadS2C.id, (payload, context) -> {
            if (FlareSpellManager.reticulesAdded == false) {
                FlareSpellManager.addreticule(
                    new FlareSpellCircleReticule(
                        List.of(
                            new FlareSpellCircle<ParticleEffect>(
                                new Vec3d(6, 6, 0), ParticleTypes.ENCHANT, new Vec3d(207,181,59), 30, 3, new Vec3d(0, 4, 0), new Vec3d(0, 0, 135)
                            ),
                            new FlareSpellCircle<ParticleEffect>(
                                new Vec3d(6, 6, 0), ParticleTypes.ENCHANT, new Vec3d(255, 165, 0), 30, 3, new Vec3d(0, 4, 0), new Vec3d(0, 0, 135)
                            ),
                            new FlareSpellCircle<ParticleEffect>(
                                new Vec3d(6, 6, 0), ParticleTypes.ENCHANT, new Vec3d(207,181,59), 30, 3, new Vec3d(0, 4, 0), new Vec3d(0, 0, 135)
                            )
                        ), 1.1, 0.2
                    )
                );
                FlareSpellManager.reticulesAdded = true;
            }

            if (FlareSpellManager.reticules != null) {
                for (FlareSpellCircleReticule ret : FlareSpellManager.reticules) {
                    ret.updateData(new Vec3d(payload.raytracePos().x, payload.raytracePos().y, payload.raytracePos().z));
                }
                FlareSpellManager.reticules.removeIf(FlareSpellCircleReticule::tick);
            }
        });
    }
}
