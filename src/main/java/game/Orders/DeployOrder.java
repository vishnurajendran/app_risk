package game.Orders;

import entity.Player;

/**
 * This class will assign orders to the player
 * Once the player is done deploying, it will execute using executeOrder() method.
 *
 * @author Soham
 */
public class DeployOrder extends Order {

    /**
     * default constructor
     */
    public DeployOrder() {
        d_armiesToAdvance = 0;
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
    public DeployOrder(Player p_player, int p_armiesToDeploy, int p_targetCountry) {
        d_ctxPlayer = p_player;
        d_armiesToAdvance = p_armiesToDeploy;
        d_targetCountry = p_targetCountry;
    }

    /**
     * This method will assign the country their armies as passed by the player
     */
    @Override
    public void executeOrder() {
        d_ctxPlayer.assignReinforcementsToCountry(d_targetCountry, d_armiesToAdvance);
    }
}
