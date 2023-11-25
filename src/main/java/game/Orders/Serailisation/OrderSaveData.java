package game.Orders.Serailisation;
import game.Orders.OrderType;

/**
 * This class holds data for an order in the player order queue.
 * @author vishnurajendran
 */
public class OrderSaveData {
    private OrderType d_type;
    private int d_ctxPlayer;
    private int d_targetPlayer;
    private int d_targetCountry;
    private int d_sourceCountry;
    private int d_armyValue;

    /**
     * parameterised constructor.
     * @param p_ctxPlayer context player id
     * @param p_targetPlayer target player id
     * @param p_targetCountry target country id
     * @param p_sourceCountry source country id
     */
    public OrderSaveData(OrderType p_type, int p_ctxPlayer, int p_targetPlayer, int p_targetCountry, int p_sourceCountry, int p_armyValue){
        d_type = p_type;
        d_ctxPlayer = p_ctxPlayer;
        d_targetPlayer = p_targetPlayer;
        d_targetCountry = p_targetCountry;
        d_sourceCountry = p_sourceCountry;
        d_armyValue = p_armyValue;
    }

    /**
     * @return the type of the order save data target.
     */
    public OrderType getType() {
        return d_type;
    }

    /**
     * @return context player id.
     */
    public int getCtxPlayer() {
        return d_ctxPlayer;
    }

    /**
     * @return id of target player
     */
    public int getTargetPlayer() {
        return d_targetPlayer;
    }

    /**
     * @return id of target country
     */
    public int getTargetCountry() {
        return d_targetCountry;
    }

    /**
     * @return id of source country
     */
    public int getSourceCountry() {
        return d_sourceCountry;
    }

    /**
     * @return value of army
     */
    public int getArmyValue() {
        return d_armyValue;
    }
}
