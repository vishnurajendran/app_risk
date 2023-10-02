package mapValidator;

import entity.Country;
import entity.RiskMap;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit test for MapValidator
 */
class MapValidatorTest {
    RiskMap d_riskMap;

    @BeforeEach
    void setUp() {
        d_riskMap = new RiskMap("testMap1");
        Country l_c1 = new Country(1, "siberia" , 1 ,0 , 0);
        Country l_c2 = new Country(2, "canada" , 1 ,1 , 1);
        d_riskMap.addCountry(l_c1);
        d_riskMap.addCountry(l_c2);
        String l_borders[] = {"2"};
        d_riskMap.addBorders(1,l_borders);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void isConnectedGraph() {
    }

    @Test
    void validateMap() {
    }
}