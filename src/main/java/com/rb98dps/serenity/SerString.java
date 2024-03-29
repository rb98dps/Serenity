package com.rb98dps.serenity;

import static com.rb98dps.serenity.SerializationReader.*;
import static com.rb98dps.serenity.SerializationWriter.writeBytes;

public class SerString extends SerBase {
    public static final byte CONTAINER_TYPE = ContainerType.STRING;
    private int count;
    private char[] characters;

    private SerString() {
        size += 1 + 4;
    }

    public String getString() {
        return new String(characters);
    }

    private void updateSize() {
        size += getDataSize();
    }

    public int getBytes(byte[] dest, int pointer) {

        pointer = serializeDefaultAttributes(dest, pointer, CONTAINER_TYPE, true);
        pointer = writeBytes(dest, pointer, count);
        pointer = writeBytes(dest, pointer, characters);

        return pointer;
    }

    public int getSize() {
        return size;
    }

    public int getDataSize() {
        return characters.length * Type.getDefaultSize(Type.CHAR);
    }

    public static SerString create(String name, String data) {
        SerString string = new SerString();
        string.setName(name);
        string.count = data.length();
        string.characters = data.toCharArray();
        string.updateSize();
        return string;
    }

    public static SerString deserialize(byte[] data, int pointer) {

        SerString result = new SerString();
        pointer = deserializeDefaultAttributes(result, data, pointer, CONTAINER_TYPE,true);
        result.count = readInt(data, pointer);
        pointer += Type.getDefaultSize(Type.INTEGER);
        result.characters = new char[result.count];
        readChars(data, pointer, result.characters);
        return result;
    }
}
