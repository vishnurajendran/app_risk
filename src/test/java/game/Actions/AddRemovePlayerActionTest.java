package game.Actions;

import common.Command;
import entity.MapLoader;
import entity.PlayerHandler;
import game.Data.Context;
import game.GameEngine;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * this class test the AddOrRemove player action.
 */
class AddRemovePlayerActionTest {

    private GameEngine d_engine;
    private GameAction d_action;

    /**
     * setup our environemnt
     */
    @BeforeEach
    public void setup(){
        d_engine = new GameEngine();
        d_action = new AddRemovePlayerAction();
        MapLoader.loadMap("testResources/WoW.map");
        d_engine.setMap(MapLoader.getMap(), "testResources/WoW.map");
        d_action.setContext(new Context(null, d_engine));
    }

    /**
     * cleanup the environment
     */
    @AfterEach
    public void cleanup(){
        d_engine.quitGame();
        d_engine.shutdown();
        PlayerHandler.cleanup();
    }

    /**
     * Tests player add functionality of
     * AddOrRemoveAction
     */
    @Test
    public void testPlayerAdd(){
        d_action.execute(Command.parseString("gameplayer -add v1 v2"));
        assertEquals(PlayerHandler.getGamePlayers().size(), 2);
    }

    @Test
    public void testPlayerRemove(){
        d_action.execute(Command.parseString("gameplayer -add v1 v2"));
        assertEquals(PlayerHandler.getGamePlayers().size(), 2);
        d_action.execute(Command.parseString("gameplayer -remove v1"));
        assertEquals(PlayerHandler.getGamePlayers().size(), 1);
    }
}