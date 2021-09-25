package mipt.bit;

import mipt.bit.entities.clients.Client;
import mipt.bit.entities.enums.ClientType;
import mipt.bit.entities.factories.ClientFactory;
import mipt.bit.entities.factories.exceptions.WrongClientTypeException;
import mipt.bit.json.elements.JsonElement;
import mipt.bit.json.elements.JsonNode;

import java.util.Map;

public class ClientMapper {
    private final static String typeFieldName = "clientType";

    public static Client firstApproach(Map<String, JsonElement> jsonMap) throws WrongClientTypeException {
        String type = ((JsonNode)jsonMap.get(typeFieldName)).getValue();
        switch (type) {
            case "INDIVIDUAL":
                return ClientFactory.createIndividual(jsonMap);
            case "HOLDING":
                return ClientFactory.createHolding(jsonMap);
            case "LEGAL_ENTITY":
                return ClientFactory.createLegalEntity(jsonMap);
            default:
                throw new IllegalArgumentException("Illegal client type" + "type: " + type);
        }
    }

    public static Client secondApproach(Map<String, JsonElement> jsonMap) throws WrongClientTypeException {
        String type = ((JsonNode)jsonMap.get(typeFieldName)).getValue();
        return ClientType.valueOf(type).createClient(jsonMap);
    }
}
