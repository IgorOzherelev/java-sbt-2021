package mipt.bit.entities.factories;

import mipt.bit.entities.clients.Holding;
import mipt.bit.entities.enums.ClientType;
import mipt.bit.utils.exceptions.WrongClientTypeException;
import mipt.bit.json.elements.JsonArray;
import mipt.bit.json.elements.JsonElement;
import mipt.bit.json.elements.JsonNode;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static mipt.bit.utils.ClientUtils.checkClientType;

public class ClientHoldingFactory implements ClientFactory {
    @Override
    public Holding create(Map<String, JsonElement> jsonMap) throws WrongClientTypeException {
        JsonNode typeNode = (JsonNode) jsonMap.get(typeFieldName);
        List<JsonElement> list = ((JsonArray)jsonMap.get("companiesInn")).getList();
        List<Integer> companiesInn = list.stream().map(elem -> {
            JsonNode nodeElem = (JsonNode) elem;
            return Integer.valueOf(nodeElem.getValue());
        }).collect(Collectors.toList());

        checkClientType(typeNode, ClientType.HOLDING.getValue());
        return Holding.createHolding(
                ((JsonNode)jsonMap.get("name")).getValue(),
                Integer.parseInt(((JsonNode)jsonMap.get("inn")).getValue()),
                companiesInn
        );
    }
}
