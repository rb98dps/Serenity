package com.rb98dps.serenity;

public enum Type {
    UNKNOWN(0),
    BYTE(1),
    SHORT(2),
    CHAR(3),
    INTEGER(4),
    LONG(5),
    FLOAT(6),
    DOUBLE(7),
    BOOLEAN(8),
    SER_OBJECT(9),
    SER_STRING(10);
    public final byte value;

    Type(int value) {
        this.value = (byte) value;
    }

    public byte getValue() {
        return value;
    }

    public static int getDefaultSize(Type type) {
        return switch (type) {
            case BYTE, BOOLEAN -> 1;
            case SHORT, CHAR -> 2;
            case INTEGER, FLOAT -> 4;
            case LONG, DOUBLE -> 8;
            default -> 0;
        };
    }


    public static Type getTypeFromValue(byte value) {
        for (Type type : Type.values()) {
            if (type.value == value) {
                return type;
            }
        }
        return UNKNOWN;
    }
}
