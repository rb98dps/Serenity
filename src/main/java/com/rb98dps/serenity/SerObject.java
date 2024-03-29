package com.rb98dps.serenity;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static com.rb98dps.serenity.SerializationReader.*;
import static com.rb98dps.serenity.SerializationWriter.writeBytes;
import static com.rb98dps.serenity.SerializationWriter.writeBytesForAttributes;


public class SerObject extends SerBase {
    public static final byte CONTAINER_TYPE = ContainerType.OBJECT;
    short fieldCount;
    public List<SerField> fields = new ArrayList<>();
    private short stringCount;
    public List<SerString> strings = new ArrayList<>();
    private short arrayCount;
    public List<SerArray> arrays = new ArrayList<>();

    public List<SerObject> objects = new ArrayList<>();

    private short objectCount;

    private SerObject() {
    }

    public SerObject(String name) {
        size += 1 + 2 + 2 + 2 + 2;
        setName(name);
    }

    public void addField(SerField field) {
        fields.add(field);
        size += field.getSize();
        fieldCount = (short) fields.size();
    }

    public void addObject(SerObject object) {
        objects.add(object);
        size += object.getSize();
        objectCount = (short) objects.size();
    }

    public void addString(SerString string) {
        strings.add(string);
        size += string.getSize();
        stringCount = (short) strings.size();
    }


    public void addArray(SerArray array) {
        arrays.add(array);
        size += array.getSize();
        arrayCount = (short) arrays.size();
    }

    public int getSize() {
        return size;
    }


    public SerField findField(String name) {
        for (SerField field : fields) {
            if (field.getName().equals(name)) {
                return field;
            }
        }
        return null;
    }


    public SerString findString(String name) {
        for (SerString string : strings) {
            if (string.getName().equals(name)) {
                return string;
            }
        }
        return null;
    }


    public SerArray findArray(String name) {
        for (SerArray array : arrays) {
            if (array.getName().equals(name)) {
                return array;
            }
        }
        return null;
    }

    @Override
    public int getBytes(byte[] dest, int pointer) {
        pointer = serializeDefaultAttributes(dest, pointer, CONTAINER_TYPE, true);
        pointer = writeBytesForAttributes(dest, pointer, fieldCount, fields);
        pointer = writeBytesForAttributes(dest, pointer, stringCount, strings);
        pointer = writeBytesForAttributes(dest, pointer, arrayCount, arrays);
        pointer = writeBytesForAttributes(dest, pointer, objectCount, objects);

        return pointer;
    }

    public static SerObject deserialize(byte[] data, int pointer) {
        SerObject result = new SerObject();
        pointer = deserializeDefaultAttributes(result, data, pointer, CONTAINER_TYPE,true);

        result.fieldCount = readShort(data, pointer);
        pointer += Type.getDefaultSize(Type.SHORT);

        for (int i = 0; i < result.fieldCount; ++i) {
            SerField field = SerField.deserialize(data, pointer);
            result.fields.add(field);
            pointer += field.getSize();
        }

        result.stringCount = readShort(data, pointer);
        pointer += Type.getDefaultSize(Type.SHORT);

        for (int i = 0; i < result.stringCount; ++i) {
            SerString string = SerString.deserialize(data, pointer);
            result.strings.add(string);
            pointer += string.getSize();
        }

        result.arrayCount = readShort(data, pointer);
        pointer += Type.getDefaultSize(Type.SHORT);

        for (int i = 0; i < result.arrayCount; ++i) {
            SerArray array = SerArray.deserialize(data, pointer);
            result.arrays.add(array);
            pointer += array.getSize();
        }

        result.objectCount = readShort(data, pointer);
        pointer += Type.getDefaultSize(Type.SHORT);

        for (int i = 0; i < result.objectCount; ++i) {
            SerObject object = SerObject.deserialize(data, pointer);
            result.objects.add(object);
            pointer += object.getSize();
        }

        return result;
    }

    public byte[] getSerializedData() {
        byte[] data = new byte[getSize()];
        getBytes(data, 0);
        return data;
    }


}
