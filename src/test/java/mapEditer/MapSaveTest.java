package mapEditer;

import entity.RiskMap;
import entity.MapLoader;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class MapSaveTest {

    private final String TEST_MAP_PATH = "testResources/ValidTestMap.map";
    private final String TEMP_MAP_PATH = "tempmap.map";

    /**
     * Test the map save functionality,
     * we check if the file us saved correctly
     * converting the RiskMap by comparing it with
     * the original file.
     */
    @Test
    void testSaveMapFile() {
        MapLoader l_loader = new MapLoader();
        l_loader.loadMap(TEST_MAP_PATH);
        RiskMap l_testMap = l_loader.getMap();

        File l_file = new File(TEMP_MAP_PATH);
        MapSave.saveMapFile(l_testMap, l_file);
        l_loader = new MapLoader();
        //check if you can load the map
        assertTrue(l_loader.loadMap(TEMP_MAP_PATH));
        assertTrue(MapValidator.validateMap(l_loader.getMap()));

        l_file.delete();
    }
}