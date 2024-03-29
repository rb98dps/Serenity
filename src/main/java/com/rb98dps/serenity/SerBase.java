package com.rb98dps.serenity;

import static com.rb98dps.serenity.SerializationReader.*;
import static com.rb98dps.serenity.SerializationWriter.writeBytes;

public abstract class SerBase {
    protected short nameLength;
    protected byte[] name;
    protected int size = 2 + 4;

    public String getName() {
        return new String(name, 0, nameLength);
    }

    public void setName(String name) {
        assert (name.length() < Short.MAX_VALUE);

        if (this.name != null) {
            size -= this.name.length;
        }
        nameLength = (short) name.length();
        this.name = name.getBytes();
        size += nameLength;
    }

    protected static int deserializeDefaultAttributes(SerBase result, byte[] data, int pointer, byte type, boolean includeSize) {
        byte containerType = data[pointer++];
        assert (containerType == type);
        result.nameLength = readShort(data, pointer);
        pointer += Type.getDefaultSize(Type.SHORT);
        result.name = readString(data, pointer, result.nameLength).getBytes();
        pointer += result.nameLength;
        if (includeSize) {
            result.size = readInt(data, pointer);
            pointer += Type.getDefaultSize(Type.INTEGER);
        }
        return pointer;
    }

    protected int serializeDefaultAttributes(byte[] dest, int pointer, byte containerType, boolean includeSize) {
        pointer = writeBytes(dest, pointer, containerType);
        pointer = writeBytes(dest, pointer, nameLength);
        pointer = writeBytes(dest, pointer, name);
        if (includeSize) {
            pointer = writeBytes(dest, pointer, size);
        }
        return pointer;
    }

    public abstract int getSize();

    public abstract int getBytes(byte[] dest, int pointer);

}
