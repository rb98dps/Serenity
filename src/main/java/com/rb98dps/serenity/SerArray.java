package com.rb98dps.serenity;

import java.util.Arrays;

import static com.rb98dps.serenity.SerializationReader.*;
import static com.rb98dps.serenity.SerializationWriter.writeBytes;

public class SerArray extends SerBase {
    public static final byte CONTAINER_TYPE = ContainerType.ARRAY;
    private Type type;
    private int count;
    private byte[] data;
    private short[] shortData;
    private char[] charData;
    private int[] intData;
    private long[] longData;
    private float[] floatData;
    private double[] doubleData;
    private boolean[] booleanData;

    private SerObject[] objectData;
    private SerString[] stringData;

    private SerArray() {
        size += 1 + 1 + 4;
    }

    private void updateSize() {
        size += getDataSize();
    }

    public int getBytes(byte[] dest, int pointer) {
        pointer = serializeDefaultAttributes(dest, pointer, CONTAINER_TYPE, true);
        pointer = writeBytes(dest, pointer, type.getValue());
        pointer = writeBytes(dest, pointer, count);

        switch (type) {
            case BYTE:
                pointer = writeBytes(dest, pointer, data);
                break;
            case SHORT:
                pointer = writeBytes(dest, pointer, shortData);
                break;
            case CHAR:
                pointer = writeBytes(dest, pointer, charData);
                break;
            case INTEGER:
                pointer = writeBytes(dest, pointer, intData);
                break;
            case LONG:
                pointer = writeBytes(dest, pointer, longData);
                break;
            case FLOAT:
                pointer = writeBytes(dest, pointer, floatData);
                break;
            case DOUBLE:
                pointer = writeBytes(dest, pointer, doubleData);
                break;
            case BOOLEAN:
                pointer = writeBytes(dest, pointer, booleanData);
                break;
            case SER_OBJECT:
                pointer = writeBytes(dest, pointer, objectData);
                break;
            case SER_STRING:
                pointer = writeBytes(dest, pointer, stringData);
                break;
        }

        return pointer;
    }

    public int getSize() {
        return size;
    }

    public Type getType() {
        return type;
    }

    public int getCount() {
        return count;
    }

    public SerObject[] getObjectData() {
        return objectData;
    }

    public int getDataSize() {

        switch (type) {

            case BYTE:
                return data.length * Type.getDefaultSize(Type.BYTE);
            case SHORT:
                return shortData.length * Type.getDefaultSize(Type.SHORT);
            case CHAR:
                return charData.length * Type.getDefaultSize(Type.CHAR);
            case INTEGER:
                return intData.length * Type.getDefaultSize(Type.INTEGER);
            case LONG:
                return longData.length * Type.getDefaultSize(Type.LONG);
            case FLOAT:
                return floatData.length * Type.getDefaultSize(Type.FLOAT);
            case DOUBLE:
                return doubleData.length * Type.getDefaultSize(Type.DOUBLE);
            case BOOLEAN:
                return booleanData.length * Type.getDefaultSize(Type.BOOLEAN);
            case SER_OBJECT: {
                int size = 0;
                for (SerObject objectDatum : objectData) {
                    size += objectDatum.getSize();
                }
                return size;
            }
            case SER_STRING: {
                int size = 0;
                for (SerString serString : stringData) {
                    size += serString.getSize();
                }
                return size;
            }

        }
        return 0;
    }

    public static SerArray Byte(String name, byte[] data) {
        SerArray array = createSerArray(name, data.length, Type.BYTE);
        array.data = data;
        array.updateSize();
        return array;
    }

    public static SerArray SerObject(String name, SerObject[] data) {
        SerArray array = createSerArray(name, data.length, Type.SER_OBJECT);
        array.objectData = data;
        array.updateSize();
        return array;
    }

    public static SerArray SerString(String name, SerString[] data) {
        SerArray array = createSerArray(name, data.length, Type.SER_STRING);
        array.stringData = data;
        array.updateSize();
        return array;
    }


    public static SerArray Short(String name, short[] data) {
        SerArray array = createSerArray(name, data.length, Type.SHORT);
        array.shortData = data;
        array.updateSize();
        return array;
    }


