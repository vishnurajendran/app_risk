package game.Orders;
import entity.Player;


/**
 * Bomb order class used to execute the bomb order.
 */
public class BombOrder extends Order {

    public BombOrder(){
        d_ctxPlayer = null;
        d_targetCountry = 0;
        d_sourceCountry = 0;
        d_armiesToAdvance = 0;
        d_armiesToAirlift = 0;
        d_riskMap = null;
    }


    public BombOrder(Player p_player, int p_targetCountry) {
        d_ctxPlayer = p_player;
        d_targetCountry = p_targetCountry;
    }

    @Override
    public void executeOrder() {
        // TODO: Write Bomb execute logic
    }
}
