package mipt.bit.entities.clients;

import mipt.bit.entities.enums.ClientType;

import java.util.Objects;

import static mipt.bit.utils.ClientUtils.checkInn;

public class Individual implements Client {
    private final String name;
    private final String surname;
    private final String inn;

    public Individual(String name, String surname, String inn) {
        this.name = name;
        this.surname = surname;
        this.inn = inn;
    }

    public static Individual createIndividual(String name, String surname, String inn) {
        checkInn(inn, ClientType.INDIVIDUAL.getValue());
        return new Individual(name, surname, inn);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Individual that = (Individual) o;
        return name.equals(that.name) && surname.equals(that.surname) && (inn.equals(that.inn));
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
