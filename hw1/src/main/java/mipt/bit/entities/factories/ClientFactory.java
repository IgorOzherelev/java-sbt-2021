package mipt.bit.entities.factories;

import mipt.bit.entities.clients.Holding;
import mipt.bit.entities.clients.Individual;
import mipt.bit.entities.clients.LegalEntity;
import mipt.bit.entities.enums.ClientType;
import mipt.bit.entities.factories.exceptions.WrongClientTypeException;
import mipt.bit.json.elements.AbstractJsonElement;
import mipt.bit.json.elements.JsonArray;
import mipt.bit.json.elements.JsonNode;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static mipt.bit.utils.Utils.checkNotNull;

public class ClientFactory {
    private final static String typeFieldName = "clientType";

    public static Individual createIndividual(Map<String, AbstractJsonElement> jsonMap) throws WrongClientTypeException {
        JsonNode typeNode = (JsonNode) jsonMap.get(typeFieldName);
        checkClientType(typeNode, ClientType.INDIVIDUAL.getValue());

        return createIndividual(
                ((JsonNode)jsonMap.get("name")).getValue(),
                ((JsonNode)jsonMap.get("surname")).getValue(),
                Integer.parseInt(((JsonNode) jsonMap.get("inn")).getValue())
        );
    }

    public static Individual createIndividual(String name, String surname, int inn) {
        checkInn(inn, ClientType.INDIVIDUAL.getValue());
        return new Individual(name, surname, inn);
    }

    public static Holding createHolding(Map<String, AbstractJsonElement> jsonMap) throws WrongClientTypeException {
        JsonNode typeNode = (JsonNode) jsonMap.get(typeFieldName);
        List<AbstractJsonElement> list = ((JsonArray)jsonMap.get("companiesInn")).getList();
        List<Integer> companiesInn = list.stream().map(elem -> {
            JsonNode nodeElem = (JsonNode) elem;
            return Integer.valueOf(nodeElem.getValue());
        }).collect(Collectors.toList());

        checkClientType(typeNode, ClientType.HOLDING.getValue());
        return createHolding(
                ((JsonNode)jsonMap.get("name")).getValue(),
                Integer.parseInt(((JsonNode)jsonMap.get("inn")).getValue()),
                companiesInn
        );
    }

    public static Holding createHolding(String name, int inn, List<Integer> companiesInn) {
        checkInn(inn, ClientType.HOLDING.getValue());
        companiesInn.forEach(inn_ -> checkInn(inn_, ClientType.HOLDING.getValue()));
        return new Holding(name, inn, companiesInn);
    }

    public static LegalEntity createLegalEntity(Map<String, AbstractJsonElement> jsonMap) throws WrongClientTypeException {
        JsonNode typeNode = (JsonNode) jsonMap.get(typeFieldName);
        checkClientType(typeNode, ClientType.LEGAL_ENTITY.getValue());


        return new LegalEntity(
                ((JsonNode)jsonMap.get("name")).getValue(),
                Integer.parseInt(((JsonNode)jsonMap.get("inn")).getValue())
        );
    }

    public static LegalEntity createLegalEntity(String name, int inn) {
        checkInn(inn, ClientType.LEGAL_ENTITY.getValue());
        return new LegalEntity(name, inn);
    }

    private static void checkClientType(JsonNode actualNode, String expected) throws WrongClientTypeException {
        String actual = actualNode.getValue();
        checkNotNull(actualNode);
        if (!actual.equals(expected)) {
            throw new WrongClientTypeException("Wrong client type actual: " + actual + " expected: " + expected);
        }
    }

    private static void checkInn(int inn, String typeInn) {
        int innStringLength = String.valueOf(inn).length();
        if (inn < 0) {
            throw new IllegalArgumentException("Illegal inn < 0 " + "inn: " + inn);
        }

        if (typeInn.equals(ClientType.INDIVIDUAL.getValue())) {
            if (innStringLength != 12) {
                throw new IllegalArgumentException("Illegal inn for INDIVIDUAL " + "inn: " + inn);
            }
        }

        if (typeInn.equals(ClientType.LEGAL_ENTITY.getValue())
                || typeInn.equals(ClientType.HOLDING.getValue())) {
            if (innStringLength != 10) {
                throw new IllegalArgumentException("Illegal inn for LEGAL_ENTITY/HOLDING" + "inn: " + inn);
            }
        }
    }
}

