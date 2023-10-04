package entity;

import common.Command;
import entity.Continent;
import entity.Country;
import entity.Player;
import entity.PlayerHandler;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class PlayerHandlerTest {

    /**
     * Setup player handler
     */
    @BeforeEach
    void setUp() {
        PlayerHandler.addGamePlayers(new ArrayList<>(Arrays.asList("player1", "player2", "player3")));
        Continent continent = new Continent(1, "test-continent", 2);
        ArrayList<Player> allPlayers = PlayerHandler.getGamePlayers();
        for (int i = 0; i < allPlayers.size(); i++) {
            allPlayers.get(i).assignCountry(new Country(i, "test-country" + i, 1), 0);
        }
    }

    /**
     * cleans player handler
     */
    @AfterEach
    void tearDown() {
        PlayerHandler.cleanup();
    }

    /**
     * this test checks if player handler
     * is correctly behaving when the deploy command is
     * provided with too many units.
     */
    @Test
    void testInvalidOrderTooManyReinforcements() {
        assertEquals(PlayerHandler.issueOrder(Command.parseString("deploy 0 7")), PlayerHandler.ISSUEORDER_TRYING_DEPLOY_MORE_THAN_AVAIALBLE);
    }

    /**
     * this test checks if player handler
     * is correctly behaving when the deploy command is
     * provided with a country, the player doesn't own.
     */
    @Test
    void testInvalidOrderInvalidCountry() {
        assertEquals(PlayerHandler.issueOrder(Command.parseString("deploy 1 2")), PlayerHandler.ISSUEORDER_PLAYER_DOESNT_OWN_COUNTRY);
    }


}