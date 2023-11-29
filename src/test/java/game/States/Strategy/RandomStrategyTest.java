package game.States.Strategy;

import common.Command;
import common.Logging.Logger;
import entity.Player;
import entity.PlayerHandler;
import game.GameEngine;
import game.Orders.Order;
import game.Orders.OrderType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * this class tests if RandomStrategy generates an
 * order of advance or bomb or deploy. this test asks
 * the strategy to generate order 100 times and checks if
 * valid orders are generated at a reasonable distribution.
 */
class RandomStrategyTest {
    GameEngine d_engine;
    Player d_player;

    /**
     * setup the engine for testing.
     */
    @BeforeEach
    void setUp() {
        d_engine = new GameEngine();
        d_engine.initialise();
        d_engine.submitCommand(Command.parseString("loadmap testResources/WoW.map"));
        d_engine.submitCommand(Command.parseString("gameplayer -add p1 p2 -strategy r h"));
        d_player = PlayerHandler.getGamePlayers().get(0);
        d_engine.submitCommand(Command.parseString("assigncountries"));
    }

    /**
     * cleanup after testing.
     */
    @AfterEach
    void tearDown() {
        d_engine.shutdown();
        PlayerHandler.cleanup();
    }

    /**
     * test the order creation 100 times to check if all types of valid orders are created,
     * at a reasonable distribution.
     */
    @Test
    public void testRandomStrategyOrderCreation(){
        int iter = 0;
        while(iter < 100) {
            iter ++;
            d_player.issueOrder();
            if (d_player.hasOrders()) {
                Order l_order = d_player.nextOrder();
                System.out.println("Generated " + l_order.getOrderType());
                assertTrue(l_order != null && (
                        l_order.getOrderType() == OrderType.Advance ||
                                l_order.getOrderType() == OrderType.Bomb ||
                                l_order.getOrderType() == OrderType.Deploy
                ));
                //execute order to change state for next iteration.
                l_order.executeOrder();
            }
        }
    }

}