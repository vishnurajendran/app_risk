package game.Orders;

import entity.Player;
import entity.PlayerHandler;
import entity.RiskMap;

import java.util.Random;
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
    public AdvanceOrder(Player p_player, int sourceCountry, int p_targetCountry, int p_armiesToAdvance, RiskMap p_riskmap){
        d_armiesToAdvance = p_armiesToAdvance;
        d_ctxPlayer = p_player;
        d_targetCountry = p_targetCountry;
        d_sourceCountry = sourceCountry;
        d_riskMap = p_riskmap;
    }
    @Override
    public void executeOrder() {
        Random random = new Random();
        int l_armiesInTargetCountry = d_riskMap.getCountryArmyById(d_targetCountry);
        int l_armiesInSourceCountryAdvanced = d_armiesToAdvance;
        // removes the no of armies form sources country
        d_riskMap.getCountryById(d_sourceCountry).setArmy(d_riskMap.getCountryById(d_sourceCountry).getArmy()-d_armiesToAdvance);
        while(l_armiesInSourceCountryAdvanced>0 && l_armiesInTargetCountry>0){
            for(int i = 0;i < l_armiesInSourceCountryAdvanced; i++){
                if(random.nextDouble() <= 0.6){
                    l_armiesInTargetCountry--;
                }
            }
            for(int i = 0;i<l_armiesInTargetCountry; i++){
                if(random.nextDouble() <= 0.7){
                    l_armiesInSourceCountryAdvanced--;
                }
            }
        }

        if(l_armiesInTargetCountry <= 0){
            for(Player player: PlayerHandler.getGamePlayers()){
                if(player.isCountryOwned(d_riskMap.getCountryById(d_targetCountry))){
                    player.removeCountry(d_riskMap.getCountryById(d_targetCountry));
                }
            }
            d_ctxPlayer.assignCountry(d_riskMap.getCountryById(d_targetCountry), l_armiesInSourceCountryAdvanced);
            d_riskMap.getCountryById(d_targetCountry).setArmy(l_armiesInSourceCountryAdvanced);
        } else{
            d_riskMap.getCountryById(d_targetCountry).setArmy(l_armiesInTargetCountry);
        }
//        d_ctxPlayer.assignReinforcementsToCountry(d_targetCountry, d_armiesToAdvance);
//        d_ctxPlayer.getCountriesOwned().get(d_sourceCountry).setArmy(d_ctxPlayer.getCountriesOwned().get(d_sourceCountry).getArmy()- d_armiesToAdvance);
    }
}
