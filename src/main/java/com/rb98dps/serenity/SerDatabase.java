package com.rb98dps.serenity;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static com.rb98dps.serenity.SerializationReader.*;
import static com.rb98dps.serenity.SerializationWriter.writeBytes;

public class SerDatabase extends SerBase {
    private static final byte[] HEADER = "CLDB".getBytes();
    private static final short VERSION = 0x0001;
    public static final byte CONTAINER_TYPE = ContainerType.DATABASE;
    private short objectCount;
    protected List<SerObject> objects = new ArrayList<SerObject>();

    public List<SerObject> getObjects() {
        return objects;
    }

    private SerDatabase() {
    }

    public SerDatabase(String name) {
        size += HEADER.length + 2 + 1 + 2;
        setName(name);
    }

    public short getObjectCount() {
        return objectCount;
    }

    public void addObject(SerObject object) {
        objects.add(object);
        size += object.getSize();
        objectCount = (short) objects.size();
    }

    public int getSize() {
        return size;
    }

    public int getBytes(byte[] dest, int pointer) {
        pointer = writeBytes(dest, pointer, HEADER);
        pointer = writeBytes(dest, pointer, VERSION);
        pointer = serializeDefaultAttributes(dest, pointer, CONTAINER_TYPE, true);
        pointer = writeBytes(dest, pointer, objectCount);
        for (SerObject object : objects) {
            pointer = object.getBytes(dest, pointer);
        }

        return pointer;
    }

    public static SerDatabase deserialize(byte[] data) {
        int pointer = 0;
        assert (readString(data, pointer, HEADER.length).equals(new String(HEADER)));
        pointer += HEADER.length;

        if (readShort(data, pointer) != VERSION) {
            System.err.println("CLDB version number mismatch. Please check if the library version matches the version of the file you're trying to read.");
            System.err.println("Expected: v" + VERSION + ", found: v" + readShort(data, pointer));
            return null;
        }
        pointer += Type.getDefaultSize(Type.SHORT);
        SerDatabase result = new SerDatabase();
        pointer = deserializeDefaultAttributes(result, data, pointer, CONTAINER_TYPE,true);

        result.objectCount = readShort(data, pointer);
        pointer += Type.getDefaultSize(Type.SHORT);

        for (int i = 0; i < result.objectCount; ++i) {
            SerObject object = SerObject.deserialize(data, pointer);
            result.objects.add(object);
            pointer += object.getSize();
        }
        return result;
    }

    public SerObject findObject(String name) {
        for (SerObject object : objects) {
            if (object.getName().equals(name)) {
                return object;
            }
        }
        return null;
    }

    public List<String> listObjects() {
        List<String> result = new ArrayList<String>();
        for (SerObject object : objects) {
            result.add(object.getName());
        }
        return result;
    }

    public static SerDatabase deserializeFromFile(String path) {
        byte[] buffer = null;
        try {
            BufferedInputStream stream = new BufferedInputStream(Files.newInputStream(Paths.get(path)));
            buffer = new byte[stream.available()];
            stream.read(buffer);
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return deserialize(buffer);
    }

    public void serializeToFile(String path) {
        byte[] data = new byte[getSize()];
        getBytes(data, 0);

        try (BufferedOutputStream stream = new BufferedOutputStream(Files.newOutputStream(Paths.get(path)))) {
            stream.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
