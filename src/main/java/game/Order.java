package game;

/**
 * This Class is used to initialize orders that the player sets
 * executeOrder() Method is implemented by the child class DeployOrder
 *
 * @author Soham
 */
abstract public class Order {
    protected Player d_ctxPlayer;
    protected int d_targetCountry;
    protected int d_armiesToDeploy;

    public abstract void executeOrder();
}
