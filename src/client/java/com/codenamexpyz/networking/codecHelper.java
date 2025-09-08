package com.codenamexpyz.networking;

import org.joml.Vector3d;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public interface codecHelper extends CustomPayload {
   public PacketCodec<ByteBuf, Vector3d> VECTOR_3D = PacketCodec.of(
      (vec, buf) -> { //encodes the vector, and writes it to the buffer
         buf.writeDouble(vec.x());
         buf.writeDouble(vec.y());
         buf.writeDouble(vec.z());
      },
      buf -> new Vector3d( //Decodes the bytes, and returns it to a Vector3d
         buf.readDouble(),
         buf.readDouble(),
         buf.readDouble()
      )
   );

   @SuppressWarnings({ "rawtypes", "unchecked" })
   static <T extends CustomPayload> Id<T> id(String namespace, String path) {
      return new Id(Identifier.of(namespace, path));
   }
}
