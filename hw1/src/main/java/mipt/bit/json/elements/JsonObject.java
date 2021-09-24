package mipt.bit.json.elements;

import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class JsonObject extends AbstractJsonElement {
    private final Set<AbstractJsonElement> elements;

    public JsonObject(String key, Set<AbstractJsonElement> elements) {
        super(key);
        this.elements = elements;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JsonObject that = (JsonObject) o;
        return key.equals(that.key) && elements.containsAll(that.elements);
    }

    @Override
    public int hashCode() {
        AtomicInteger result = new AtomicInteger(key.hashCode());
        elements.forEach(node -> result.addAndGet(31 * result.get() + node.hashCode()));
        result.set(31 * result.get());
        return result.get();
    }
}
