package mipt.bit.entities.clients;

import java.util.Objects;

public class Individual implements Client {
    private final String name;
    private final String surname;
    private final int inn;

    public Individual(String name, String surname, int inn) {
        this.name = name;
        this.surname = surname;
        this.inn = inn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Individual that = (Individual) o;
        return name.equals(that.name) && surname.equals(that.surname) && (inn == that.inn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, inn);
    }

    @Override
    public String toString() {
        return "Individual{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", inn='" + inn + '\'' +
                '}';
    }
}
