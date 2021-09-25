package mipt.bit;

import mipt.bit.entities.clients.Client;
import mipt.bit.entities.enums.ClientType;
import mipt.bit.entities.factories.ClientFactory;
import mipt.bit.entities.factories.exceptions.WrongClientTypeException;
import mipt.bit.json.*;
import mipt.bit.json.elements.AbstractJsonElement;
import mipt.bit.json.elements.JsonNode;
import mipt.bit.json.exceptions.RecursionDepthException;

import java.util.Map;

public class ClientMapper {
    private final static String typeFieldName = "clientType";

    private final static String jsonLegalEntity = "{\n" +
            "   \"name\": \"ООО Матрешка\",\n" +
            "   \"inn\": 1234567890,\n" +
            "   \"clientType\": \"LEGAL_ENTITY\"\n" +
            "}";

    private final static String jsonIndividual = "{\n" +
            "   \"name\": \"Иван\",\n" +
            "   \"inn\": 123456789012,\n" +
            "   \"clientType\": \"INDIVIDUAL\",\n" +
            "   \"surname\": \"Иванов\"\n" +
            "}";

    private final static String jsonHolding = "{\n" +
            "   \"name\": \"Холдинг\",\n" +
            "   \"inn\": 1234567891,\n" +
            "   \"clientType\": \"HOLDING\",\n" +
            " \"companiesInn\": [1234567890, 1234567890, 1234567890] \n" +
            "}";

    public static Client firstApproach(Map<String, AbstractJsonElement> jsonMap) throws WrongClientTypeException {
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

    public static Client secondApproach(Map<String, AbstractJsonElement> jsonMap) throws WrongClientTypeException {
        String type = ((JsonNode)jsonMap.get(typeFieldName)).getValue();
        return ClientType.valueOf(type).createClient(jsonMap);
    }

    public static void main(String[] args) throws RecursionDepthException, WrongClientTypeException {
//        JsonParser parser = new JsonParser(jsonLegalEntity);
        JsonParser parser = new JsonParser(jsonIndividual);
//        JsonParser parser = new JsonParser(jsonHolding);
        Map<String, AbstractJsonElement> jsonMap = parser.parseJson();

        System.out.println(secondApproach(jsonMap));
    }
}
