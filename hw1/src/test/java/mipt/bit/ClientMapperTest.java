package mipt.bit;

import mipt.bit.entities.clients.Client;
import mipt.bit.entities.clients.Holding;
import mipt.bit.entities.clients.Individual;
import mipt.bit.entities.clients.LegalEntity;
import mipt.bit.entities.factories.exceptions.WrongClientTypeException;
import mipt.bit.json.JsonParser;
import mipt.bit.json.elements.AbstractJsonElement;
import mipt.bit.json.exceptions.RecursionDepthException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Map;

public class ClientMapperTest {
    private final static String jsonLegalEntity = "{\n" +
            "   \"name\": \"ОООМатрешка\",\n" +
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

    private final LegalEntity legalEntity =
            new LegalEntity(
                    "ОООМатрешка",
                    1234567890
            );

    private final Individual individual = new Individual("Иван", "Иванов", "123456789012");

    private final Holding holding = new Holding("Холдинг", 1234567891, Arrays.asList(1234567890, 1234567890, 1234567890));

    @Test
    public void test001_firstApproach_LegalEntity_positive() throws RecursionDepthException, WrongClientTypeException {
        LegalEntity legalEntity = (LegalEntity) getFirstApproachResult(jsonLegalEntity);
        Assertions.assertEquals(this.legalEntity, legalEntity);
    }

    @Test
    public void test002_firstApproach_Holding_positive() throws RecursionDepthException, WrongClientTypeException {
        Holding holding = (Holding) getFirstApproachResult(jsonHolding);
        Assertions.assertEquals(this.holding, holding);
    }

    @Test
    public void test003_firstApproach_Individual_positive() throws RecursionDepthException, WrongClientTypeException {
        Individual individual = (Individual) getFirstApproachResult(jsonIndividual);
        Assertions.assertEquals(this.individual, individual);
    }

    @Test
    public void test004_secondApproach_LegalEntity_positive() throws WrongClientTypeException, RecursionDepthException {
        LegalEntity legalEntity = (LegalEntity) getSecondApproachResult(jsonLegalEntity);
        Assertions.assertEquals(this.legalEntity, legalEntity);
    }

    @Test
    public void test005_secondApproach_Holding_positive() throws RecursionDepthException, WrongClientTypeException {
        Holding holding = (Holding) getSecondApproachResult(jsonHolding);
        Assertions.assertEquals(this.holding, holding);
    }

    @Test
    public void test006_secondApproach_Individual_positive() throws RecursionDepthException, WrongClientTypeException {
        Individual individual = (Individual) getSecondApproachResult(jsonIndividual);
        Assertions.assertEquals(this.individual, individual);
    }

    private Map<String, AbstractJsonElement> getJsonMap(String json) throws RecursionDepthException {
        JsonParser parser = new JsonParser(json);
        return parser.parseJson();
    }

    private Client getFirstApproachResult(String json) throws RecursionDepthException, WrongClientTypeException {
        Map<String, AbstractJsonElement> jsonMap = getJsonMap(json);
        return ClientMapper.firstApproach(jsonMap);
    }

    private Client getSecondApproachResult(String json) throws RecursionDepthException, WrongClientTypeException {
        Map<String, AbstractJsonElement> jsonMap = getJsonMap(json);
        return ClientMapper.secondApproach(jsonMap);
    }
}
