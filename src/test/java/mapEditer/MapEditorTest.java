package mapEditer;

import common.Command;
import entity.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test cases for all map editor operations.
 */
class MapEditorTest {
    MapEditor d_mapEditorTest;

    @BeforeEach
    void setUp() {
        d_mapEditorTest = new MapEditor();
    }

    /**
     * Test for editmap when loading an existing map.
     */
    @Test
    void testCmdEditMapFromFile() {
        d_mapEditorTest.submitCommand(Command.parseString("editmap testResources/WoW.map"));
        assertTrue(d_mapEditorTest.isMapEditorInitialised());
        assertFalse(d_mapEditorTest.hasQuit());
    }

    /**
     * Test for editmap when creating map from scratch.
     */
    @Test
    void testCmdEditMap() {
        d_mapEditorTest.submitCommand(Command.parseString("editmap"));
        assertTrue(d_mapEditorTest.isMapEditorInitialised());
        assertFalse(d_mapEditorTest.hasQuit());
    }

    /**
     * Negative Test for editmap when file doesnot exist.
     */
    @Test
    void testCmdEditMapInvalidFile() {
        d_mapEditorTest.submitCommand(Command.parseString("editmap 1.txt"));
        assertFalse(d_mapEditorTest.isMapEditorInitialised());
        assertTrue(d_mapEditorTest.hasQuit());
    }

    /**
     * Test for editcountry -add
     */
    @Test
    void testCmdEditCountryAdd() {
        d_mapEditorTest.submitCommand(Command.parseString("editmap testResources/ValidTestMap.map"));
        d_mapEditorTest.submitCommand(Command.parseString("editcountry -add 11 1"));
        assertTrue(d_mapEditorTest.d_map.hasCountry(1));
        assertFalse(d_mapEditorTest.hasQuit());
    }

    /**
     * Negative Test for editcountry -add
     */
    @Test
    void testCmdEditCountryAddInvalidArgs() {
        d_mapEditorTest.submitCommand(Command.parseString("editmap testResources/ValidTestMap.map"));
        d_mapEditorTest.submitCommand(Command.parseString("editcountry -add 11 100 20"));
        assertFalse(d_mapEditorTest.d_map.hasCountry(11));
        assertTrue(d_mapEditorTest.hasQuit());
    }

    /**
     * Test for editcountry -remove
     */
    @Test
    void testCmdEditCountryRemove() {
        d_mapEditorTest.submitCommand(Command.parseString("editmap testResources/ValidTestMap.map"));
        d_mapEditorTest.submitCommand(Command.parseString("editcountry -remove 1"));
        assertFalse(d_mapEditorTest.d_map.hasCountry(1));
        assertFalse(d_mapEditorTest.hasQuit());
    }

    /**
     * Test for editcontinent -remove
     * Test if all countries in the given continent are also removed.
     */
    @Test
    void testCmdEditContinentRemove() {
        d_mapEditorTest.submitCommand(Command.parseString("editmap testResources/ValidTestMap.map"));
        d_mapEditorTest.submitCommand(Command.parseString("editcontinent -remove 1"));
        assertFalse(d_mapEditorTest.d_map.hasContinent(1));
        assertFalse(d_mapEditorTest.d_map.hasCountry(1));
        assertFalse(d_mapEditorTest.d_map.hasCountry(2));
        assertFalse(d_mapEditorTest.hasQuit());
    }

    /**
     * Negative Test for editcontinent -remove
     *
     */
    @Test
    void testCmdEditContinentRemoveInvalid() {
        d_mapEditorTest.submitCommand(Command.parseString("editmap testResources/ValidTestMap.map"));
        assertFalse(d_mapEditorTest.d_map.hasContinent(11));
        d_mapEditorTest.submitCommand(Command.parseString("editcontinent -remove 11"));
        assertFalse(d_mapEditorTest.d_map.hasContinent(11));
        assertFalse(d_mapEditorTest.hasQuit());
    }


    /**
     * Test for editcontinent -add
     */
    @Test
    void testCmdEditContinentAdd() {
        d_mapEditorTest.submitCommand(Command.parseString("editmap testResources/ValidTestMap.map"));
        d_mapEditorTest.submitCommand(Command.parseString("editcontinent -add 5 6"));
        assertTrue(d_mapEditorTest.d_map.hasContinent(5));
        assertFalse(d_mapEditorTest.hasQuit());
    }

    /**
     * Test for editneighbor -add
     */
    @Test
    void testCmdEditNeighborAdd() {
        d_mapEditorTest.submitCommand(Command.parseString("editmap testResources/ValidTestMap.map"));
        d_mapEditorTest.submitCommand(Command.parseString("editneighbor -add 1 4"));
        Country l_country1 = d_mapEditorTest.d_map.getCountryById(1);
        assertTrue(l_country1.isNeighbour(4));
        Country l_country2 = d_mapEditorTest.d_map.getCountryById(4);
        assertTrue(l_country2.isNeighbour(1));
        assertFalse(d_mapEditorTest.hasQuit());
    }

    /**
     * Negative Test for editneighbor -add
     */
    @Test
    void testCmdEditNeighborAddInvalidCountry() {
        d_mapEditorTest.submitCommand(Command.parseString("editmap testResources/ValidTestMap.map"));
        d_mapEditorTest.submitCommand(Command.parseString("editneighbor -add 4 11"));
        Country l_country1 = d_mapEditorTest.d_map.getCountryById(4);
        assertFalse(l_country1.isNeighbour(11));
        assertNull(d_mapEditorTest.d_map.getCountryById(11));
        assertFalse(d_mapEditorTest.hasQuit());
    }

    /**
     * Test for editneighbor -remove
     */
    @Test
    void testCmdEditNeighborRemove() {
        d_mapEditorTest.submitCommand(Command.parseString("editmap testResources/ValidTestMap.map"));
        d_mapEditorTest.submitCommand(Command.parseString("editneighbor -remove 1 4"));
        Country l_country1 = d_mapEditorTest.d_map.getCountryById(1);
        assertFalse(l_country1.isNeighbour(4));
        assertFalse(d_mapEditorTest.hasQuit());
    }

    /**
     * Negative Test for editneighbor -remove
     */
    @Test
    void testCmdEditNeighborRemoveInvalidCountry() {
        d_mapEditorTest.submitCommand(Command.parseString("editmap testResources/ValidTestMap.map"));
        d_mapEditorTest.submitCommand(Command.parseString("editneighbor -remove 4 11"));
        Country l_country1 = d_mapEditorTest.d_map.getCountryById(4);
        assertFalse(l_country1.isNeighbour(11));
        assertNull(d_mapEditorTest.d_map.getCountryById(11));
        assertFalse(d_mapEditorTest.hasQuit());
    }
}