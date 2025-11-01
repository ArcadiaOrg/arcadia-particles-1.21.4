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

   public PacketCodec<ByteBuf, char[]> CHARS = PacketCodec.of(
      (chars, buf) -> { //Encodes the list of characters 
         for (char c : chars) {
            buf.writeChar(c);
         }
      },
      buf -> { //Takes a preceding length int, and converts the following character list into readable characters
         int length = buf.readInt();
         char[] chars = new char[length]; 

         for (int i = 0; i < length; i++){
            chars[i] = buf.readChar();
         }

         return chars;
      }
   );

   @SuppressWarnings({ "rawtypes", "unchecked" })
   static <T extends CustomPayload> Id<T> id(String namespace, String path) {
      return new Id(Identifier.of(namespace, path));
   }
}
