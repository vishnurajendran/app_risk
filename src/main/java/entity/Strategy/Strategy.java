package entity.Strategy;

import game.Data.StrategyData;
import game.Orders.Order;

/**
 * @author Soham
 */
public abstract class Strategy{
    protected StrategyData d_strategyData;

    public abstract Order decide();

    public void setContext(StrategyData p_strategyData) {
        this.d_strategyData = p_strategyData;
    }
}
