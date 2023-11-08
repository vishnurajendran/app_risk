package game.Orders;

import entity.CardType;
import entity.Player;
import entity.PlayerHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test case for testing order execution
 */
class NegotiateOrderTest {
    Player d_currPlayer;
    Player d_oppPlayer;
    NegotiateOrder d_negotiateOrder;

    /**
     * Setup of negotiate order and players
     */
    @BeforeEach
    void setUp() {
        d_currPlayer = new Player(PlayerHandler.generatePlayerId(), "currPlayer" , null);
        d_currPlayer.addCard(CardType.Diplomat);
        d_oppPlayer = new Player(PlayerHandler.generatePlayerId(),"oppPlayer" , null);
        d_negotiateOrder = new NegotiateOrder(d_currPlayer, d_oppPlayer);
    }

    /**
     * Test order execution
     */
    @Test
    void testExecuteOrder() {
        assertTrue(d_currPlayer.isCardAvailable(CardType.Diplomat));
        d_negotiateOrder.executeOrder();

        assertTrue(d_currPlayer.isPlayerNegotiated(d_oppPlayer));
        assertTrue(d_oppPlayer.isPlayerNegotiated(d_currPlayer));
        assertFalse(d_currPlayer.isCardAvailable(CardType.Diplomat));
    }
}