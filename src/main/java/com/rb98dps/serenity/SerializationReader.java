package com.rb98dps.serenity;

import java.nio.ByteBuffer;
import java.util.*;

public class SerializationReader {
    public static byte readByte(byte[] src, int pointer) {
        return src[pointer];
    }

    public static void readBytes(byte[] src, int pointer, byte[] dest) {
        System.arraycopy(src, pointer, dest, 0, dest.length);
    }

    public static void readShorts(byte[] src, int pointer, short[] dest) {
        for (int i = 0; i < dest.length; ++i) {
            dest[i] = readShort(src, pointer);
            pointer += Type.getDefaultSize(Type.SHORT);
        }
    }

    public static void readChars(byte[] src, int pointer, char[] dest) {
        for (int i = 0; i < dest.length; ++i) {
            dest[i] = readChar(src, pointer);
            pointer += Type.getDefaultSize(Type.CHAR);
        }
    }

    public static void readObjects(byte[] src, int pointer, SerObject[] objects) {
        for (int i = 0; i < objects.length; ++i) {
            objects[i] = SerObject.deserialize(src, pointer);
            pointer += objects[i].getSize();
        }
    }

    public static void readStrings(byte[] src, int pointer, SerString[] strings) {
        for (int i = 0; i < strings.length; ++i) {
            strings[i] = SerString.deserialize(src, pointer);
            pointer += strings[i].getSize();
        }
    }

    public static void readInts(byte[] src, int pointer, int[] dest) {
        for (int i = 0; i < dest.length; ++i) {
            dest[i] = readInt(src, pointer);
            pointer += Type.getDefaultSize(Type.INTEGER);
        }
    }

    public static void readLongs(byte[] src, int pointer, long[] dest) {
        for (int i = 0; i < dest.length; ++i) {
            dest[i] = readLong(src, pointer);
            pointer += Type.getDefaultSize(Type.LONG);
        }
    }

    public static void readFloats(byte[] src, int pointer, float[] dest) {
        for (int i = 0; i < dest.length; ++i) {
            dest[i] = readFloat(src, pointer);
            pointer += Type.getDefaultSize(Type.FLOAT);
        }
    }

    public static void readDoubles(byte[] src, int pointer, double[] dest) {
        for (int i = 0; i < dest.length; ++i) {
            dest[i] = readDouble(src, pointer);
            pointer += Type.getDefaultSize(Type.DOUBLE);
        }
    }

    public static void readBooleans(byte[] src, int pointer, boolean[] dest) {
        for (int i = 0; i < dest.length; ++i) {
            dest[i] = readBoolean(src, pointer);
            pointer += Type.getDefaultSize(Type.BOOLEAN);
        }
    }

    public static short readShort(byte[] src, int pointer) {
        return ByteBuffer.wrap(src, pointer, Type.getDefaultSize(Type.SHORT)).getShort();
    }


    public static char readChar(byte[] src, int pointer) {
        return ByteBuffer.wrap(src, pointer, Type.getDefaultSize(Type.CHAR)).getChar();
    }

    public static int readInt(byte[] src, int pointer) {
        return ByteBuffer.wrap(src, pointer, Type.getDefaultSize(Type.INTEGER)).getInt();
    }


    public static long readLong(byte[] src, int pointer) {
        return ByteBuffer.wrap(src, pointer, Type.getDefaultSize(Type.LONG)).getLong();
    }

    public static float readFloat(byte[] src, int pointer) {
        return Float.intBitsToFloat(readInt(src, pointer));
    }


    public static double readDouble(byte[] src, int pointer) {
        return Double.longBitsToDouble(readLong(src, pointer));
    }

    public static boolean readBoolean(byte[] src, int pointer) {
        assert (src[pointer] == 0 || src[pointer] == 1);
        return src[pointer] != 0;
    }

    public static String readString(byte[] src, int pointer, int length) {
        return new String(src, pointer, length);
    }

}
