package game.Orders;

import entity.CardType;
import entity.Country;
import entity.Player;
import entity.RiskMap;
import game.Data.Context;
import game.GameEngine;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for blockadeOrder class
 */
class BlockadeOrderTest {
    Order d_blockadeOrder;
    RiskMap d_riskMap;
    Player d_player;
    Country d_country;


    /**
     * Create different object for testing
     * The country has id of 1 and is not owned by player yet.
     *
     */
    @BeforeEach
    void setUp() {


        d_riskMap=new RiskMap();
        d_player=new Player();
        d_country=new Country(1,"testCountry",1);
        d_riskMap.addCountry(d_country);
        d_blockadeOrder=new BlockadeOrder(d_riskMap,d_player,d_country);

    }


    @AfterEach
    void tearDown() {

    }


    /**
     * Test execute order success case
     */
    @Test
    void testExecuteOrder() {
        d_player.addCard(CardType.Blockade);
        d_player.assignCountry(d_country,2);
        assertEquals(2,d_country.getArmy());
        d_blockadeOrder.executeOrder();
        assertEquals(6,d_country.getArmy());

    }

    /**
     * Test when card is not there
     */
    @Test
    void testExecuteBlockAdeOrderWithoutCard(){
        d_player.assignCountry(d_country,2);
        assertEquals(2,d_country.getArmy());
        d_blockadeOrder.executeOrder();
        assertEquals(2,d_country.getArmy());
    }

    /**
     * Test when country is not owned
     */
    @Test
    void testExecuteBlockAdeOrderWithoutCountry(){
        d_player.addCard(CardType.Blockade);
        assertEquals(0,d_country.getArmy());
        d_blockadeOrder.executeOrder();
        assertEquals(0,d_country.getArmy());
    }

}

