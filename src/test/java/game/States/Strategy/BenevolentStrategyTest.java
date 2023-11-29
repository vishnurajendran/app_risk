package game.States.Strategy;

import common.Command;
import entity.Country;
import entity.Player;
import entity.PlayerHandler;
import game.Data.StrategyData;
import game.GameEngine;
import game.Orders.DeployOrder;
import game.Orders.Order;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BenevolentStrategyTest {
    GameEngine d_gameEngineTest;

    ArrayList<Player> d_gamePlayersTest;
    @BeforeEach
    void setUp() {
        d_gameEngineTest = new GameEngine();
        d_gameEngineTest.initialise();
        d_gameEngineTest.submitCommand(Command.parseString("loadmap testResources/WoW.map"));
        d_gameEngineTest.submitCommand(Command.parseString("gameplayer -add p1 -strategy b"));
        d_gameEngineTest.submitCommand(Command.parseString("assigncountries"));
        d_gamePlayersTest = PlayerHandler.getGamePlayers();
        for(Player player: d_gamePlayersTest){
            player.setStrategyContext(d_gameEngineTest);
        }
        d_gamePlayersTest.get(0).assignReinforcementsToPlayer();
        d_gamePlayersTest.get(0).assignCountry(d_gameEngineTest.getMap().getCountryById(28), 0);
        d_gamePlayersTest.get(0).assignCountry(d_gameEngineTest.getMap().getCountryById(20), 1);
        d_gamePlayersTest.get(0).assignCountry(d_gameEngineTest.getMap().getCountryById(15), 1);
    }

    @AfterEach
    void tearDown() {
        d_gameEngineTest.shutdown();
        PlayerHandler.cleanup();
    }

    @Test
    void testFindWeakestCountry() {
        // Assuming that the BenevolentStrategy is the current player
        BenevolentStrategy benevolentStrategy = new BenevolentStrategy();

        // Test if it correctly finds the country with the least armies
        Country weakestCountry = benevolentStrategy.findWeakestCountry(d_gamePlayersTest.get(0).getCountriesOwned());
        assertEquals(d_gameEngineTest.getMap().getCountryById(28), weakestCountry);
    }

    @Test
    void testDeployOrder() {
        d_gamePlayersTest.get(0).issueOrder();
        Order order = d_gamePlayersTest.get(0).nextOrder();
        order.executeOrder();
        assertEquals(1, d_gameEngineTest.getMap().getCountryById(28).getArmy());
    }
}
