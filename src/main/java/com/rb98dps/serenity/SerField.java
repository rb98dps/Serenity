package com.rb98dps.serenity;

import static com.rb98dps.serenity.SerializationReader.*;
import static com.rb98dps.serenity.SerializationWriter.writeBytes;

public class SerField extends SerBase {

    public static final byte CONTAINER_TYPE = ContainerType.FIELD;
    public Type type;
    public byte[] data;

    private SerField() {
    }

    public Object getValue() {
        switch (type) {
            case BYTE:
                return this.getByte();
            case SHORT:
                return this.getShort();
            case CHAR:
                return getChar();
            case INTEGER:
                return getInt();
            case LONG:
                return getLong();

            case FLOAT:
                return getFloat();
            case DOUBLE:
                return getDouble();
            case BOOLEAN:
                return getBoolean();
        }
        return null;
    }

    public byte getByte() {
        return readByte(data, 0);
    }

    public short getShort() {
        return readShort(data, 0);
    }

    public char getChar() {
        return readChar(data, 0);
    }

    public int getInt() {
        return readInt(data, 0);
    }

    public long getLong() {
        return readLong(data, 0);
    }

    public float getFloat() {
        return readFloat(data, 0);
    }

    public double getDouble() {
        return readDouble(data, 0);
    }

    public boolean getBoolean() {
        return readBoolean(data, 0);
    }

    public int getBytes(byte[] dest, int pointer) {
        pointer = serializeDefaultAttributes(dest, pointer, CONTAINER_TYPE, false);
        pointer = writeBytes(dest, pointer, type.getValue());
        pointer = writeBytes(dest, pointer, data);

        return pointer;
    }

    public int getSize() {
        return 1 + 2 + name.length + 1 + data.length;
    }

    public static SerField Byte(String name, byte value) {
        SerField field = createField(name, Type.BYTE);
        writeBytes(field.data, 0, value);
        return field;
    }

    public static SerField Short(String name, short value) {
        SerField field = createField(name, Type.SHORT);
        writeBytes(field.data, 0, value);
        return field;
    }

    public static SerField Char(String name, char value) {
        SerField field = createField(name, Type.CHAR);
        writeBytes(field.data, 0, value);
        return field;
    }

    public static SerField Integer(String name, int value) {
        SerField field = createField(name, Type.INTEGER);
        writeBytes(field.data, 0, value);
        return field;
    }

    public static SerField Long(String name, long value) {
        SerField field = createField(name, Type.LONG);
        writeBytes(field.data, 0, value);
        return field;
    }

    public static SerField Float(String name, float value) {
        SerField field = createField(name, Type.FLOAT);
        writeBytes(field.data, 0, value);
        return field;
    }


    public static SerField Double(String name, double value) {
        SerField field = createField(name, Type.DOUBLE);
        writeBytes(field.data, 0, value);
        return field;
    }


    public static SerField Boolean(String name, boolean value) {

        SerField field = createField(name, Type.BOOLEAN);
        writeBytes(field.data, 0, value);
        return field;
    }

    public static SerField createField(String name, Type type) {
        SerField field = new SerField();
        field.setName(name);
        field.type = type;
        field.data = new byte[Type.getDefaultSize(type)];
        return field;
    }

    public static SerField deserialize(byte[] data, int pointer) {

        SerField result = new SerField();
        pointer = deserializeDefaultAttributes(result, data, pointer, CONTAINER_TYPE,false);
        result.type = Type.getTypeFromValue(data[pointer++]);
        result.data = new byte[Type.getDefaultSize(result.type)];
        readBytes(data, pointer, result.data);

        return result;
    }
}
