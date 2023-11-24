package game.States.Strategy;

import game.Data.Context;
import game.Orders.Order;

/**
 * @author Soham
 */
public abstract class Strategy{
    protected Context d_context;

    public abstract Order decide();

    public void setContext(Context p_context) {
        this.d_context = p_context;
    }
}
