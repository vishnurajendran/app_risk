package game;

/**
 * @author Soham
 */
abstract public class Order {
    protected Player d_ctxPlayer;
    protected int d_targetCountry;
    protected int d_armiesToDeploy;
    public abstract void executeOrder();
}
