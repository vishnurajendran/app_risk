package game.Orders;

import entity.Player;
import entity.RiskMap;

/**
 * This class will assign orders to the player
 * Once the player is done deploying, it will execute using executeOrder() method.
 *
 * @author Soham
 */
public class DeployOrder extends Order {

    private int d_armiesToDeploy;
    /**
     * default constructor
     */
    public DeployOrder() {
        d_armiesToDeploy = 0;
        d_ctxPlayer = null;
        d_targetCountry = 0;
    }

    /**
     * parametrised constructor to initialise the
     * DeployOrder object.
     *
     * @param p_player         player in context for this order
     * @param p_armiesToDeploy no. of armies to deploy
     * @param p_targetCountry  Id of the country to target this order on.
     */
    public DeployOrder(Player p_player, int p_armiesToDeploy, int p_targetCountry, RiskMap p_riskMap) {
        d_ctxPlayer = p_player;
        d_armiesToDeploy = p_armiesToDeploy;
        d_targetCountry = p_targetCountry;
        d_riskMap = p_riskMap;
    }

    /**
     * This method will assign the country their armies as passed by the player
     */
    @Override
    public void executeOrder() {
        String canExecute = canExecuteCommand();
        if(canExecute.equals("SUCCESS")){
            d_ctxPlayer.assignReinforcementsToCountry(d_targetCountry, d_armiesToDeploy);
        } else{
            System.out.println(canExecute);
        }

    }

    public String canExecuteCommand(){
        // checks if player owns the country
        if(!d_ctxPlayer.isCountryOwned(d_riskMap.getCountryById(d_targetCountry))) {
            return "ERROR: Advance execution failed. " + d_ctxPlayer.getPlayerName() + " doesn't own the country " + d_riskMap.getCountryById(d_targetCountry).getName() + " anymore";
        }
        return "SUCCESS";
    }
    /**
     * overriden to print deploy order details
     * @return a string with order details
     */
    @Override
    public String toString() {
        return "[ Deploy " + d_armiesToDeploy + " units -> " + d_targetCountry + " ]";
    }
}
