package com.polling;

import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.UUID;

public class Uuid64 {

  private static final Encoder BASE64_URL_ENCODER = Base64.getUrlEncoder().withoutPadding();

  public static void main(String[] args) {
    // String uuidStr = UUID.randomUUID().toString();
    String uuidStr = "eb55c9cc-1fc1-43da-9adb-d9c66bb259ad";
    String uuid64 = uuidHexToUuid64(uuidStr);
    System.out.println(uuid64); //=> 61XJzB_BQ9qa29nGa7JZrQ
    System.out.println(uuid64.length()); //=> 22
    String uuidHex = uuid64ToUuidHex(uuid64);
    System.out.println(uuidHex); //=> eb55c9cc-1fc1-43da-9adb-d9c66bb259ad
  }

  public static String uuidHexToUuid64(String uuidStr) {
    UUID uuid = UUID.fromString(uuidStr);
    byte[] bytes = uuidToBytes(uuid);
    return BASE64_URL_ENCODER.encodeToString(bytes);
  }

  public static String uuid64ToUuidHex(String uuid64) {
    byte[] decoded = Base64.getUrlDecoder().decode(uuid64);
    UUID uuid = uuidFromBytes(decoded);
    return uuid.toString();
  }

  public static byte[] uuidToBytes(UUID uuid) {
    ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
    bb.putLong(uuid.getMostSignificantBits());
    bb.putLong(uuid.getLeastSignificantBits());
    return bb.array();
  }

  public static UUID uuidFromBytes(byte[] decoded) {
    ByteBuffer bb = ByteBuffer.wrap(decoded);
    long mostSigBits = bb.getLong();
    long leastSigBits = bb.getLong();
    return new UUID(mostSigBits, leastSigBits);
  }
}