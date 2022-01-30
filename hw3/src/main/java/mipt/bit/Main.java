package mipt.bit;

import mipt.bit.generators.JsonGenerator;
import mipt.bit.generators.JsonGeneratorFactory;
import mipt.bit.generators.JsonGeneratorFactoryImpl;
import mipt.bit.models.Person;
import mipt.bit.models.Student;
import mipt.bit.models.StudentWithCollection;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Person person = new Person(1L, "Ivan", "Ivanov");
        Student student = new Student(1L, "Ivan", "Ivanov", new int[4]);

        Collection<Integer> collection = new ArrayList<>();
        collection.add(1);
        collection.add(2);

        StudentWithCollection studentWithCollection =
                new StudentWithCollection(1L, "Ivan", "Ivanov", collection);

        JsonGeneratorFactory<Person> factory1 = new JsonGeneratorFactoryImpl<>(Person.class);
        JsonGenerator<Person> generator1 = factory1.createJsonGenerator();
        System.out.println(generator1.toJsonString(person));

        JsonGeneratorFactory<Student> factory2 = new JsonGeneratorFactoryImpl<>(Student.class);
        JsonGenerator<Student> generator2 = factory2.createJsonGenerator();
        System.out.println(generator2.toJsonString(student));

        JsonGeneratorFactory<StudentWithCollection> factory3 = new JsonGeneratorFactoryImpl<>(StudentWithCollection.class);
        JsonGenerator<StudentWithCollection> generator3 = factory3.createJsonGenerator();
        System.out.println(generator3.toJsonString(studentWithCollection));

    }
}
