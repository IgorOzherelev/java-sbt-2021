package mipt.bit.json.elements;

import java.util.Objects;

abstract public class AbstractJsonElement {
    protected String key = null;

    public AbstractJsonElement(String key) {
        if (key != null) {
            this.key = key.replaceAll("\"", "");
        }
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractJsonElement)) return false;
        AbstractJsonElement that = (AbstractJsonElement) o;
        return key.equals(that.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }
}
