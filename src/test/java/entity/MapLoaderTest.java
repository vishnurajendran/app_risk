package entity;

import entity.MapLoader;
import entity.RiskMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for map loader
 */
class MapLoaderTest {


    /**
     * Set up the mapLoader before testing
     * The map detail is in the "testMap.map".
     * The map file contains some country and have a name of "GreatMap ^_^"
     *
     * @see <a href="file:testMap.map"></a>
     */
    @BeforeEach
    void setUp() {
        MapLoader.cleanUp();
    }


    /**
     * Unit test of loadMap function
     * The map should have a title of "GreatMap ^_^" after loading.
     */
    @Test
    void loadMap() {
        assertNull(MapLoader.getMap());
        MapLoader.loadMap("testResources/testMap.map");
        assertEquals("GreatMap ^_^", MapLoader.getMap().getName());

    }

    /**
     * Unit test of getMap function.
     * The map should have a list of countries ids from 1-10
     */
    @Test
    void getMap() {
        assertNull(MapLoader.getMap());
        MapLoader.loadMap("testResources/testMap.map");
        RiskMap l_Risk_map = MapLoader.getMap();
        assertEquals(10, l_Risk_map.getCountryIds().size());
        assertTrue(l_Risk_map.getCountryIds().contains(10));
        assertFalse(l_Risk_map.getCountryIds().contains(0));
        assertFalse(l_Risk_map.getCountryIds().contains(11));
        assertEquals("siberia", l_Risk_map.getCountryById(1).getName());
        assertEquals(4, l_Risk_map.getContinentIds().size());
        assertEquals("ameroki", l_Risk_map.getContinentById(1).getName());
    }
}