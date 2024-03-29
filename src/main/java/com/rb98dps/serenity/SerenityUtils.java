package com.rb98dps.serenity;

import java.lang.reflect.Method;
import java.util.List;

public class SerenityUtils {

    private SerenityUtils() {

    }

    public static boolean isPrimitive(Class<?> clazz) {
        return clazz.isPrimitive() || clazz == Integer.class || clazz == Double.class || clazz == Boolean.class || clazz == Character.class;
    }

    public static boolean isGetter(Method method) {
        return (method.getName().startsWith("get") || method.getName().startsWith("is")) && method.getParameterCount() == 0;
    }

    public static String getFieldName(String methodName) {
        if (methodName.startsWith("get")) {
            return Character.toLowerCase(methodName.charAt(3)) + methodName.substring(4);
        } else if (methodName.startsWith("is")) {
            return Character.toLowerCase(methodName.charAt(2)) + methodName.substring(3);
        }
        return methodName;
    }


    public static ArrayType getArrayTypeForList(List<?> list) {
        Class<?> elementType;
        if (!list.isEmpty()) {
            elementType = list.get(0).getClass();
            return getObjectType(elementType);
        }
        return ArrayType.UNKNOWN;
    }

    public static ArrayType getArrayType(Object[] array) {
        if (array.length > 0) {
            Class<?> elementType = array[0].getClass();
            return getObjectType(elementType);
        }
        return ArrayType.UNKNOWN;
    }


    public static ArrayType getObjectType(Class<?> elementType) {
        ArrayType type = ArrayType.UNKNOWN;
        if (elementType != null) {
            if (elementType.equals(String.class)) {
                type = ArrayType.STRING;
            } else if (elementType.equals(Integer.class)) {
                type = ArrayType.INTEGER;
            } else if (elementType.equals(Long.class)) {
                type = ArrayType.LONG;
            } else if (elementType.equals(Double.class)) {
                type = ArrayType.DOUBLE;
            } else if (elementType.equals(Float.class)) {
                type = ArrayType.FLOAT;
            } else if (elementType.equals(Byte.class)) {
                type = ArrayType.BYTE;
            } else if (elementType.equals(Short.class)) {
                type = ArrayType.SHORT;
            } else if (elementType.equals(Character.class)) {
                type = ArrayType.CHAR;
            } else if (elementType.equals(Boolean.class)) {
                type = ArrayType.BOOLEAN;
            }

        }
        return type;
    }

    public static SerField createSerField(String fieldName, Object value) {
        if (value instanceof Integer) {
            return SerField.Integer(fieldName, (int) value);
        } else if (value instanceof Double) {
            return SerField.Double(fieldName, (double) value);
        } else if (value instanceof Boolean) {
            return SerField.Boolean(fieldName, (boolean) value);
        } else if (value instanceof Character) {
            return SerField.Char(fieldName, (char) value);
        } else if (value instanceof Float) {
            return SerField.Float(fieldName, (float) value);
        } else if (value instanceof Short) {
            return SerField.Short(fieldName, (short) value);
        } else {
            return SerField.Byte(fieldName, (byte) value);
        }
    }
}
