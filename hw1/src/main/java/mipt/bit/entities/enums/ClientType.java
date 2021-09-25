package mipt.bit.entities.enums;

import mipt.bit.entities.clients.Client;
import mipt.bit.entities.clients.Holding;
import mipt.bit.entities.clients.Individual;
import mipt.bit.entities.clients.LegalEntity;
import mipt.bit.entities.factories.ClientFactory;
import mipt.bit.entities.factories.exceptions.WrongClientTypeException;
import mipt.bit.json.elements.JsonElement;

import java.util.Map;

public enum ClientType {
    INDIVIDUAL("INDIVIDUAL") {
        @Override
        public Individual createClient(Map<String, JsonElement> jsonMap) throws WrongClientTypeException {
            return ClientFactory.createIndividual(jsonMap);
        }
    },
    LEGAL_ENTITY("LEGAL_ENTITY") {
        @Override
        public LegalEntity createClient(Map<String, JsonElement> jsonMap) throws WrongClientTypeException {
            return ClientFactory.createLegalEntity(jsonMap);
        }
    },
    HOLDING("HOLDING") {
        @Override
        public Holding createClient(Map<String, JsonElement> jsonMap) throws WrongClientTypeException {
            return ClientFactory.createHolding(jsonMap);
        }
    };

    private final String value;

    ClientType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public abstract Client createClient(Map<String, JsonElement> jsonMap) throws WrongClientTypeException;
}
