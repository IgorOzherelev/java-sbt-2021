package mipt.bit.utils;

import mipt.bit.entities.enums.ClientType;
import mipt.bit.utils.exceptions.WrongClientTypeException;
import mipt.bit.json.elements.JsonNode;

import static mipt.bit.utils.Utils.checkNotNull;

public class ClientUtils {
    public static void checkClientType(JsonNode actualNode, String expected) throws WrongClientTypeException {
        String actual = actualNode.getValue();
        checkNotNull(actualNode);
        if (!actual.equals(expected)) {
            throw new WrongClientTypeException("Wrong client type actual: " + actual + " expected: " + expected);
        }
    }

    public static void checkInn(String inn, String typeInn) {
        int innStringLength = inn.length();
        if (inn.charAt(0) == '-') {
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
