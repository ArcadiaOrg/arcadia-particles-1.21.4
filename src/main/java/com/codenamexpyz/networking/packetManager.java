package com.codenamexpyz.networking;

import static com.codenamexpyz.ArcadiaParticlesClient.config;

import java.awt.Color;
import java.util.List;
import java.util.Random;

import org.joml.Vector3f;

import com.codenamexpyz.networking.SpellPayloads.generalSpellPayloadS2C;
import com.codenamexpyz.networking.SpellPayloads.spellTickPayloadS2C;
import com.codenamexpyz.networking.hasModPayloads.hasModPayloadC2S;
import com.codenamexpyz.networking.hasModPayloads.hasModPayloadS2C;
import com.codenamexpyz.objects.StaticParticles;
import com.codenamexpyz.objects.DaelinShapes.Pentagon;
import com.codenamexpyz.objects.DaelinShapes.Square;
import com.codenamexpyz.objects.DaelinShapes.Triangle;
import com.codenamexpyz.objects.FlareSpellAssets.FlareSpellCircle;
import com.codenamexpyz.objects.FlareSpellAssets.FlareSpellCircleReticule;
import com.codenamexpyz.objects.SoundObjects.SoundMaker;
import com.codenamexpyz.utils.Managers.SpellManager;

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

        PayloadTypeRegistry.playS2C().register(generalSpellPayloadS2C.id, generalSpellPayloadS2C.CODEC);
        ClientPlayNetworking.registerGlobalReceiver(generalSpellPayloadS2C.id, (payload, context) -> {
            String spellName = new String(payload.spellName());
            switch (spellName) {
                case "Flare":
                    SpellManager.addreticule(
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
                            ), 1.1, 0.2, 100
                        )
                    );
                    SpellManager.addreticule(
                        new FlareSpellCircleReticule(
                            List.of(
                                new FlareSpellCircle<ParticleEffect>(
                                    new Vec3d(6, 6, 0), ParticleTypes.ENCHANT, new Vec3d(255, 165, 0), 30, 3, new Vec3d(0, 4, 0), new Vec3d(0, 0, -135)
                                ),
                                new FlareSpellCircle<ParticleEffect>(
                                    new Vec3d(6, 6, 0), ParticleTypes.ENCHANT, new Vec3d(207,181,59), 30, 3, new Vec3d(0, 4, 0), new Vec3d(0, 0, -135)
                                ),
                                new FlareSpellCircle<ParticleEffect>(
                                    new Vec3d(6, 6, 0), ParticleTypes.ENCHANT, new Vec3d(255, 165, 0), 30, 3, new Vec3d(0, 4, 0), new Vec3d(0, 0, -135)
                                )
                            ), 1.1, 0.2, 100
                        )
                    );
                    break;
                case "DoubleJump":
                    if (!config.playerPacketSettings.toggleDoubleRender) break;
                    Random random = new Random();

                    List<Color> colors = List.of(Color.CYAN, Color.WHITE);

                    int colorChoice = random.nextInt(2);

                    switch (random.nextInt(3)) { //So many switches
                        case 0:
                            SpellManager.addShape(new Square(new Vector3f(payload.playerPos()), colors.get(colorChoice), 1f, 3f));
                            break;
                        case 1:
                            SpellManager.addShape(new Triangle(new Vector3f(payload.playerPos()), colors.get(colorChoice), 1f, 3f));
                            break;
                        default:
                            SpellManager.addShape(new Pentagon(new Vector3f(payload.playerPos()), colors.get(colorChoice), 1f, 3f));
                            break;
                    }
                    StaticParticles.ParticleJet(new Vec3d(new Vector3f(payload.playerPos())), List.of(ParticleTypes.CLOUD), null, 50, 20);
                    SoundMaker.divJump(((float)config.accessibilitySettings.doubleJumpSound/100f), payload.playerPos());
                    break;
            
                default:
                    break;
            }
        });

        PayloadTypeRegistry.playS2C().register(spellTickPayloadS2C.id, spellTickPayloadS2C.CODEC);
        ClientPlayNetworking.registerGlobalReceiver(spellTickPayloadS2C.id, (payload, context) -> { 
            String spellName = new String(payload.spellName());
            switch (spellName) {
                case "Flare":
                    for (FlareSpellCircleReticule ret : SpellManager.getFlareReticules()) {
                        ret.updateData(new Vec3d(payload.raytracePos().x, payload.raytracePos().y, payload.raytracePos().z));
                    }
                    break;
            
                default:
                    break;
            }
        });
    }
}