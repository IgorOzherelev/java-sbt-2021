package mipt.bit;

import mipt.bit.generators.JsonGenerator;
import mipt.bit.models.Person;

public class DummyGenerator<T> implements JsonGenerator<T> {

    @Override
    public String toJsonString(T instance) {
        StringBuilder builder = new StringBuilder();

        Person person = (mipt.bit.models.Person) instance;
        builder.append("{\n\t").append("\"id\":");
        builder.append("\"").append(person.getId()).append("\",\n").append("\t");
        builder.append("\"lastName:\"");
        builder.append("\"").append(person.getLastName()).append("\",\n").append("\t");
        builder.append("\"name\":");
        builder.append("\"").append(person.getName()).append("\"\n").append("}");

        return builder.toString();
    }
}
