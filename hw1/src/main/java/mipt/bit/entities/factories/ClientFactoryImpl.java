package mipt.bit.entities.factories;

import mipt.bit.entities.clients.Client;
import mipt.bit.entities.enums.ClientType;
import mipt.bit.utils.exceptions.WrongClientTypeException;
import mipt.bit.json.elements.JsonElement;

import java.util.Map;

public class ClientFactoryImpl implements ClientFactory {
    @Override
    public Client create(Map<String, JsonElement> jsonMap, ClientType clientType) throws WrongClientTypeException {
       switch (clientType) {
           case INDIVIDUAL:
               return new ClientIndividualFactory().create(jsonMap);
           case HOLDING:
               return new ClientHoldingFactory().create(jsonMap);
           case LEGAL_ENTITY:
               return new ClientLegalEntityFactory().create(jsonMap);
           default:
               throw new WrongClientTypeException("Wrong client type is specified");
       }
    }
}

