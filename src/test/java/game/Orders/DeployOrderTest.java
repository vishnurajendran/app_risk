package game.Orders;

import common.Command;
import entity.Player;
import game.GameEngine;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DeployOrderTest {
    GameEngine d_gameEngineTest;
    ArrayList<Player> d_gamePlayersTest = new ArrayList<>();
    ArrayList<Order> d_deployOrderTest = new ArrayList<>();

    /**
     * Setup method that initializes the test environment before each test case.
     * It creates a GameEngine, adds players, sets up a continent, and assigns countries to players.
     */
    @BeforeEach
    void setUp() {
        d_gameEngineTest = new GameEngine();
        d_gameEngineTest.initialise();
        d_gameEngineTest.submitCommand(Command.parseString("loadmap testResources/WoW.map"));
        d_gamePlayersTest.add(new Player(1, "player1", d_gameEngineTest.getMap()));
        d_gamePlayersTest.get(0).assignCountry(d_gameEngineTest.getMap().getCountryById(22), 0);
        d_gamePlayersTest.get(0).assignCountry(d_gameEngineTest.getMap().getCountryById(33), 0);
        d_gamePlayersTest.get(0).assignReinforcementsToPlayer();
    }

    /**
     * Cleanup method that runs after each test case to clean up and reset the environment.
     */
    @AfterEach
    void cleanup() {
        d_gameEngineTest.quitGame();
        d_gameEngineTest.shutdown();
        d_gameEngineTest = null;
    }

    /**
     * Tests the order execution of deploy command
     * Deploys armies in two countries and checks if they deployed.
     */
    @Test
    void TestDeployOrder() {
        d_deployOrderTest.add(new DeployOrder(d_gamePlayersTest.get(0), 2, 22));
        d_deployOrderTest.add(new DeployOrder(d_gamePlayersTest.get(0), 1, 33));
        d_deployOrderTest.get(0).executeOrder();
        d_deployOrderTest.get(1).executeOrder();

        assertEquals(2, d_gameEngineTest.getMap().getCountryById(22).getArmy());
        assertEquals(1, d_gameEngineTest.getMap().getCountryById(33).getArmy());
    }
}