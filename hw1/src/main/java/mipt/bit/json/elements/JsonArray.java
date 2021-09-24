package mipt.bit.json.elements;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class JsonArray extends AbstractJsonElement {
    private final List<AbstractJsonElement> list;

    public JsonArray(String key, List<AbstractJsonElement> list) {
        super(key);
        this.list = list;
    }

    public List<AbstractJsonElement> getList() {
        return list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JsonArray)) return false;
        JsonArray jsonArray = (JsonArray) o;
        return key.equals(jsonArray.key) && list.containsAll(jsonArray.list);
    }

    @Override
    public int hashCode() {
        AtomicInteger result = new AtomicInteger(key.hashCode());
        list.forEach(elem -> result.addAndGet(31 * elem.hashCode()));
        return result.get();
    }
}
