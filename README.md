
<img src="./charts/logo.png" alt="Serenity" height="240" width="850">

## Serenity - A High Performance Serialization Library for Java

Serenity is a fast serialization library for Java which supports all Java Primitives,Objects , Strings, Arrays , List
and offers many performance benefits over the default Java Serialization implementation.

# Usage 
1) Build the project by taking a clone of main branch and using java 17 so that all the dependencies are downloaded.

2) Once the jar is installed, use this jar as dependency in your project. 
3) To use the serialization just invoke writeObjectAsBytes method using Serenity Object in your project. Here is an example of the same.
```java
Serenity serenity = new Serenity();
byte[] byteArray = serenity.writeObjectAsBytes(objectToBeSerialized);
```
Serenity reads all the non-final fields declared in the class and converts them into custom classes. 
Once all the custom classes are created for all the fields, it then converts them into bytes and return a byte array
-------------
One more implementation that mimics the jackson reading of fields in the class, that is using getter methods. 
This is used to solve the problem of unnecessary reading of fields that are not required for serialization, but downside is that it significantly impacts the performance of the Serenity.
```java
Serenity serenity = new Serenity();
byte[] byteArray = serenity.writeObjectAsBytesUsingMethods(objectToBeSerialized);
```

# Performance 
When compared with java serialization, <b>it has significant improvement upto 50% less time taken for serializing the same size object</b>. Even coming close to almost equal compared to json serialization(the god of serialization).
This is a graph of time taken in microseconds by 3 serialization methods when the object size is increased upto 200KB.

![Serenity](./charts/comparision%20chart.png)
## Future Prospects
Further progress can be made in these areas:-

1) There is a scope for improvement in the time taken by Serenity by reducing the number of duplicate custom objects created by the system
2) Serenity cannot handle other collections other than List, so that is a big area which asks for improvement
3) field annotations can be added just like in jackson to give more flexibility in creation of serialization objects.
