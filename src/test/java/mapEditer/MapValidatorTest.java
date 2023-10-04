package mapEditer;

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

    MapLoader d_mapLoader;

    RiskMap d_riskMap1;

    /**
     * Set up the mapLoader before testing
     */
    @BeforeEach
    void setUp() {
        d_mapLoader = new MapLoader();
    }


    /**
     * Unit test to validate that map is a connected graph
     * Uses a map loader to load test map file.
     * Test cases:
     * 1. Map Object is null.(due to some internal errors).
     * 2. Map file is empty.(due to reading/loading issue or actually empty).
     * 3. Valid connected map.
     * The map detail is in the "testResources/ValidTestMap.map".
     *
     * @see <a href="file:testResources/ValidTestMap.map"></a>
     */
    @Test
    void testIsMapValid() {
        RiskMap l_nullMap = null;
        assertFalse(MapValidator.validateMap(l_nullMap));

        d_mapLoader.loadMap("");
        RiskMap l_emptyMap = d_mapLoader.getMap();
        assertFalse(MapValidator.validateMap(l_emptyMap));

        d_mapLoader.loadMap("testResources/ValidTestMap.map");
        RiskMap l_map = d_mapLoader.getMap();
        assertTrue(MapValidator.validateMap(l_map));

    }

    /**
     * Placeholder test function to test individual cases.
     */
    @Test
    void testMap() {
        d_mapLoader.loadMap("testResources/test.map");
        RiskMap l_map2 = d_mapLoader.getMap();
        assertTrue(MapValidator.validateMap(l_map2));
    }

}