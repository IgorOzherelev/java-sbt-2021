package mipt.bit.json;

import mipt.bit.json.elements.AbstractJsonElement;
import mipt.bit.json.elements.JsonArray;
import mipt.bit.json.elements.JsonNode;
import mipt.bit.json.elements.JsonObject;
import mipt.bit.json.exceptions.RecursionDepthException;

import java.util.*;
import java.util.stream.Collectors;

import static mipt.bit.utils.Utils.checkNotNull;

public class JsonParser {
    private final static int MAX_RECURSION_DEPTH = 100;

    private final Map<String, AbstractJsonElement> jsonMap = new HashMap<>();
    private final JsonOffsetBuffer jsonBuffer;

    private int currentDepth = 0;
    private final String json;

    private boolean isPrimitiveArray = false;

    private boolean inRecursion = false;

    private enum Token {
        START_OBJECT_TOKEN('{'),
        END_OBJECT_TOKEN('}'),
        START_ARRAY_TOKEN('['),
        END_ARRAY_TOKEN(']'),
        COLON_TOKEN(':'),
        COMMA_TOKEN(',');

        private final char value;

        Token(char value) {
            this.value = value;
        }

        public static boolean isToken(char value) {
            return Arrays.stream(Token.values())
                    .map(token -> token.value)
                    .collect(Collectors.toSet())
                    .contains(value);
        }
    }

    public JsonParser(String json) {
        this.json = json.replaceAll("\\s", ""); // удаление всех whitespaces, \n, \r, ...;
        this.jsonBuffer = new JsonOffsetBuffer(this.json.toCharArray(), 0);
    }

    public Map<String, AbstractJsonElement> parseJson() throws RecursionDepthException {
        checkNotNull("json", json);
        parseJsonElement();
        return jsonMap;
    }

    private AbstractJsonElement parseJsonElement() throws RecursionDepthException {
        AbstractJsonElement element;

        jsonBuffer.increment();
        if (jsonBuffer.isEndOfBuffer()) {
            return null;
        }

        String key = getJsonElementKey();
        jsonBuffer.shift(key.length());
        jsonBuffer.increment();
        char currentChar = jsonBuffer.getChar();

        if (currentChar == Token.START_OBJECT_TOKEN.value) {
            element = parseJsonObject(key);
        } else if (currentChar == Token.START_ARRAY_TOKEN.value) {
            if (!Token.isToken(jsonBuffer.charAt(1))) {
                isPrimitiveArray = true;
            }
            element = parseJsonArray(key);
        } else {
            element = parseJsonNode(key);
        }

        if (!inRecursion) {
            key = key.replaceAll("\"", "");
            jsonMap.put(key, element);
        }

        return element;
    }

    private String getJsonElementKey() {
        return json.substring(jsonBuffer.pc, json.indexOf(Token.COLON_TOKEN.value, jsonBuffer.pc));
    }

    private JsonObject parseJsonObject(String key) throws RecursionDepthException {
        Set<AbstractJsonElement> elements = new HashSet<>();
        inRecursion = true;
        currentDepth++;
        checkRecursionDepth();
        while (!jsonBuffer.isEndOfBuffer()) {
            if (jsonBuffer.getChar() == Token.END_OBJECT_TOKEN.value) {
                break;
            }

            jsonBuffer.increment();
            elements.add(parseJsonElement());
        }
        currentDepth--;
        inRecursion = false;

        if (elements.isEmpty()) {
            throw new IllegalArgumentException("Set of elements is null");
        }

        return new JsonObject(key, elements);
    }

    private JsonArray parseJsonArray(String key) throws RecursionDepthException {
        List<AbstractJsonElement> list = new ArrayList<>();

        inRecursion = true;
        currentDepth++;
        checkRecursionDepth();
        while (!jsonBuffer.isEndOfBuffer()) {
            if (jsonBuffer.getChar() == Token.END_ARRAY_TOKEN.value) {
                break;
            }
            jsonBuffer.increment();
            // в элементы json массива обычно не проставляют ключи, поэтому key=null
            list.add(parseJsonNode(null));
        }
        inRecursion = false;
        isPrimitiveArray = false;
        currentDepth--;

        if (list.isEmpty()) {
            throw new IllegalArgumentException("List of elements is null");
        }

        return new JsonArray(key, list);
    }

    private JsonNode parseJsonNode(String key) throws RecursionDepthException {
        int index = json.indexOf(Token.COMMA_TOKEN.value, jsonBuffer.pc);
        if (isPrimitiveArray && index == -1) {
            index = json.indexOf(Token.END_ARRAY_TOKEN.value, jsonBuffer.pc);
        }

        if (index == -1) {
            index = json.indexOf(Token.END_OBJECT_TOKEN.value, jsonBuffer.pc);
        }

        String value = json.substring(jsonBuffer.pc, index);
        jsonBuffer.shift(value.length());

        if (!jsonBuffer.isEndOfBuffer() && !isPrimitiveArray) {
            parseJsonElement();
        }
        return new JsonNode(key, value);
    }

    private void checkRecursionDepth() throws RecursionDepthException {
        if (currentDepth >= MAX_RECURSION_DEPTH) {
            throw new RecursionDepthException("Max recursion depth is exceed " + "currentDepth: " + currentDepth);
        }
    }
}
