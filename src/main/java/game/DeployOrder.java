package game;

/**
 * This class will assign orders to the player
 * Once the player is done deploying, it will execute using executeOrder() method.
 *
 * @author Soham
 */
public class DeployOrder extends Order {
    public DeployOrder(Player p_player, int p_armiesToDeploy, int p_targetCountry) {
        d_ctxPlayer = p_player;
        d_armiesToDeploy = p_armiesToDeploy;
        d_targetCountry = p_targetCountry;
    }

    /**
     * This method will assign the country their armies as passed by the player
     */
    @Override
    public void executeOrder() {
        d_ctxPlayer.assignReinforcementsToCountry(d_targetCountry, d_armiesToDeploy);
    }
}
