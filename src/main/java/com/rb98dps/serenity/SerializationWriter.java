package com.rb98dps.serenity;

import java.util.List;

public class SerializationWriter {

    public static int writeBytes(byte[] dest, int pointer, byte[] src) {
        assert (dest.length >= pointer + src.length);
        for (byte b : src) {
            dest[pointer++] = b;
        }
        return pointer;
    }

    public static int writeBytes(byte[] dest, int pointer, char[] src) {
        assert (dest.length >= pointer + src.length);
        for (char c : src) {
            pointer = writeBytes(dest, pointer, c);
        }
        return pointer;
    }

    public static int writeBytes(byte[] dest, int pointer, short[] src) {
        assert (dest.length >= pointer + src.length);
        for (short value : src) {
            pointer = writeBytes(dest, pointer, value);
        }
        return pointer;
    }

    public static int writeBytes(byte[] dest, int pointer, int[] src) {
        assert (dest.length >= pointer + src.length);
        for (int j : src) {
            pointer = writeBytes(dest, pointer, j);
        }
        return pointer;
    }

    public static int writeBytes(byte[] dest, int pointer, long[] src) {
        assert (dest.length >= pointer + src.length);
        for (long l : src) {
            pointer = writeBytes(dest, pointer, l);
        }
        return pointer;
    }

    public static int writeBytes(byte[] dest, int pointer, float[] src) {
        assert (dest.length >= pointer + src.length);
        for (float v : src) {
            pointer = writeBytes(dest, pointer, v);
        }
        return pointer;
    }

    public static int writeBytes(byte[] dest, int pointer, double[] src) {
        assert (dest.length >= pointer + src.length);
        for (double v : src) {
            pointer = writeBytes(dest, pointer, v);
        }
        return pointer;
    }

    public static int writeBytes(byte[] dest, int pointer, boolean[] src) {
        assert (dest.length >= pointer + src.length);
        for (boolean b : src) {
            pointer = writeBytes(dest, pointer, b);
        }
        return pointer;
    }

    public static int writeBytes(byte[] dest, int pointer, SerObject[] src) {
        assert (dest.length >= pointer + src.length);
        for (SerObject serObject : src) {
            pointer = writeBytes(dest, pointer, serObject);
        }
        return pointer;
    }

    public static int writeBytes(byte[] dest, int pointer, SerString[] src) {
        assert (dest.length >= pointer + src.length);
        for (SerString serString : src) {
            pointer = writeBytes(dest, pointer, serString);
        }
        return pointer;
    }

    private static int writeBytes(byte[] dest, int pointer, SerObject serObject) {
        pointer = serObject.getBytes(dest, pointer);
        return pointer;
    }

    private static int writeBytes(byte[] dest, int pointer, SerString serString) {
        pointer = serString.getBytes(dest, pointer);
        return pointer;
    }

    public static int writeBytes(byte[] dest, int pointer, byte value) {
        assert (dest.length >= pointer + Type.getDefaultSize(Type.BYTE));
        dest[pointer++] = value;
        return pointer;
    }

    public static int writeBytes(byte[] dest, int pointer, short value) {
        assert (dest.length >= pointer + Type.getDefaultSize(Type.SHORT));
        dest[pointer++] = (byte) ((value >> 8) & 0xff);
        dest[pointer++] = (byte) ((value) & 0xff);
        return pointer;
    }

    public static int writeBytes(byte[] dest, int pointer, char value) {
        assert (dest.length >= pointer + Type.getDefaultSize(Type.CHAR));
        dest[pointer++] = (byte) ((value >> 8) & 0xff);
        dest[pointer++] = (byte) ((value) & 0xff);
        return pointer;
    }

    public static int writeBytes(byte[] dest, int pointer, int value) {
        assert (dest.length >= pointer + Type.getDefaultSize(Type.INTEGER));
        dest[pointer++] = (byte) ((value >> 24) & 0xff);
        dest[pointer++] = (byte) ((value >> 16) & 0xff);
        dest[pointer++] = (byte) ((value >> 8) & 0xff);
        dest[pointer++] = (byte) ((value) & 0xff);
        return pointer;
    }

    public static int writeBytes(byte[] dest, int pointer, long value) {
        assert (dest.length >= pointer + Type.getDefaultSize(Type.LONG));

        dest[pointer++] = (byte) ((value >> 56) & 0xff);
        dest[pointer++] = (byte) ((value >> 48) & 0xff);
        dest[pointer++] = (byte) ((value >> 40) & 0xff);
        dest[pointer++] = (byte) ((value >> 32) & 0xff);
        dest[pointer++] = (byte) ((value >> 24) & 0xff);
        dest[pointer++] = (byte) ((value >> 16) & 0xff);
        dest[pointer++] = (byte) ((value >> 8) & 0xff);
        dest[pointer++] = (byte) ((value) & 0xff);
        return pointer;
    }

    public static int writeBytes(byte[] dest, int pointer, float value) {
        assert (dest.length >= pointer + Type.getDefaultSize(Type.FLOAT));
        int data = Float.floatToIntBits(value);
        return writeBytes(dest, pointer, data);
    }

    public static int writeBytes(byte[] dest, int pointer, double value) {
        assert (dest.length >= pointer + Type.getDefaultSize(Type.DOUBLE));
        long data = Double.doubleToLongBits(value);
        return writeBytes(dest, pointer, data);
    }

    public static int writeBytes(byte[] dest, int pointer, boolean value) {
        assert (dest.length >= pointer + Type.getDefaultSize(Type.BOOLEAN));
        dest[pointer++] = (byte) (value ? 1 : 0);
        return pointer;
    }


    public static int writeBytes(byte[] dest, int pointer, String string) {
        pointer = writeBytes(dest, pointer, (short) string.length());
        return writeBytes(dest, pointer, string.getBytes());
    }

    public static int writeBytesForAttributes(byte[] dest, int pointer, short count, List<? extends SerBase> objects) {
        pointer = writeBytes(dest, pointer, count);
        for (SerBase serBase : objects) {
            pointer = serBase.getBytes(dest, pointer);
        }
        return pointer;
    }

}
