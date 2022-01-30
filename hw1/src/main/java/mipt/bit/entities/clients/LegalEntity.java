package mipt.bit.entities.clients;

import mipt.bit.entities.enums.ClientType;

import java.util.Objects;

import static mipt.bit.utils.ClientUtils.checkInn;

public class LegalEntity implements Client {
    private final String name;
    private final int inn;

    public LegalEntity(String name, int inn) {
        this.name = name;
        this.inn = inn;
    }

    public static LegalEntity createLegalEntity(String name, int inn) {
        String innString = String.valueOf(inn);
        checkInn(innString, ClientType.LEGAL_ENTITY.getValue());
        return new LegalEntity(name, inn);
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
