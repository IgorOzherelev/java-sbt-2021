package mipt.bit.json.elements;

public class JsonNode implements JsonElement {
    private final String value;

    public JsonNode(String value) {
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

        String value = jsonNode.getValue();
        return this.value.equals(value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
