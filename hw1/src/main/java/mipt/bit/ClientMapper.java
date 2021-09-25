package mipt.bit;

import mipt.bit.entities.enums.ClientType;
import mipt.bit.entities.factories.ClientFactory;
import mipt.bit.entities.factories.exceptions.WrongClientTypeException;
import mipt.bit.json.elements.JsonElement;
import mipt.bit.json.elements.JsonNode;

import java.util.Map;

public class ClientMapper {
    private final static String typeFieldName = "clientType";

    public static <T> T firstApproach(Map<String, JsonElement> jsonMap, Class<T> clazz) throws WrongClientTypeException {
        String type = ((JsonNode)jsonMap.get(typeFieldName)).getValue();
        switch (type) {
            case "INDIVIDUAL":
                return clazz.cast(ClientFactory.createIndividual(jsonMap));
            case "HOLDING":
                return clazz.cast(ClientFactory.createHolding(jsonMap));
            case "LEGAL_ENTITY":
                return clazz.cast(ClientFactory.createLegalEntity(jsonMap));
            default:
                throw new WrongClientTypeException("Illegal client type" + "type: " + type);
        }
    }

    public static <T> T secondApproach(Map<String, JsonElement> jsonMap, Class<T> clazz) throws WrongClientTypeException {
        String type = ((JsonNode)jsonMap.get(typeFieldName)).getValue();
        return clazz.cast(ClientType.valueOf(type).createClient(jsonMap));
    }
}
