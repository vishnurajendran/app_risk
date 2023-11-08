package game.Orders;

import entity.CardType;
import entity.Country;
import entity.Player;
import entity.RiskMap;

/**
 * Blockade order class used to execute the blockade order.
 */
public class BlockadeOrder extends Order{
    private Country d_country;

    /**
     * Constructor which create order and initialize it
     * @param p_riskMap Map of the game.
     * @param p_player Player that want to execute this order
     * @param p_country The targetCountry of the order
     */
    public BlockadeOrder(RiskMap p_riskMap,Player p_player, Country p_country){
        d_riskMap=p_riskMap;
        d_ctxPlayer=p_player;
        d_country=p_country;
        d_targetCountry=p_country.getDId();
    }

    /**
     * Execution of blockade order, which did extra check for card availability and country ownership.
     * Then execute blockade and remove card from player.
     */
    @Override
    public void executeOrder() {
        if(!d_ctxPlayer.isCardAvailable(CardType.Blockade)){
            System.out.println("You don't have blockade card to blockade at the execution." +
                    "The card might already been used or removed.");
            return;
        }


        if(!d_ctxPlayer.isCountryOwned(d_country)){
            System.out.println("The country is not owned by you at the execution." +
                    " The ownership of country might changed.");
            return;
        }

        d_riskMap.increaseCountryArmyById(d_targetCountry,d_riskMap.getCountryArmyById(d_country.getDId())*2);
        d_ctxPlayer.removeCountry(d_country);


    }
}
