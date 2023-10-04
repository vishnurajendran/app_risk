package mapShow;

import entity.RiskMap;
import mapEditer.MapLoader;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Unit test for MapViewer
 */
class MapViewerTest {

    /**
     * Unit test to ensure that the RiskMap instance is created successfully.
     * Test cases:
     * 1. Check if RiskMap instance is not null.
     */
    @Test
    void testCreateRiskMap() {
        RiskMap riskMap = MapViewer.createRiskMap();
        assertNotNull(riskMap);
    }
    
    /**
     * Unit test for a map with a small number of countries and continents.
     * Test case:
     * 1. Check if the application works correctly with a small map.
     */
    @Test
    void testSmallMapFromFile() {
        MapLoader mapLoader = new MapLoader();
        mapLoader.loadMap("testResources/test.map");
        RiskMap smallMap = mapLoader.getMap();

        MapViewer.RiskMapPanel riskMapPanel = new MapViewer.RiskMapPanel(smallMap);
        assertNotNull(riskMapPanel);
    }

    /**
     * Unit test for a map with a large number of countries and continents.
     * Test case:
     * 1. Check if the application works correctly with a large map.
     */
    @Test
    void testLargeMapFromFile() {
        MapLoader mapLoader = new MapLoader();
        mapLoader.loadMap("testResources/largeMap.map");
        RiskMap largeMap = mapLoader.getMap();

        MapViewer.RiskMapPanel riskMapPanel = new MapViewer.RiskMapPanel(largeMap);
        assertNotNull(riskMapPanel);
    }

}




