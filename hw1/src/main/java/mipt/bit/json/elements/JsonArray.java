package mipt.bit.json.elements;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class JsonArray implements JsonElement {
    private final List<JsonElement> list;

    public JsonArray(List<JsonElement> list) {
        this.list = list;
    }

    public List<JsonElement> getList() {
        return list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JsonArray)) return false;
        JsonArray jsonArray = (JsonArray) o;
        return list.containsAll(jsonArray.list);
    }

    @Override
    public int hashCode() {
        AtomicInteger result = new AtomicInteger(1);
        list.forEach(elem -> result.addAndGet(31 * elem.hashCode()));
        return result.get();
    }
}
