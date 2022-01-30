package mipt.bit.generators;

public interface JsonGenerator<T> {
    String toJsonString(T instance);
}
