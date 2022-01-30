package mipt.bit.entities.factories;

import mipt.bit.entities.clients.Individual;
import mipt.bit.entities.enums.ClientType;
import mipt.bit.utils.exceptions.WrongClientTypeException;
import mipt.bit.json.elements.JsonElement;
import mipt.bit.json.elements.JsonNode;

import java.util.Map;

import static mipt.bit.utils.ClientUtils.checkClientType;

public class ClientIndividualFactory implements ClientFactory {
    @Override
    public Individual create(Map<String, JsonElement> jsonMap) throws WrongClientTypeException {
        JsonNode typeNode = (JsonNode) jsonMap.get(typeFieldName);
        checkClientType(typeNode, ClientType.INDIVIDUAL.getValue());

        return Individual.createIndividual(
                ((JsonNode)jsonMap.get("name")).getValue(),
                ((JsonNode)jsonMap.get("surname")).getValue(),
                ((JsonNode) jsonMap.get("inn")).getValue()
        );
    }
}
