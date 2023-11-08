package game.Actions;

import common.Command;
import entity.CardType;
import entity.Country;
import entity.Player;
import entity.RiskMap;
import game.Data.Context;
import game.GameEngine;
import game.Orders.Order;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for blockade action
 *
 */
class BlockadeActionTest {
    Player d_player;
    RiskMap d_riskMap;
    //only for creating context
    GameEngine d_gameEngine;
    Context d_context;
    Country d_country;

    Command d_command;
    GameAction d_action;
    ByteArrayOutputStream d_outputStream;

    /**
     * Create different object for testing
     * The country has id of 1 and is not owned by player yet.
     * Set the console out to be captured for testing
     */
    @BeforeEach
    void setUp() {
        // get output for testing error cases
        d_outputStream=new ByteArrayOutputStream();
        System.setOut(new PrintStream(d_outputStream));


        d_riskMap=new RiskMap();
        d_gameEngine=new GameEngine();
        d_gameEngine.setMap(d_riskMap);
        d_player=new Player();
        d_context=new Context(d_player,d_gameEngine);
        d_country=new Country(1,"testCountry",1);
        d_riskMap.addCountry(d_country);
        d_action=new BlockadeAction();
        d_action.SetContext(d_context);

    }

    /**
     * Set output back
     */
    @AfterEach
    void tearDown() {
        System.setOut(System.out);
    }

    /**
     * Test correct order that needed to be added.
     */
    @Test
    void testExecute() {
        Order l_order=d_player.nextOrder();
        assertNull(l_order);
        d_command=Command.parseString("blockade 1");
        d_player.addCard(CardType.Blockade);
        d_player.assignCountry(d_country,1);
        d_action.execute(d_command);

        l_order=d_player.nextOrder();
        assertNotNull(l_order);
        assertEquals(ActionExecStatus.Success,d_action.getExecutionStatus());



    }

    /**
     * Test order when card is not there
     */
    @Test
    void testBlockadeActionErrorWithoutCard(){
        d_command=Command.parseString("blockade 1");
        assertEquals(ActionExecStatus.Success,d_action.getExecutionStatus());
        d_player.assignCountry(d_country,1);
        d_action.execute(d_command);
        assertEquals(ActionExecStatus.Fail,d_action.getExecutionStatus());
        assertEquals("You don't have blockade card to blockade.",d_outputStream.toString().trim());






    }
    /**
     * Test order when country is not owned by player
     */
    @Test
    void testBlockadeActionErrorWithoutOwnership(){
        d_command=Command.parseString("blockade 1");
        assertEquals(ActionExecStatus.Success,d_action.getExecutionStatus());
        d_player.addCard(CardType.Blockade);
        d_action.execute(d_command);
        assertEquals(ActionExecStatus.Fail,d_action.getExecutionStatus());
        assertEquals("The country is not owned by you.",d_outputStream.toString().trim());



    }



}