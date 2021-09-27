package mipt.bit.models;

public class Student {
    private final Long id;
    private final String name;
    private final String lastName;
    private final int[] marks;

    public Student(Long id, String name, String lastName, int[] marks) {
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

    public int[] getMarks() {
        return marks;
    }
}
