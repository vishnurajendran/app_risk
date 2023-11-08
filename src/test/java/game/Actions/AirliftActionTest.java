package game.Actions;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import game.GameEngine;
import entity.Player;
import entity.Country;
import entity.CardType;

public class AirliftActionTest {

    private AirliftAction airliftAction;
    private GameEngine gameContext;
    private Player currentPlayer;
    private Country sourceCountry;
    private Country targetCountry;

    @Before
    public void setUp() {
        gameContext = new GameEngine();
        airliftAction = new AirliftAction();
        currentPlayer = new Player();
        sourceCountry = new Country(1, "Source Country", 1);
        targetCountry = new Country(2, "Target Country", 2);
        currentPlayer.addCard(CardType.Airlift);
        currentPlayer.assignCountry(sourceCountry, 10);
        currentPlayer.assignCountry(targetCountry, 9);

        gameContext.setEngine(new GameEngine());
        gameContext.setCurrentPlayer(currentPlayer);
        gameContext.getEngine().setMap();
    }

    @Test
    public void testCheckCommandValidityInvalidCommand() {
        int result = airliftAction.checkCommandValidity();
        assertEquals(0, result); // 1. AIRLIFT_ORDER_ERROR: Airlift command is invalid.
    }

    @Test
    public void testCheckCommandValidityPlayerDoesntOwnAirliftCard() {
        currentPlayer.removeCard(CardType.Airlift);
        int result = airliftAction.checkCommandValidity();
        assertEquals(1, result); // 2. AIRLIFT_ORDER_PLAYER_DOESNT_OWN_AIRLIFT_CARD
    }

    @Test
    public void testCheckCommandValidityPlayerDoesntOwnSourceCountry() {
        currentPlayer.removeCountry(sourceCountry);
        int result = airliftAction.checkCommandValidity();
        assertEquals(2, result); // 3. AIRLIFT_ORDER_PLAYER_DOESNT_OWN_COUNTRY
    }

    @Test
    public void testCheckCommandValidityMoreArmiesRequestedThanAvailable() {
        sourceCountry.setArmy(2);
        int result = airliftAction.checkCommandValidity();
        assertEquals(3, result); // 4. AIRLIFT_ORDER_MORE_THAN_AVAILABLE
    }

    @Test
    public void testCheckCommandValidityPlayerDoesntOwnTargetCountry() {
        currentPlayer.removeCountry(targetCountry);
        int result = airliftAction.checkCommandValidity();
        assertEquals(4, result); // 5. AIRLIFT_ORDER_PLAYER_DOESNT_OWN_TARGET_COUNTRY
    }

    @Test
    public void testCheckCommandValiditySuccessful() {
        int result = airliftAction.checkCommandValidity();
        assertEquals(5, result); // 6. AIRLIFT ACTION SUCCESSFUL
    }
}
