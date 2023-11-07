package game.Orders;

import entity.Player;

/**
 * @author Soham
 */
public class AdvanceOrder extends Order{

    /**
     * Default Constructor
     */
    public AdvanceOrder(){
        d_armiesToAdvance = 0;
        d_ctxPlayer = null;
        d_targetCountry = 0;
        d_sourceCountry = 0;
    }

    /**
     * Constructor with assigned value
     * @param p_player the current active player
     * @param sourceCountry the country from which the armies are to be deployed
     * @param p_targetCountry the country to which the armies are to be deployed
     * @param p_armiesToAdvance no of armies from source to targetCountry
     */
    public AdvanceOrder(Player p_player,  int sourceCountry, int p_targetCountry, int p_armiesToAdvance){
        d_armiesToAdvance = p_armiesToAdvance;
        d_ctxPlayer = p_player;
        d_targetCountry = p_targetCountry;
        d_sourceCountry = sourceCountry;
    }
    @Override
    public void executeOrder() {
        d_ctxPlayer.assignReinforcementsToCountry(d_targetCountry, d_armiesToAdvance);
        d_ctxPlayer.getCountriesOwned().get(d_sourceCountry).setArmy(d_ctxPlayer.getCountriesOwned().get(d_sourceCountry).getArmy()- d_armiesToAdvance);

    }
}
