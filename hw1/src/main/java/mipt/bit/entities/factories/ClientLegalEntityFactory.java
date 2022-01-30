package mipt.bit.entities.factories;

import mipt.bit.entities.clients.LegalEntity;
import mipt.bit.entities.enums.ClientType;
import mipt.bit.utils.exceptions.WrongClientTypeException;
import mipt.bit.json.elements.JsonElement;
import mipt.bit.json.elements.JsonNode;

import java.util.Map;

import static mipt.bit.utils.ClientUtils.checkClientType;

public class ClientLegalEntityFactory implements ClientFactory {
    @Override
    public LegalEntity create(Map<String, JsonElement> jsonMap) throws WrongClientTypeException {
        JsonNode typeNode = (JsonNode) jsonMap.get(typeFieldName);
        checkClientType(typeNode, ClientType.LEGAL_ENTITY.getValue());

        return LegalEntity.createLegalEntity(
                ((JsonNode)jsonMap.get("name")).getValue(),
                Integer.parseInt(((JsonNode)jsonMap.get("inn")).getValue())
        );
    }
}
