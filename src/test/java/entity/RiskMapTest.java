package entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test class for the Map Class.
 */
class RiskMapTest {
    Country d_testCountry;
    Country d_testBorderCountry;
    Country d_testBorderCountry2;
    Continent d_testContinent;
    Continent d_testContinent2;
    RiskMap d_testRiskMap;

    /**
     * Setup for unit tests.
     * Map has a name of "Test Map".
     * Countries have detailed info below which is similar to CountryTest
     *
     * @see CountryTest#setUp()
     * Sample data: p_id:1; p_name: Canada; p_continentId:2; p_xCoordinates: 3; p_yCoordinates: 4;
     * army: 10
     * Sample data for border related features:
     * p_id:2; p_name: America; p_continentId:2; p_xCoordinates: 4; p_yCoordinates: 5;
     * army: 20
     * Sample data 2 for border related features:
     * p_id:3; p_name: Mexico; p_continentId:3; p_xCoordinates: 5; p_yCoordinates: 6;
     * army:1
     *
     *
     * Continent have detailed info below which is similar to ContinentTest
     * Sample data: p_id:1; p_name:"Asia"; p_controlValue:7; p_color:"yellow";
     * Sample data for add continent:
     * p_id:2; p_name:"Australia"; p_controlValue:3; p_color:"blue";
     */
    @BeforeEach
    void setUp() {
        d_testCountry = new Country(1, "Canada", 2, 3, 4);
        d_testCountry.setArmy(10);
        d_testBorderCountry = new Country(2, "America", 2, 4, 5);
        d_testBorderCountry.setArmy(20);
        d_testBorderCountry2 = new Country(3, "Mexico", 3, 5, 6);
        d_testBorderCountry2.setArmy(1);
        d_testCountry.addBorder(d_testBorderCountry);
        d_testContinent = new Continent(1, "Asia", 7, "yellow");
        d_testContinent2 = new Continent(2, "Australia", 3, "blue");
        d_testRiskMap = new RiskMap("Test Map");
        d_testRiskMap.addCountry(d_testCountry);
        d_testRiskMap.addCountry(d_testBorderCountry);
        d_testRiskMap.addContinent(d_testContinent);
    }

    /**
     * Unit test for get Name.
     * Expected result: "Test Map"
     */
    @Test
    void getName() {
        assertEquals("Test Map", d_testRiskMap.getName());
    }

    /**
     * Unit test for setName of the map.
     * Expected result: "Modified map"
     */
    @Test
    void setName() {
        d_testRiskMap.setName("Modified map");
        assertEquals("Modified map", d_testRiskMap.getName());
    }

    /**
     * Unit test for add a country
     * Mexico should be added to the map.
     */
    @Test
    void addCountry() {
        assertFalse(d_testRiskMap.getCountryIds().contains(3));
        d_testRiskMap.addCountry(d_testBorderCountry2);
        assertTrue(d_testRiskMap.getCountryIds().contains(3));
    }

    /**
     * Unit test for add a continent
     * Australia should be added to the map.
     */
    @Test
    void testAddContinent(){
        assertFalse(d_testRiskMap.getContinentIds().contains(2));
        d_testRiskMap.addContinent(d_testContinent2);
        assertTrue(d_testRiskMap.getContinentIds().contains(2));
    }

    /**
     * Unit test for add borders.
     * Mexico will be added connection with America.
     */
    @Test
    void addBorder(){
        d_testRiskMap.addCountry(d_testBorderCountry2);
        assertEquals(0, d_testBorderCountry2.getBorders().size());

        d_testRiskMap.addBorder(3, 1);
        assertEquals(1, d_testBorderCountry2.getBorders().get(1).getDId());
        assertEquals(3, d_testCountry.getBorders().get(3).getDId());
    }

    /**
     * Unit test for add Borders
     * Mexico will be added connection with America and Canada
     */
    @Test
    void addBorders() {
        d_testRiskMap.addCountry(d_testBorderCountry2);
        assertEquals(0, d_testBorderCountry2.getBorders().size());

        d_testRiskMap.addBorders(3, new String[]{"1", "2"});
        assertEquals(2, d_testBorderCountry2.getBorders().size());
    }

    /**
     * Unit test for getCountryIds.
     * Ids should contain 1 and 2 but not 3
     */
    @Test
    void getCountryIds() {
        Set l_IDs = d_testRiskMap.getCountryIds();
        assertTrue(l_IDs.contains(1));
        assertTrue(l_IDs.contains(2));
        assertFalse(l_IDs.contains(3));
    }

    /**
     * Unit test for getContinentIds.
     * Ids should contain 1 but not 2
     */
    @Test
    void testGetContinentIds() {
        Set l_IDs = d_testRiskMap.getContinentIds();
        assertTrue(l_IDs.contains(1));
        assertFalse(l_IDs.contains(2));
    }

    /**
     * Unit test for getArmyById.
     * The number of Army should be 10 for 1 and 20 for 2.
     */
    @Test
    void getCountryArmyById() {
        assertEquals(10, d_testRiskMap.getCountryArmyById(1));
        assertEquals(20, d_testRiskMap.getCountryArmyById(2));
    }

    /**
     * Unit test for getCountryById
     * Expected value: Country object of id:2
     */
    @Test
    void testGetCountryById(){
        Country l_country = d_testRiskMap.getCountryById(2);
        assertEquals(d_testBorderCountry, l_country);
    }

    /**
     * Unit test for getContinentById
     * Expected value: Continent object of id:1
     */
    @Test
    void testGetContinentById(){
        Continent l_continent = d_testRiskMap.getContinentById(1);
        assertEquals(d_testContinent, l_continent);
    }

    /**
     * Unit test for get Army By ID.
     * The number of army after set should be 33
     */
    @Test
    void setCountryArmyById() {
        assertEquals(10, d_testRiskMap.getCountryArmyById(1));
        d_testRiskMap.setCountryArmyById(1, 33);
        assertEquals(33, d_testRiskMap.getCountryArmyById(1));
    }

    /**
     * Unit test for override toString
     * The Expected output is:
     * "Map Test Map\n" +
     * "Id: 1 Name: Canada continentId: 2{2=Id: 2 Name: America continentId: 2{}}\n" +
     * "Id: 2 Name: America continentId: 2{}\n"
     */
    @Test
    void testToString() {
        assertEquals("Map Test Map\n" +
                "Id: 1 Name: Canada continentId: 2{2=Id: 2 Name: America continentId: 2{}}\n" +
                "Id: 2 Name: America continentId: 2{}\n", d_testRiskMap.toString());
    }
}