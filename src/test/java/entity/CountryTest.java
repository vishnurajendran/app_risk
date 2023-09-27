package entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test class for Country Class.
 */
class CountryTest {
    Country testCountry;
    Country testBorderCountry;
    Country testBorderCountry2;
    /**
     * Setup for unit tests.
     * Sample data: p_id:1; p_name: Canada; p_continentId:2; p_xCoordinates: 3; p_yCoordinates: 4;
     * army: 10
     * Sample data for border related features:
     * p_id:2; p_name: America; p_continentId:2; p_xCoordinates: 4; p_yCoordinates: 5;
     * army: 20
     * Sample data 2 for border related features:
     * p_id:3; p_name: Mexico; p_continentId:3; p_xCoordinates: 5; p_yCoordinates: 6;
     * army:1
     */
    @BeforeEach
    void setUp() {
        testCountry=new Country(1,"Canada",2,3,4);
        testCountry.setArmy(10);
        testBorderCountry=new Country(2,"America",2,4,5);
        testBorderCountry.setArmy(20);
        testBorderCountry2=new Country(3,"Mexico",3,5,6);
        testBorderCountry2.setArmy(1);
        testCountry.addBorder(testBorderCountry);
    }

    /**
     * Tests getArmy.
     * Expected value: 10
     */
    @Test
    void getArmy() {
        assertEquals(10,testCountry.getArmy());
    }

    /**
     * Tests getName.
     * Expected value: "Canada".
     */
    @Test
    void getName() {
        assertEquals("Canada",testCountry.getName());
    }

    /**
     * Tests getDId.
     * Expected value: 1.
     */
    @Test
    void getDId() {
        assertEquals(1,testCountry.getDId());
    }

    /**
     * Test getContinentId.
     * Expected value: 2
     */
    @Test
    void getContinentId() {
        assertEquals(2,testCountry.getContinentId());
    }

    /**
     * Test getXCoordinates.
     * Expected value:3
     *
     */
    @Test
    void getXCoordinates() {
        assertEquals(3,testCountry.getXCoordinates());
    }

    /**
     * Test getYCoordinates.
     * Expected value:4
     *
     */
    @Test
    void getYCoordinates() {
        assertEquals(4,testCountry.getYCoordinates());
    }

    /**
     * Test getBorders.
     * Border should only contain America for now.
     * Therefore, Map from getBorders should contain America but get null while trying to find Mexico.
     */
    @Test
    void getBorders() {
        Map<Integer,Country> l_borders=testCountry.getBorders();
        assertEquals("America",l_borders.get(testBorderCountry.getDId()).getName());
        assertNull(l_borders.get(testBorderCountry2.getDId()));
    }

    /**
     * Test set continent ID.
     * The id of test country should be 10 after set.
     */
    @Test
    void setContinentId() {
        testCountry.setContinentId(10);
        assertEquals(10,testCountry.getContinentId());
    }

    /**
     * Test set X coordinates
     * The x coordinates after set should be 11
     */
    @Test
    void setXCoordinates() {
        testCountry.setXCoordinates(11);
        assertEquals(11,testCountry.getXCoordinates());
    }
    /**
     * Test set Y coordinates
     * The y coordinates after set should be 12
     */
    @Test
    void setYCoordinates() {
        testCountry.setYCoordinates(12);
        assertEquals(12,testCountry.getYCoordinates());
    }

    /**
     * Test set Name.
     * The name after set should be "England"
     */
    @Test
    void setName() {
        testCountry.setName("England");
        assertEquals("England",testCountry.getName());
    }

    /**
     * Test set army.
     * The number of armies after setting should be 100.
     */
    @Test
    void setArmy() {
        testCountry.setArmy(100);
        assertEquals(100,testCountry.getArmy());
    }

    /**
     * Test add border
     * Mexico should also be connected to Canada
     */
    @Test
    void addBorder() {
        testCountry.addBorder(testBorderCountry2);
        assertEquals("Mexico",testCountry.getBorders().get(testBorderCountry2.getDId()).getName());

    }

    /**
     * Test the override to String method
     * The string should be "Id: 1 Name: Canada continentId: 2{2=Id: 2 Name: America continentId: 2{}}"
     */
    @Test
    void testToString() {
        assertEquals("Id: 1 Name: Canada continentId: 2{2=Id: 2 Name: America continentId: 2{}}",
                testCountry.toString());
    }
}