package game.Actions;

import common.Command;
import entity.Continent;
import entity.Country;
import entity.Player;
import entity.PlayerHandler;
import game.Data.Context;
import game.GameEngine;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

class AdvanceActionTest {

    GameEngine d_gameEngineTest;
    AdvanceAction d_advanceActionTest;

    ArrayList<Player> d_gamePlayersTest;

    @BeforeEach
    void setUp(){
        d_gameEngineTest = new GameEngine();
        d_gameEngineTest.initialise();
        d_advanceActionTest = new AdvanceAction();
        PlayerHandler.addGamePlayers(new ArrayList<>(Arrays.asList("player1", "player2", "player3", "player4")), null);
        Continent continent = new Continent(1, "test-continent", 3);
        d_gamePlayersTest = PlayerHandler.getGamePlayers();
        for (int i = 0; i < d_gamePlayersTest.size(); i++) {
            d_gamePlayersTest.get(i).assignCountry(new Country(i, "test-country" + i, 1), 0);
        }
    }

    @Test
    void TestCommands(){
        Context l_testContext = new Context(d_gamePlayersTest.get(0), d_gameEngineTest);
        Command l_cmd = Command.parseString("advance 1 2 3");
        d_advanceActionTest.execute(l_cmd);
        Assertions.assertEquals(1, d_advanceActionTest.checkCommandValidity());

    }


}