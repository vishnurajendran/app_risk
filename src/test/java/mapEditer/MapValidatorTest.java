package mapEditer;

import entity.Continent;
import entity.Country;
import entity.RiskMap;
import entity.MapLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit test for MapValidator
 */
class MapValidatorTest {

    RiskMap d_riskMap1;

    @BeforeEach
    void setUp() {

    }


    /**
     * Test cases:
     * 1. Map Object is null.(due to some internal errors).
     */
    @Test
    void testForNullMap() {
        RiskMap l_nullMap = null;
        assertFalse(MapValidator.validateMap(l_nullMap));
    }

    /**
     * Test cases:
     * 2. Map file is empty.(due to reading/loading issue or actually empty).
     */
    @Test
    void testForEmptyMap() {
        MapLoader.loadMap("");
        RiskMap l_emptyMap = MapLoader.getMap();
        assertFalse(MapValidator.validateMap(l_emptyMap));
    }

    /**
     * Test cases:
     * 3. Valid connected map.
     * The map detail is in the "testResources/ValidTestMap.map".
     *
     * @see <a href="file:testResources/ValidTestMap.map"></a>
     */
    @Test
    void testForValidMap() {
        MapLoader.loadMap("testResources/ValidTestMap.map");
        RiskMap l_map = MapLoader.getMap();
        assertTrue(MapValidator.validateMap(l_map));
    }

    /**
     * Negative Test cases:
     * 4. InValid map- Disconnected graph
     * The map detail is in the "testResources/largemap.map".
     *
     * @see <a href="file:testResources/largemap.map"></a>
     */
    @Test
    void testForInvalidMap() {
        MapLoader.loadMap("testResources/largemap.map");
        RiskMap l_map2 = MapLoader.getMap();
        assertFalse(MapValidator.validateMap(l_map2));
    }

    /**
     * Negative Test cases:
     * 5. InValid map -Continent is not a connected graph
     * The map detail is in the "testResources/InvalidDisconnectedContinent.map".
     *
     * @see <a href="file:testResources/InvalidDisconnectedContinent.map"></a>
     */
    @Test
    void testForDisconnectedContinent() {
        MapLoader.loadMap("testResources/InvalidDisconnectedContinent.map");
        RiskMap l_map2 = MapLoader.getMap();
        assertFalse(MapValidator.validateMap(l_map2));
    }

    /**
     * Negative Test cases:
     * 6. InValid map -Duplicate Country
     * The map detail is in the "testResources/ValidTestMap.map".
     *
     * @see <a href="file:testResources/ValidTestMap.map"></a>
     */
    @Test
    void testForDuplicateCountry() {
        MapLoader.loadMap("testResources/ValidTestMap.map");
        RiskMap l_map2 = MapLoader.getMap();
        Continent l_continent = l_map2.getContinentById(1);
        Country l_country = l_map2.getCountryById(10);
        l_continent.addCountry(l_country);
        assertFalse(MapValidator.validateMap(l_map2));
    }

}