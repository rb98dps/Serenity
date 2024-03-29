package com.rb98dps.benchmark;

import java.io.Serializable;

public class DummyObject1 implements Serializable {

    private int[] integers;

    private boolean[] booleans;

    private DummyObject[] objects;
    private String name;
    private int age;
    private boolean isActive;

    public DummyObject1(int[] integers, boolean[] booleans, DummyObject[] objects, String name, int age, boolean isActive, double score, DummyObject unknownObject) {
        this.integers = integers;
        this.booleans = booleans;
        this.objects = objects;
        this.name = name;
        this.age = age;
        this.isActive = isActive;
        this.score = score;
        this.unknownObject = unknownObject;
    }

    private double score;
    private DummyObject unknownObject;

    public void setObjects(DummyObject[] objects) {
        this.objects = objects;
    }

    public int[] getIntegers() {
        return integers;
    }

    public void setIntegers(int[] integers) {
        this.integers = integers;
    }

    public DummyObject[] getObjects() {
        return objects;
    }

    public boolean[] getBooleans() {
        return booleans;
    }

    public void setBooleans(boolean[] booleans) {
        this.booleans = booleans;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public DummyObject getUnknownObject() {
        return unknownObject;
    }

    public void setUnknownObject(DummyObject unknownObject) {
        this.unknownObject = unknownObject;
    }

}
