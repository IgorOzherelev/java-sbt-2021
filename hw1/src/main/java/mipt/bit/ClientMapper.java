package mipt.bit;

import mipt.bit.entities.enums.ClientType;
import mipt.bit.entities.factories.ClientFactory;
import mipt.bit.entities.factories.ClientFactoryImpl;
import mipt.bit.utils.exceptions.WrongClientTypeException;
import mipt.bit.json.elements.JsonElement;
import mipt.bit.json.elements.JsonNode;

import java.util.Map;

public class ClientMapper {
    private final static String typeFieldName = "clientType";
    private final static ClientFactory clientFactory = new ClientFactoryImpl();

    public static <T> T firstApproach(Map<String, JsonElement> jsonMap, Class<T> clazz) throws WrongClientTypeException {
        String type = ((JsonNode)jsonMap.get(typeFieldName)).getValue();
        switch (type) {
            case "INDIVIDUAL":
                return clazz.cast(clientFactory.create(jsonMap, ClientType.INDIVIDUAL));
            case "HOLDING":
                return clazz.cast(clientFactory.create(jsonMap, ClientType.HOLDING));
            case "LEGAL_ENTITY":
                return clazz.cast(clientFactory.create(jsonMap, ClientType.LEGAL_ENTITY));
            default:
                throw new WrongClientTypeException("Illegal client type" + "type: " + type);
        }
    }

    public static <T> T secondApproach(Map<String, JsonElement> jsonMap, Class<T> clazz) throws WrongClientTypeException {
        String type = ((JsonNode)jsonMap.get(typeFieldName)).getValue();
        return clazz.cast(ClientType.valueOf(type).createClient(jsonMap));
    }
}
