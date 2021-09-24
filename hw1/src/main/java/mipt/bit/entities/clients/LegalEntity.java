package mipt.bit.entities.clients;

import java.util.Objects;

public class LegalEntity implements Client {
    private final String name;
    private final int inn;

    public LegalEntity(String name, int inn) {
        this.name = name;
        this.inn = inn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LegalEntity that = (LegalEntity) o;
        return name.equals(that.name) && (inn == that.inn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, inn);
    }

    @Override
    public String toString() {
        return "LegalEntity{" +
                "name='" + name + '\'' +
                ", inn='" + inn + '\'' +
                '}';
    }
}
