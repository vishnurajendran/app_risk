package game.Orders;

import entity.Player;

public class AirliftOrder extends Order{

    /**
     * Default Constructor
     */
    public AirliftOrder(){
        d_armiesToAirlift = 0;
        d_ctxPlayer = null;
        d_sourceCountry = 0;
        d_targetCountry = 0;

    }

    /**
     * Constructor with assigned value
     * @param p_player the current active player
     * @param sourceCountry the country from which the armies are to be airlifted
     * @param p_targetCountry the country to which the armies are to be airlifted
     * @param p_armiesToAirlift number of armies from source to targetCountry
     */
    public AirliftOrder(Player p_player, int sourceCountry, int p_targetCountry, int p_armiesToAirlift){
        d_armiesToAdvance = p_armiesToAirlift;
        d_ctxPlayer = p_player;
        d_targetCountry = p_targetCountry;
        d_sourceCountry = sourceCountry;
    }
    @Override
    public void executeOrder() {

    }
}
