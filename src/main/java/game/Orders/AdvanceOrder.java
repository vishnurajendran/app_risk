package game.Orders;

import entity.Player;
import entity.PlayerHandler;
import entity.RiskMap;

import java.util.Random;

/**
 * @author Soham
 * this class executes the advance orders present in the players list of orders.
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
        int l_armiesInTargetCountry = d_riskMap.getCountryArmyById(d_targetCountry);
        int l_armiesInSourceCountryAdvanced = d_armiesToAdvance;
        // removes the no of armies form sources country
        d_riskMap.getCountryById(d_sourceCountry).setArmy(d_riskMap.getCountryById(d_sourceCountry).getArmy() - d_armiesToAdvance);
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

        Player l_targetCountryOwner = null;
        for (Player player : PlayerHandler.getGamePlayers()) {
            if (player.isCountryOwned(d_riskMap.getCountryById(d_targetCountry))) {
                player.removeCountry(d_riskMap.getCountryById(d_targetCountry));
                l_targetCountryOwner = player;
            }
        }
        if (l_armiesInTargetCountry <= 0) {
            if (l_targetCountryOwner != null) {
                System.out.println(d_ctxPlayer.getPlayerName() + " conquered " + d_riskMap.getCountryById(d_targetCountry).getName()
                        + "that was owned by " + l_targetCountryOwner.getPlayerName());
            }

            d_ctxPlayer.assignCountry(d_riskMap.getCountryById(d_targetCountry), l_armiesInSourceCountryAdvanced);
            d_riskMap.getCountryById(d_targetCountry).setArmy(l_armiesInSourceCountryAdvanced);
        } else {
            if(l_targetCountryOwner!=null){
                System.out.println(l_targetCountryOwner.getPlayerName() + " defended an attack from "
                        + d_ctxPlayer.getPlayerName() + " on " + d_riskMap.getCountryById(d_targetCountry).getName());
            }
            d_riskMap.getCountryById(d_targetCountry).setArmy(l_armiesInTargetCountry);
        }
    }
}
