package mipt.bit;

import mipt.bit.generators.JsonGenerator;
import mipt.bit.generators.JsonGeneratorFactory;
import mipt.bit.generators.JsonGeneratorFactoryImpl;
import mipt.bit.models.Letter;
import mipt.bit.models.Person;
import mipt.bit.models.Student;
import mipt.bit.models.StudentWithCollection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;

public class JsonGeneratorFactoryTest {
    @Test
    public void test001_whenOnlyWrapperOrPrimitives() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Person person = new Person(1L, "Ivan", "Ivanov");

        Assertions.assertEquals("{\n" +
                "\t\"id\":1,\n" +
                "\t\"name\":\"Ivan\",\n" +
                "\t\"lastName\":\"Ivanov\"\n" +
                "}", buildJsonString(Person.class, person));
    }

    @Test
    public void test002_whenArrayOfWrappersOrPrimitives() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        int[] arr = new int[2];
        arr[0] = 1;
        arr[1] = 1;
        Student student = new Student(1L, "Ivan", "Ivanov", arr);

        Assertions.assertEquals("{\n" +
                "\t\"id\":1,\n" +
                "\t\"name\":\"Ivan\",\n" +
                "\t\"lastName\":\"Ivanov\",\n" +
                "\t\"marks\":[1, 1]\n" +
                "}", buildJsonString(Student.class, student));
    }

    @Test
    public void test003_whenCollectionOfWrappers() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Collection<Integer> collection = new ArrayList<>();
        collection.add(1);
        collection.add(2);

        StudentWithCollection studentWithCollection =
                new StudentWithCollection(1L, "Ivan", "Ivanov", collection);

        Assertions.assertEquals("{\n" +
                "\t\"id\":1,\n" +
                "\t\"name\":\"Ivan\",\n" +
                "\t\"lastName\":\"Ivanov\",\n" +
                "\t\"marks\":[1, 2]\n" +
                "}", buildJsonString(StudentWithCollection.class, studentWithCollection));
    }

    @Test
    public void test004_whenHasNotAllGettersForFields() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Letter letter = new Letter("addresTo", "addressFrom");

        Assertions.assertEquals("{\n" +
                "\t\"addressTo\":\"addresTo\"\n" +
                "}", buildJsonString(Letter.class, letter));
    }

    private <T> String buildJsonString(Class<T> clazz, T instance) throws ClassNotFoundException, InvocationTargetException,
            NoSuchMethodException, InstantiationException, IllegalAccessException {
        JsonGeneratorFactory<T> factory = new JsonGeneratorFactoryImpl<>(clazz);
        JsonGenerator<T> generator = factory.createJsonGenerator();

        return generator.toJsonString(instance);
    }
}
