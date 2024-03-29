package com.rb98dps.serenity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import static com.rb98dps.serenity.SerenityUtils.*;

public class Serenity {


    public void printObject(SerObject object, String initalSpacing) {
        System.out.println(initalSpacing + object.getName());
        initalSpacing += "\t";
        for (SerField field : object.fields) {
            System.out.println(initalSpacing + field.getName() + " " + field.getValue());
        }
        for (SerArray array : object.arrays) {

            if (Type.SER_OBJECT.equals(array.getType())) {
                for (SerObject arrayObject : array.getObjectData()) {
                    printObject(arrayObject, initalSpacing + "\t\t");
                }
            } else if (Type.SER_STRING.equals(array.getType())) {
                StringBuilder string = new StringBuilder();
                for (SerString arrayString : array.getStringData()) {
                    string.append(arrayString.getString()).append(" ");
                }
                System.out.println(initalSpacing + array.getName() + ": " + string);
            } else {
                System.out.println(initalSpacing + array.getName() + " " + array.getValues());
            }
        }
        for (SerString string : object.strings) {
            System.out.println(initalSpacing + string.getName() + " : " + string.getString());
        }
        for (SerObject insideObject : object.objects) {
            printObject(insideObject, initalSpacing + "\t\t");
        }
    }

    public void serializeArray(String fieldName, Object arrayObject, SerObject parent) {
        if (arrayObject instanceof int[]) {
            parent.addArray(SerArray.Integer(fieldName, (int[]) arrayObject));
        } else if (arrayObject instanceof double[]) {
            parent.addArray(SerArray.Double(fieldName, (double[]) arrayObject));
        } else if (arrayObject instanceof boolean[]) {
            parent.addArray(SerArray.Boolean(fieldName, (boolean[]) arrayObject));
        } else if (arrayObject instanceof char[]) {
            parent.addArray(SerArray.Char(fieldName, (char[]) arrayObject));
        } else if (arrayObject instanceof byte[]) {
            parent.addArray(SerArray.Byte(fieldName, (byte[]) arrayObject));
        } else if (arrayObject instanceof short[]) {
            parent.addArray(SerArray.Short(fieldName, (short[]) arrayObject));
        } else if (arrayObject instanceof long[]) {
            parent.addArray(SerArray.Long(fieldName, (long[]) arrayObject));
        } else if (arrayObject instanceof float[]) {
            parent.addArray(SerArray.Float(fieldName, (float[]) arrayObject));
        } else if (arrayObject instanceof Object[]) {
            Object[] array = (Object[]) arrayObject;
            ArrayType type = getArrayType(array);
            switch (type) {
                case INTEGER:
                    int[] intArray = new int[array.length];
                    for (int i = 0; i < array.length; i++) {
                        intArray[i] = (Integer) array[i];
                    }
                    parent.addArray(SerArray.Integer(fieldName, intArray));
                    break;
                case SHORT:
                    short[] shortArray = new short[array.length];
                    for (int i = 0; i < array.length; i++) {
                        shortArray[i] = (Short) array[i];
                    }
                    parent.addArray(SerArray.Short(fieldName, shortArray));
                    break;
                case DOUBLE:
                    double[] doubleArray = new double[array.length];
                    for (int i = 0; i < array.length; i++) {
                        doubleArray[i] = (Double) array[i];
                    }
                    parent.addArray(SerArray.Double(fieldName, doubleArray));
                    break;
                case FLOAT:
                    float[] floatArray = new float[array.length];
                    for (int i = 0; i < array.length; i++) {
                        floatArray[i] = (Float) array[i];
                    }
                    parent.addArray(SerArray.Float(fieldName, floatArray));
                    break;
                case LONG:
                    long[] longArray = new long[array.length];
                    for (int i = 0; i < array.length; i++) {
                        longArray[i] = (Long) array[i];
                    }
                    parent.addArray(SerArray.Long(fieldName, longArray));
                    break;
                case BYTE:
                    byte[] byteArray = new byte[array.length];
                    for (int i = 0; i < array.length; i++) {
                        byteArray[i] = (Byte) array[i];
                    }
                    parent.addArray(SerArray.Byte(fieldName, byteArray));
                    break;
                case CHAR:
                    char[] charArray = new char[array.length];
                    for (int i = 0; i < array.length; i++) {
                        charArray[i] = (Character) array[i];
                    }
                    parent.addArray(SerArray.Char(fieldName, charArray));
                    break;
                case BOOLEAN:
                    boolean[] booleanArray = new boolean[array.length];
                    for (int i = 0; i < array.length; i++) {
                        booleanArray[i] = (Boolean) array[i];
                    }
                    parent.addArray(SerArray.Boolean(fieldName, booleanArray));
                    break;
                case STRING:
                    SerString[] serStrings = new SerString[array.length];
                    for (int i = 0; i < array.length; i++) {
                        serStrings[i] = SerString.create(fieldName + i, (String) array[i]);
                    }
                    parent.addArray(SerArray.SerString(fieldName, serStrings));
                    break;
                case UNKNOWN:
                    SerObject[] serObjects = new SerObject[array.length];
                    for (int i = 0; i < array.length; i++) {
                        serObjects[i] = new SerObject(fieldName + i);
                        serializeObjectByField(array[i], serObjects[i]);
                    }
                    parent.addArray(SerArray.SerObject(fieldName, serObjects));
                    break;

            }
        }

    }

