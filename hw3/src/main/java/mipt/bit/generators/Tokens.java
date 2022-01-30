package mipt.bit.generators;

public enum Tokens {
    PUBLIC(" public "),
    PRIVATE(" private "),
    VOID(" void "),
    CLASS(" class "),
    IMPLEMENTS(" implements "),
    OVERRIDE("@Override\n"),
    T(" T "),
    GENERIC_T(" <T> "),
    JSON_GENERATOR(" JsonGenerator<T> "),
    STRING(" String "),
    TO_JSON_STRING_FUNCTION("toJsonString(T instance) {\n\t"),

    NEXT_LINE("\n");

    private final String tokenString;

    public String getTokenString() {
        return this.tokenString;
    }

    Tokens(String tokenString) {
        this.tokenString = tokenString;
    }
}