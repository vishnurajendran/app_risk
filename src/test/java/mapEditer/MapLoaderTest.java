package mapEditer;

import entity.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for map loader
 */
class MapLoaderTest {
    MapLoader d_loader;
    /**
     * Set up the mapLoader before testing
     * The map detail is in the "testMap.map".
     * The map file contains some country and have a name of "GreatMap ^_^"
     * @see <a href="file:testMap.map"></a>
     */
    @BeforeEach
    void setUp() {
        d_loader=new MapLoader();
    }


    /**
     * Unit test of loadMap function
     * The map should have a title of "GreatMap ^_^" after loading.
     */
    @Test
    void loadMap() {
        assertNull(d_loader.getMap().getName());
        d_loader.loadMap("testMap.map");
        assertEquals("GreatMap ^_^",d_loader.getMap().getName());

    }

    /**
     * Unit test of getMap function.
     * The map should have a list of countries ids from 1-10
     */
    @Test
    void getMap() {
        assertNull(d_loader.getMap().getName());
        d_loader.loadMap("testMap.map");
        Map l_map=d_loader.getMap();
        assertEquals(10,l_map.getCountryIds().size());
        assertTrue(l_map.getCountryIds().contains(10));
        assertFalse(l_map.getCountryIds().contains(0));
        assertFalse(l_map.getCountryIds().contains(11));
    }
}