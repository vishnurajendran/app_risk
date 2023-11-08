package game.Actions;

import common.Command;
import entity.*;
import game.Data.Context;
import game.GameEngine;
import game.Orders.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for NegotiateAction
 */
class NegotiateActionTest {
        Player d_player;
        Player d_oppPlayer;
        RiskMap d_riskMap;
        GameEngine d_gameEngine;
        Context d_context;

        Command d_command;
        GameAction d_action;

        /**
         * Create different object for testing
         */
        @BeforeEach
        void setUp() {
            d_gameEngine = new GameEngine();
            MapLoader.loadMap("testResources/ValidTestMap.map");
            d_riskMap = MapLoader.getMap();
            d_gameEngine.setMap(d_riskMap);
            ArrayList<String> players = new ArrayList<>();
            players.add("dummy1");
            players.add("dummy2");
            PlayerHandler.addGamePlayers(players,d_riskMap);
            d_player = PlayerHandler.getPlayerById(PlayerHandler.getPlayerIDByName("dummy1"));
            d_oppPlayer = PlayerHandler.getPlayerById(PlayerHandler.getPlayerIDByName("dummy2"));
            d_context = new Context(d_player, d_gameEngine);
            d_action = new NegotiateAction();
            d_action.SetContext(d_context);
        }

        /**
         * Negative test case when card is not owned by the player.
         */
        @Test
        void testExecuteCardNotFound() {
            Order l_order=d_player.nextOrder();
            assertNull(l_order);
            UUID l_oppPlayerId = PlayerHandler.getPlayerIDByName(d_oppPlayer.getPlayerName());
            d_command=Command.parseString("negotiate " + l_oppPlayerId);
            d_action.execute(d_command);
            assertEquals(ActionExecStatus.Fail,d_action.getExecutionStatus());
        }

        /**
         * Negative test case player is invalid.
         */
        @Test
        void testExecuteCardInvalidPlayer() {
            Order l_order=d_player.nextOrder();
            assertNull(l_order);
            d_player.addCard(CardType.Diplomat);
            d_command=Command.parseString("negotiate 122");
            d_action.execute(d_command);
            assertEquals(ActionExecStatus.Fail,d_action.getExecutionStatus());
        }

        /**
         * Valid Action Test Case
         */
        @Test
        void testValidAction() {
            Order l_order=d_player.nextOrder();
            assertNull(l_order);
            d_player.addCard(CardType.Diplomat);
            UUID l_oppPlayerId = PlayerHandler.getPlayerIDByName(d_oppPlayer.getPlayerName());
            d_command=Command.parseString("negotiate " + l_oppPlayerId);
            d_action.execute(d_command);

            l_order=d_player.nextOrder();
            assertNotNull(l_order);
            assertEquals(ActionExecStatus.Success,d_action.getExecutionStatus());
        }
}