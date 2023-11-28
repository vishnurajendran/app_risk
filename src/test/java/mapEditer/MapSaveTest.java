package mapEditer;

import common.FileIO.FileIO;
import entity.RiskMap;
import entity.MapLoader;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class MapSaveTest {

    private final String TEST_MAP_PATH = "testResources/ValidTestMap.map";
    private final String TEMP_MAP_PATH = "tempmap.map";
    private final String TEST_MAP_PATH_C = "testResources/testConquest.map";
    private final String TEMP_MAP_PATH_C = "tempmap.map";

    /**
     * Test the map save functionality for domination map,
     * we check if the file us saved correctly
     * converting the RiskMap by comparing it with
     * the original file.
     */
    @Test
    void testSaveMapFileDomination() {
        MapLoader l_loader = new MapLoader();
        l_loader.loadMap(TEST_MAP_PATH);
        RiskMap l_testMap = l_loader.getMap();

        MapSave.saveMapFile(l_testMap, TEMP_MAP_PATH,false);
        l_loader = new MapLoader();
        //check if you can load the map
        assertTrue(l_loader.loadMap(TEMP_MAP_PATH));
        assertTrue(MapValidator.validateMap(l_loader.getMap()));

        FileIO.removeFile(TEMP_MAP_PATH);
    }

    /**
     * Test the map save functionality for conquestMap,
     * we check if the file us saved correctly
     * converting the RiskMap by comparing it with
     * the original file.
     */
    @Test
    void testSaveMapFileConquest() {
        MapLoader l_loader = new MapLoader();
        l_loader.loadMap(TEST_MAP_PATH_C);
        RiskMap l_testMap = l_loader.getMap();

        MapSave.saveMapFile(l_testMap, TEMP_MAP_PATH_C,true);
        l_loader = new MapLoader();
        //check if you can load the map
        assertTrue(l_loader.loadMap(TEMP_MAP_PATH_C));
        assertTrue(MapValidator.validateMap(l_loader.getMap()));

        FileIO.removeFile(TEMP_MAP_PATH_C);
    }
}