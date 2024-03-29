package com.rb98dps.benchmark;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rb98dps.serenity.Serenity;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class SerializationBenchmark {
    private static List<Long> javaSerializationTimes = new ArrayList<>();
    private static List<Long> jsonSerializationTimes = new ArrayList<>();
    private static List<Long> serenitySerializationTimes = new ArrayList<>();
    private static List<Long> javaSerializationSizes = new ArrayList<>();
    private static List<Long> jsonSerializationSizes = new ArrayList<>();
    private static List<Long> serenitySerializationSizes = new ArrayList<>();

    public static class BenchmarkState {
        private int iteration = 1;
        private DummyObject1 heavyClass;

        public void setup() {

            int arraySize = 10 * iteration;
            int[] integers = IntStream.range(0, arraySize).toArray();
            boolean[] booleans = new boolean[arraySize];
            Arrays.fill(booleans, true);
            DummyObject[] objects = new DummyObject[arraySize];
            for (int i = 0; i < arraySize; i++) {
                List<String> hellos = new ArrayList<>();
                for (int j = 0; j < i + 1; j++) {
                    hellos.add("hello"+i);
                }
                objects[i] = new DummyObject(i, 3.14 * i, "Hello" + i, true, hellos);
            }
            heavyClass = new DummyObject1(integers, booleans, objects, "John" + iteration, 25 * iteration, true, 3.14 * iteration, null);
            iteration++;
        }

        void iterationReset() {
            iteration = 1;
        }

        private ObjectMapper objectMapper = new ObjectMapper();

        private Serenity serenity = new Serenity();

    }


    public static long serenitySerialization(BenchmarkState state) {

        long startTime = System.nanoTime();
        byte[] byteArray = state.serenity.writeObjectAsBytes(state.heavyClass);
        long endTime = System.nanoTime();
        return (endTime - startTime)/1000;
    }

    public static long javaSerialization(BenchmarkState state) throws Exception {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        long startTime = System.nanoTime();
        objectOutputStream.writeObject(state.heavyClass);
        objectOutputStream.close();
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        long endTime = System.nanoTime();
        return (endTime - startTime)/1000;

    }

    public static long jsonSerialization(BenchmarkState state) throws Exception {

        long startTime = System.nanoTime();
        byte[] byteArray = state.objectMapper.writeValueAsBytes(state.heavyClass);
        long endTime = System.nanoTime();
        jsonSerializationSizes.add((long)byteArray.length);
        //System.out.println(state.objectMapper.writeValueAsString(state.heavyClass));
        return (endTime - startTime)/1000;
    }


    public static void main(String[] args) throws Exception {

        int iterations = 2000;
        int averageIteration = 50;

        BenchmarkState benchmarkState = new BenchmarkState();
        benchmarkState.setup();
        benchmarkState.iterationReset();
        for (int j = 0; j < iterations; j++) {
            long average = 0;
            for (int i = 0; i < averageIteration; i++) {
                average+=javaSerialization(benchmarkState);
            }
            average/=averageIteration;
            javaSerializationTimes.add(average);
            benchmarkState.setup();
        }
        System.out.println("BenchMarking done for Java Serialization");
        benchmarkState.iterationReset();
        benchmarkState.setup();
        for (int j = 0; j < iterations; j++) {
            long average = 0;
            for (int i = 0; i < averageIteration; i++) {
                average+=jsonSerialization(benchmarkState);
            }
            average/=averageIteration;
            jsonSerializationTimes.add(average);
            benchmarkState.setup();
        }
        System.out.println("BenchMarking done for Java Serialization");
        benchmarkState.iterationReset();
        benchmarkState.setup();
        for (int j = 0; j < iterations; j++) {
            long average = 0;
            for (int i = 0; i < averageIteration; i++) {
                average+=serenitySerialization(benchmarkState);
            }
            average/=averageIteration;
            serenitySerializationTimes.add(average);
            benchmarkState.setup();
        }
        benchmarkState.iterationReset();
        System.out.println("BenchMarking done for Java Serialization");
        Path path = Paths.get("serialization_results.csv");
        StringBuilder builder = new StringBuilder();
        builder.append("Data Size;Java Serialization;JSON Serialization;Serenity Serialization\n");
        for (int i = 0; i < javaSerializationTimes.size(); i++) {
            double dataSize = ((double)jsonSerializationSizes.get(i * averageIteration))/1024;
            long javaTime = javaSerializationTimes.get(i);
            long jsonTime = jsonSerializationTimes.get(i);
            long serenityTime = serenitySerializationTimes.get(i);
            builder.append(dataSize).append(";").append(javaTime).append(";").append(jsonTime).append(";").append(serenityTime).append("\n");
        }
        try {
            Files.write(path, builder.toString().getBytes());
            System.out.println("CSV file exported successfully to: " + path);
        } catch (Exception e) {
            System.err.println("Error exporting CSV file: " + e.getMessage());
        }
    }


}

