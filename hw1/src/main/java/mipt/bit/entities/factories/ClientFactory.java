package mipt.bit.entities.factories;

import mipt.bit.entities.clients.Client;
import mipt.bit.entities.enums.ClientType;
import mipt.bit.utils.exceptions.WrongClientTypeException;
import mipt.bit.json.elements.JsonElement;

import java.util.Map;

public interface ClientFactory {
    String typeFieldName = "clientType";

    default Client create(Map<String, JsonElement> jsonMap, ClientType clientType) throws WrongClientTypeException {
        return null;
    }

    default Client create(Map<String, JsonElement> jsonMap) throws WrongClientTypeException  {
        return null;
    }
}
