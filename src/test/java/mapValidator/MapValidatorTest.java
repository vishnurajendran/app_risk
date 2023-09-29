package mapValidator;

import entity.Country;
import entity.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for MapValidator
 */
class MapValidatorTest {
    Map d_map;

    @BeforeEach
    void setUp() {
        d_map = new Map("testMap1");
        Country l_c1 = new Country(1, "siberia" , 1 ,0 , 0);
        Country l_c2 = new Country(2, "canada" , 1 ,1 , 1);
        d_map.addCountry(l_c1);
        d_map.addCountry(l_c2);
        String l_borders[] = {"2"};
        d_map.addBorders(1,l_borders);
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