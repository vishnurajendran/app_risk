package game.Orders;

import common.Logging.Logger;
import entity.Player;
import entity.PlayerHandler;
import entity.RiskMap;
import mapShow.MapViewer;

import java.util.Random;

/**
 * this class executes the advance orders present in the players list of orders.
 * @author Soham
 */
public class AdvanceOrder extends Order {
    private int d_armiesToAdvance;

    /**
     * Default Constructor
     */
    public AdvanceOrder() {
        d_armiesToAdvance = 0;
        d_ctxPlayer = null;
        d_targetCountry = 0;
        d_sourceCountry = 0;
    }

    /**
     * Constructor with assigned value
     *
     * @param p_player          the current active player
     * @param sourceCountry     the country from which the armies are to be deployed
     * @param p_targetCountry   the country to which the armies are to be deployed
     * @param p_armiesToAdvance no of armies from source to targetCountry
     */
    public AdvanceOrder(Player p_player, int sourceCountry, int p_targetCountry, int p_armiesToAdvance, RiskMap p_riskmap) {
        d_armiesToAdvance = p_armiesToAdvance;
        d_ctxPlayer = p_player;
        d_targetCountry = p_targetCountry;
        d_sourceCountry = sourceCountry;
        d_riskMap = p_riskmap;
    }

    /**
     * This method executes the advance actions by the player.
     * The attacker country first attacks with 60% chances of success on killing each defending
     * Then defending country defends with 70% chances of success on killing each attacking army
     */
    @Override
    public void executeOrder() {
        Random random = new Random();
        String canExecute = canExecuteCommand();
        if(canExecute.equals("SUCCESS")){
            int l_armiesInTargetCountry = d_riskMap.getCountryArmyById(d_targetCountry);
            int l_armiesInSourceCountryAdvanced = d_armiesToAdvance;
            // removes the no of armies form sources country
            Player l_targetCountryOwner = null;
            for (Player player : PlayerHandler.getGamePlayers()) {
                PlayerHandler.displayGamePlayersCountries(player);
                if (player.isCountryOwned(d_riskMap.getCountryById(d_targetCountry))) {
                    player.removeCountry(d_riskMap.getCountryById(d_targetCountry));
                    l_targetCountryOwner = player;
                    break;
                }
            }

            if(l_targetCountryOwner == null){
                return;
            }

            //assert l_targetCountryOwner != null;
            d_riskMap.getCountryById(d_sourceCountry).setArmy(d_riskMap.getCountryById(d_sourceCountry).getArmy() - d_armiesToAdvance);
            if(l_targetCountryOwner.getPlayerId() == d_ctxPlayer.getPlayerId()){
                d_ctxPlayer.assignCountry(d_riskMap.getCountryById(d_targetCountry),l_armiesInTargetCountry);
                d_riskMap.getCountryById(d_targetCountry).setArmy(l_armiesInTargetCountry);
                return;
            }
            // subtract source army value.
            d_riskMap.getCountryById(d_sourceCountry).incrementArmy(-1*l_armiesInSourceCountryAdvanced);

            // run simulation
            while (l_armiesInSourceCountryAdvanced > 0 && l_armiesInTargetCountry > 0) {
                double attacker = random.nextDouble();
                double defender = random.nextDouble();
                if (attacker <= 0.6) {
                    l_armiesInTargetCountry--;
                }

                if (defender <= 0.7) {
                    l_armiesInSourceCountryAdvanced--;
                }
            }
            d_riskMap.getCountryById(d_targetCountry).setArmy(l_armiesInTargetCountry);

            if (l_armiesInTargetCountry <= 0) {
                System.out.println(d_ctxPlayer.getPlayerName() + " conquered " + d_riskMap.getCountryById(d_targetCountry).getName()
                        + " that was owned by " + l_targetCountryOwner.getPlayerName());

                l_targetCountryOwner.removeCountry(d_riskMap.getCountryById(d_targetCountry));
                d_ctxPlayer.assignCountry(d_riskMap.getCountryById(d_targetCountry), l_armiesInSourceCountryAdvanced);
                d_riskMap.getCountryById(d_targetCountry).setArmy(l_armiesInSourceCountryAdvanced);
                d_ctxPlayer.addRandomCard();
            } else {
                System.out.println(l_targetCountryOwner.getPlayerName() + " defended an attack from "
                        + d_ctxPlayer.getPlayerName() + " on " + d_riskMap.getCountryById(d_targetCountry).getName());
            }
        }
    }

    @Override
    public OrderType getOrderType() {
        return OrderType.Advance;
    }


    public String canExecuteCommand(){
        // checks if player owns the country
        if(!d_ctxPlayer.isCountryOwned(d_riskMap.getCountryById(d_sourceCountry))) {
            return "ERROR: Advance execution failed. " + d_ctxPlayer.getPlayerName() + " doesn't own the country " + d_sourceCountry + " anymore";
        }
        // checks if that country has enough armies to deploy
        else if(d_armiesToAdvance > d_riskMap.getCountryArmyById(d_sourceCountry)){
            return "ERROR: Advance execution failed. " + d_ctxPlayer.getPlayerName() + " doesn't have enough armies on the country " + d_riskMap.getCountryById(d_sourceCountry).getName() + " anymore";
        }
        // check if player is negotiated with the player that owns the target country
        for(Player player : PlayerHandler.getGamePlayers()){
            if(player.isCountryOwned(d_riskMap.getCountryById(d_targetCountry))){
                if(d_ctxPlayer.isPlayerNegotiated(player)){
                    return "ERROR: Advance execution failed. " + d_ctxPlayer.getPlayerName() + " has a diplomacy with " + player.getPlayerName();
                }
            }
        }
        return "SUCCESS";
    }

    /**
     * @return armies count to advance.
     */
    public int getArmiesToAdvance() {
        return d_armiesToAdvance;
    }

    public String toString(){
        return "Advance from: " + d_sourceCountry + " to: " + d_targetCountry +  " " + PlayerHandler.getGamePlayers().size() ;
    }

}


