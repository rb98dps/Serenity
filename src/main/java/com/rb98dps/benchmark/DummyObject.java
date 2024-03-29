package com.rb98dps.benchmark;

import java.io.Serializable;
import java.util.List;

public class DummyObject implements Serializable {
    private int intField;
    private double doubleField;
    private String stringField;
    private boolean booleanField;

    private List<String> helloa;


    public DummyObject(int intField, double doubleField, String stringField, boolean booleanField, List<String> hello) {
        this.intField = intField;
        this.doubleField = doubleField;
        this.stringField = stringField;
        this.booleanField = booleanField;
        this.helloa = hello;
    }

    public int getIntField() {
        return intField;
    }

    public List<String> getHelloa() {
        return helloa;
    }

    public void setHelloa(List<String> helloa) {
        this.helloa = helloa;
    }

    public void setIntField(int intField) {
        this.intField = intField;
    }

    public double getDoubleField() {
        return doubleField;
    }

    public void setDoubleField(double doubleField) {
        this.doubleField = doubleField;
    }

    public String getStringField() {
        return stringField;
    }

    public void setStringField(String stringField) {
        this.stringField = stringField;
    }

    public boolean isBooleanField() {
        return booleanField;
    }

    public void setBooleanField(boolean booleanField) {
        this.booleanField = booleanField;
    }

}