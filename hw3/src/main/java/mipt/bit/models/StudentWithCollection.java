package mipt.bit.models;

import java.util.Collection;

public class StudentWithCollection {
    private final Long id;
    private final String name;
    private final String lastName;
    private final Collection<Integer> marks;

    public StudentWithCollection(Long id, String name, String lastName, Collection<Integer> marks) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.marks = marks;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public Collection<Integer> getMarks() {
        return marks;
    }
}
