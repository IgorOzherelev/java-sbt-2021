package mipt.bit.entities.enums;

import mipt.bit.entities.clients.Client;
import mipt.bit.entities.clients.Holding;
import mipt.bit.entities.clients.Individual;
import mipt.bit.entities.clients.LegalEntity;
import mipt.bit.entities.factories.ClientFactory;
import mipt.bit.entities.factories.ClientFactoryImpl;
import mipt.bit.utils.exceptions.WrongClientTypeException;
import mipt.bit.json.elements.JsonElement;

import java.util.Map;

public enum ClientType {
    INDIVIDUAL("INDIVIDUAL") {
        @Override
        public Individual createClient(Map<String, JsonElement> jsonMap) throws WrongClientTypeException {
            return (Individual) clientFactory.create(jsonMap, INDIVIDUAL);
        }
    },
    LEGAL_ENTITY("LEGAL_ENTITY") {
        @Override
        public LegalEntity createClient(Map<String, JsonElement> jsonMap) throws WrongClientTypeException {
            return (LegalEntity) clientFactory.create(jsonMap, LEGAL_ENTITY);
        }
    },
    HOLDING("HOLDING") {
        @Override
        public Holding createClient(Map<String, JsonElement> jsonMap) throws WrongClientTypeException {
            return (Holding) clientFactory.create(jsonMap, HOLDING);
        }
    };

    private final static ClientFactory clientFactory = new ClientFactoryImpl();
    private final String value;

    ClientType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public abstract Client createClient(Map<String, JsonElement> jsonMap) throws WrongClientTypeException;
}