    private void serializeList(String fieldName, List<?> list, SerObject parent) {
        if (!list.isEmpty()) {
            ArrayType type = getArrayTypeForList(list);
            int size = list.size();
            switch (type) {
                case INTEGER:
                    int[] intArray = new int[size];
                    for (int i = 0; i < size; i++) {
                        intArray[i] = (Integer) list.get(i);
                    }
                    parent.addArray(SerArray.Integer(fieldName, intArray));
                    break;
                case SHORT:
                    short[] shortArray = new short[size];
                    for (int i = 0; i < size; i++) {
                        shortArray[i] = (Short) list.get(i);
                    }
                    parent.addArray(SerArray.Short(fieldName, shortArray));
                    break;
                case DOUBLE:
                    double[] doubleArray = new double[size];
                    for (int i = 0; i < size; i++) {
                        doubleArray[i] = (Double) list.get(i);
                    }
                    parent.addArray(SerArray.Double(fieldName, doubleArray));
                    break;
                case FLOAT:
                    float[] floatArray = new float[size];
                    for (int i = 0; i < size; i++) {
                        floatArray[i] = (Float) list.get(i);
                    }
                    parent.addArray(SerArray.Float(fieldName, floatArray));
                    break;
                case LONG:
                    long[] longArray = new long[size];
                    for (int i = 0; i < size; i++) {
                        longArray[i] = (Long) list.get(i);
                    }
                    parent.addArray(SerArray.Long(fieldName, longArray));
                    break;
                case BYTE:
                    byte[] byteArray = new byte[size];
                    for (int i = 0; i < size; i++) {
                        byteArray[i] = (Byte) list.get(i);
                    }
                    parent.addArray(SerArray.Byte(fieldName, byteArray));
                    break;
                case CHAR:
                    char[] charArray = new char[size];
                    for (int i = 0; i < size; i++) {
                        charArray[i] = (Character) list.get(i);
                    }
                    parent.addArray(SerArray.Char(fieldName, charArray));
                    break;
                case BOOLEAN:
                    boolean[] booleanArray = new boolean[size];
                    for (int i = 0; i < size; i++) {
                        booleanArray[i] = (Boolean) list.get(i);
                    }
                    parent.addArray(SerArray.Boolean(fieldName, booleanArray));
                    break;
                case STRING:
                    SerString[] serStrings = new SerString[size];
                    for (int i = 0; i < size; i++) {
                        serStrings[i] = SerString.create(fieldName + i, (String) list.get(i));
                    }
                    parent.addArray(SerArray.SerString(fieldName, serStrings));
                    break;
                case UNKNOWN:
                    SerObject[] serObjects = new SerObject[size];
                    for (int i = 0; i < size; i++) {
                        serObjects[i] = new SerObject(fieldName + i);
                        serializeObjectByField(list.get(i), serObjects[i]);
                    }
                    parent.addArray(SerArray.SerObject(fieldName, serObjects));
                    break;

            }
        }
    }

    public void serializeObjectByField(Object obj, SerObject parent) {
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String fieldName = field.getName();

            Object value;
            try {
                value = field.get(obj);
            } catch (IllegalAccessException e) {
                value = null;
            }

            if (value != null) {
                if (value.getClass().isArray()) {
                    serializeArray(fieldName, value, parent);
                } else if (value instanceof List) {
                    serializeList(fieldName, (List<?>) value, parent);
                } else if (isPrimitive(value.getClass())) {
                    SerField serField = createSerField(fieldName, value);
                    parent.addField(serField);
                } else if (value.getClass() == String.class) {
                    SerString serString = SerString.create(fieldName, String.valueOf(value));
                    parent.addString(serString);
                } else {
                    SerObject childObject = new SerObject(fieldName);
                    serializeObjectByField(value, childObject);
                    parent.addObject(childObject);
                }
            }
        }
    }

    public void serializeObjectByMethods(Object obj, SerObject parent) {

        Class<?> clazz = obj.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if (isGetter(method)) {
                String fieldName = getFieldName(method.getName());
                try {
                    Object value = method.invoke(obj);

                    if (value != null) {
                        if (value.getClass().isArray()) {
                            serializeArray(fieldName, value, parent);
                        } else if (value instanceof List) {
                            serializeList(fieldName, (List<?>) value, parent);
                        } else if (isPrimitive(value.getClass())) {
                            SerField serField = createSerField(fieldName, value);
                            parent.addField(serField);
                        } else if (value.getClass() == String.class) {
                            SerString serString = SerString.create(fieldName, (String)value);
                            parent.addString(serString);
                        } else {
                            SerObject childObject = new SerObject(fieldName);
                            serializeObjectByField(value, childObject);
                            parent.addObject(childObject);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public byte[] writeObjectAsBytes(Object obj) {
        SerObject serObject = new SerObject(obj.getClass().getSimpleName());
        serializeObjectByField(obj, serObject);
        return serObject.getSerializedData();
    }

    public byte[] writeObjectAsBytesUsingMethods(Object obj) {
        SerObject serObject = new SerObject(obj.getClass().getSimpleName());
        serializeObjectByMethods(obj, serObject);
        return serObject.getSerializedData();
    }


}
