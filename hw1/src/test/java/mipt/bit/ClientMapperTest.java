package mipt.bit;

import mipt.bit.entities.clients.Holding;
import mipt.bit.entities.clients.Individual;
import mipt.bit.entities.clients.LegalEntity;
import mipt.bit.utils.exceptions.WrongClientTypeException;
import mipt.bit.json.JsonParser;
import mipt.bit.json.elements.JsonElement;
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

    private final static String jsonIndividualWrongInn = "{\n" +
            "   \"name\": \"Иван\",\n" +
            "   \"inn\": 123456789012111,\n" +
            "   \"clientType\": \"INDIVIDUAL\",\n" +
            "   \"surname\": \"Иванов\"\n" +
            "}";

    private final static String jsonIndividualWrongFormat = "{\n" +
            "   \"name\": \"Иван\",\n" +
            "   \"inn\": 123456789012,\n" +
            "   \"clientType\": \"INDIVIDUAL\"\n" +
            "}";

    private final LegalEntity legalEntity = new LegalEntity("ОООМатрешка", 1234567890);
    private final Individual individual = new Individual("Иван", "Иванов", "123456789012");
    private final Holding holding = new Holding("Холдинг", 1234567891, Arrays.asList(1234567890, 1234567890, 1234567890));

    @Test
    public void firstApproachLegalEntityPositive() throws RecursionDepthException, WrongClientTypeException {
        LegalEntity legalEntity = getFirstApproachResult(jsonLegalEntity, LegalEntity.class);
        Assertions.assertEquals(this.legalEntity, legalEntity);
    }

    @Test
    public void firstApproachHoldingPositive() throws RecursionDepthException, WrongClientTypeException {
        Holding holding = getFirstApproachResult(jsonHolding, Holding.class);
        Assertions.assertEquals(this.holding, holding);
    }

    @Test
    public void firstApproachIndividualPositive() throws RecursionDepthException, WrongClientTypeException {
        Individual individual = getFirstApproachResult(jsonIndividual, Individual.class);
        Assertions.assertEquals(this.individual, individual);
    }

    @Test
    public void secondApproachLegalEntityPositive() throws WrongClientTypeException, RecursionDepthException {
        LegalEntity legalEntity = getSecondApproachResult(jsonLegalEntity, LegalEntity.class);
        Assertions.assertEquals(this.legalEntity, legalEntity);
    }

    @Test
    public void secondApproachHoldingPositive() throws RecursionDepthException, WrongClientTypeException {
        Holding holding = getSecondApproachResult(jsonHolding, Holding.class);
        Assertions.assertEquals(this.holding, holding);
    }

    @Test
    public void secondApproachIndividualPositive() throws RecursionDepthException, WrongClientTypeException {
        Individual individual = getSecondApproachResult(jsonIndividual, Individual.class);
        Assertions.assertEquals(this.individual, individual);
    }

    @Test
    public void secondApproachWrongInn() throws RecursionDepthException, WrongClientTypeException {
        try {
            Individual individual = getSecondApproachResult(jsonIndividualWrongInn, Individual.class);
        } catch (IllegalArgumentException ex) {
            Assertions.assertEquals(ex.getMessage(), "Illegal inn for INDIVIDUAL inn: 123456789012111");
        }
    }

    @Test
    public void secondApproachWrongFormat() throws RecursionDepthException, WrongClientTypeException {
        try {
            Individual individual = getSecondApproachResult(jsonIndividualWrongFormat, Individual.class);
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }

    private Map<String, JsonElement> getJsonMap(String json) throws RecursionDepthException {
        JsonParser parser = new JsonParser(json);
        return parser.parseJson();
    }

    private <T> T getFirstApproachResult(String json, Class<T> clazz) throws RecursionDepthException, WrongClientTypeException {
        Map<String, JsonElement> jsonMap = getJsonMap(json);
        return ClientMapper.firstApproach(jsonMap, clazz);
    }

    private <T> T getSecondApproachResult(String json, Class<T> clazz) throws RecursionDepthException, WrongClientTypeException {
        Map<String, JsonElement> jsonMap = getJsonMap(json);
        return ClientMapper.secondApproach(jsonMap, clazz);
    }
}
