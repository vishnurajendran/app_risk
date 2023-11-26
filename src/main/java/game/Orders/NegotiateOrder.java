package game.Orders;

import entity.CardType;
import entity.Player;

/**
 * This class implements the negotiate order
 *
 * author TaranjeetKaur
 */
public class NegotiateOrder extends Order{
    Player d_negotiatedPlayer;

    /**
     * default constructor
     */
    public NegotiateOrder() {
        d_negotiatedPlayer = null;
    }

    /**
     * Parameterised constructor.
     * @param p_currPlayer  Current player object
     * @param p_oppPlayer   Negotiated player object
     */
    public NegotiateOrder(Player p_currPlayer, Player p_oppPlayer) {
        d_ctxPlayer = p_currPlayer;
        d_negotiatedPlayer = p_oppPlayer;
    }

    /**
     * This method adds the negotiated player in current players negotiated list and vice-versa.
     * Clear the Diplomat card after use.
     */
    @Override
    public void executeOrder() {
        d_ctxPlayer.addNegotiatedPlayer(d_negotiatedPlayer.getPlayerId());
        d_negotiatedPlayer.addNegotiatedPlayer(d_ctxPlayer.getPlayerId());
        d_ctxPlayer.removeCard(CardType.Diplomat);
    }

    /**
     * @return target player for this order
     */
    public Player getNegotiatedPlayer() {
        return d_negotiatedPlayer;
    }

    @Override
    public OrderType getOrderType() {
        return OrderType.Negotiate;
    }
}
