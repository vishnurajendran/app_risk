package game.Orders;

import entity.Player;

/**
 * @author Soham
 */
public class AdvanceOrder extends Order{

    public AdvanceOrder(){
        d_armiesToAdvance = 0;
        d_ctxPlayer = null;
        d_targetCountry = 0;
        d_initialCountry = 0;
    }

    public AdvanceOrder(Player p_player,  int p_targetCountry, int p_initialCountry, int p_armiesToAdvance){
        d_armiesToAdvance = p_armiesToAdvance;
        d_ctxPlayer = p_player;
        d_targetCountry = p_targetCountry;
        d_initialCountry = p_initialCountry;
    }
    @Override
    public void executeOrder() {
        d_ctxPlayer.assignReinforcementsToCountry(d_targetCountry, d_armiesToAdvance);
        d_ctxPlayer.getCountriesOwned().get(d_initialCountry).setArmy(d_ctxPlayer.getCountriesOwned().get(d_initialCountry).getArmy()- d_armiesToAdvance);
    }
}
