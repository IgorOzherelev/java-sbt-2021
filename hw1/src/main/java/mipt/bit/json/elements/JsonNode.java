package mipt.bit.json.elements;

public class JsonNode extends AbstractJsonElement {
    private final String value;

    public JsonNode(String key, String value) {
        super(key);
        this.value = value.replaceAll("\"", "");
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JsonNode jsonNode = (JsonNode) o;

        String key = jsonNode.getKey();
        String value = jsonNode.getValue();
        return this.key.equals(key) && this.value.equals(value);
    }

    @Override
    public int hashCode() {
        int result = key.hashCode();
        result = 31 * result + value.hashCode();
        return result;
    }
}