    public static SerArray Char(String name, char[] data) {
        SerArray array = createSerArray(name, data.length, Type.CHAR);
        array.charData = data;
        array.updateSize();
        return array;
    }


    public static SerArray Integer(String name, int[] data) {
        SerArray array = createSerArray(name, data.length, Type.INTEGER);
        array.intData = data;
        array.updateSize();
        return array;
    }


    public static SerArray Long(String name, long[] data) {
        SerArray array = createSerArray(name, data.length, Type.LONG);
        array.longData = data;
        array.updateSize();
        return array;
    }


    public static SerArray Float(String name, float[] data) {
        SerArray array = createSerArray(name, data.length, Type.FLOAT);
        array.floatData = data;
        array.updateSize();
        return array;
    }


    public static SerArray Double(String name, double[] data) {
        SerArray array = createSerArray(name, data.length, Type.DOUBLE);
        array.doubleData = data;
        array.updateSize();
        return array;
    }


    public static SerArray Boolean(String name, boolean[] data) {
        SerArray array = createSerArray(name, data.length, Type.BOOLEAN);
        array.booleanData = data;
        array.updateSize();
        return array;
    }

    private static SerArray createSerArray(String name, int length, Type type) {
        SerArray array = new SerArray();
        array.setName(name);
        array.type = type;
        array.count = length;
        return array;
    }

    public static SerArray deserialize(byte[] data, int pointer) {
        SerArray result = new SerArray();
        pointer = deserializeDefaultAttributes(result, data, pointer, CONTAINER_TYPE,true);
        result.type = Type.getTypeFromValue(data[pointer++]);
        result.count = readInt(data, pointer);
        pointer += Type.getDefaultSize(Type.INTEGER);

        switch (result.type) {
            case BYTE:
                result.data = new byte[result.count];
                readBytes(data, pointer, result.data);
                break;
            case SHORT:
                result.shortData = new short[result.count];
                readShorts(data, pointer, result.shortData);
                break;
            case CHAR:
                result.charData = new char[result.count];
                readChars(data, pointer, result.charData);
                break;
            case INTEGER:
                result.intData = new int[result.count];
                readInts(data, pointer, result.intData);
                break;
            case LONG:
                result.longData = new long[result.count];
                readLongs(data, pointer, result.longData);
                break;
            case FLOAT:
                result.floatData = new float[result.count];
                readFloats(data, pointer, result.floatData);
                break;
            case DOUBLE:
                result.doubleData = new double[result.count];
                readDoubles(data, pointer, result.doubleData);
                break;
            case BOOLEAN:
                result.booleanData = new boolean[result.count];
                readBooleans(data, pointer, result.booleanData);
                break;
            case SER_OBJECT:
                result.objectData = new SerObject[result.count];
                readObjects(data, pointer, result.objectData);
                break;
            case SER_STRING:
                result.stringData = new SerString[result.count];
                readStrings(data, pointer, result.stringData);
                break;
        }

        return result;
    }

    public byte[] getData() {
        return data;
    }

    public short[] getShortData() {
        return shortData;
    }

    public char[] getCharData() {
        return charData;
    }

    public int[] getIntData() {
        return intData;
    }

    public long[] getLongData() {
        return longData;
    }

    public float[] getFloatData() {
        return floatData;
    }

    public SerString[] getStringData() {
        return stringData;
    }

    public double[] getDoubleData() {
        return doubleData;
    }

    public boolean[] getBooleanData() {
        return booleanData;
    }

    public String getValues() {
        switch (type) {
            case SHORT:
                return Arrays.toString(this.getShortData());
            case CHAR:
                return Arrays.toString(getCharData());
            case INTEGER:
                return Arrays.toString(getIntData());
            case LONG:
                return Arrays.toString(getLongData());

            case FLOAT:
                return Arrays.toString(getFloatData());
            case DOUBLE:
                return Arrays.toString(getDoubleData());
            case BOOLEAN:
                return Arrays.toString(getBooleanData());
            case SER_OBJECT:
                return Arrays.toString(getObjectData());
            case SER_STRING:
                return Arrays.toString(getStringData());
        }

        return null;
    }
}
