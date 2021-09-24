package mipt.bit.entities.clients;

import java.util.List;
import java.util.Objects;

public class Holding implements Client {
    private final String name;
    private final int inn;
    private final List<Integer> companiesInn;

    public Holding(String name, int inn, List<Integer> companiesInn) {
        this.name = name;
        this.inn = inn;
        this.companiesInn = companiesInn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Holding holding = (Holding) o;
        return name.equals(holding.name) && (inn == holding.inn) && companiesInn.equals(holding.companiesInn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, inn, companiesInn);
    }

    @Override
    public String toString() {
        return "Holding{" +
                "name='" + name + '\'' +
                ", inn='" + inn + '\'' +
                ", companiesInn=" + companiesInn +
                '}';
    }
}
