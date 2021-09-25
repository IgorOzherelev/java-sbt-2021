package mipt.bit;

import mipt.bit.entities.clients.Holding;
import mipt.bit.entities.clients.Individual;
import mipt.bit.entities.clients.LegalEntity;
import mipt.bit.entities.factories.exceptions.WrongClientTypeException;
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
    public void test001_ClientMapperTest_firstApproach_LegalEntity_positive() throws RecursionDepthException, WrongClientTypeException {
        LegalEntity legalEntity = getFirstApproachResult(jsonLegalEntity, LegalEntity.class);
        Assertions.assertEquals(this.legalEntity, legalEntity);
    }

    @Test
    public void test002_ClientMapperTest_firstApproach_Holding_positive() throws RecursionDepthException, WrongClientTypeException {
        Holding holding = getFirstApproachResult(jsonHolding, Holding.class);
        Assertions.assertEquals(this.holding, holding);
    }

    @Test
    public void test003_ClientMapperTest_firstApproach_Individual_positive() throws RecursionDepthException, WrongClientTypeException {
        Individual individual = getFirstApproachResult(jsonIndividual, Individual.class);
        Assertions.assertEquals(this.individual, individual);
    }

    @Test
    public void test004_ClientMapperTest_secondApproach_LegalEntity_positive() throws WrongClientTypeException, RecursionDepthException {
        LegalEntity legalEntity = getSecondApproachResult(jsonLegalEntity, LegalEntity.class);
        Assertions.assertEquals(this.legalEntity, legalEntity);
    }

    @Test
    public void test005_ClientMapperTest_secondApproach_Holding_positive() throws RecursionDepthException, WrongClientTypeException {
        Holding holding = getSecondApproachResult(jsonHolding, Holding.class);
        Assertions.assertEquals(this.holding, holding);
    }

    @Test
    public void test006_ClientMapperTest_secondApproach_Individual_positive() throws RecursionDepthException, WrongClientTypeException {
        Individual individual = getSecondApproachResult(jsonIndividual, Individual.class);
        Assertions.assertEquals(this.individual, individual);
    }

    @Test
    public void test007_ClientMapperTest_wrongInn() throws RecursionDepthException, WrongClientTypeException {
        try {
            Individual individual = getSecondApproachResult(jsonIndividualWrongInn, Individual.class);
        } catch (IllegalArgumentException ex) {
            Assertions.assertEquals(ex.getMessage(), "Illegal inn for INDIVIDUAL inn: 123456789012111");
        }
    }

    @Test
    public void test008_ClientMapperTest_wrongFormat() throws RecursionDepthException, WrongClientTypeException {
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
