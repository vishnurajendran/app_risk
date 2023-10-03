package game;

/**
 * @author Soham
 */
public class DeployOrder extends Order {
    public DeployOrder(Player p_player, int p_armiesToDeploy, int p_targetCountry){
        d_ctxPlayer = p_player;
    }

    @Override
    public void executeOrder() {
        d_ctxPlayer.nextOrder();
    }
}
