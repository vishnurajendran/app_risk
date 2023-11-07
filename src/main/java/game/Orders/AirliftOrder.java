package game.Orders;

import entity.Country;
import entity.Player;
import entity.RiskMap;

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
    public AirliftOrder(Player p_player, int sourceCountry, int p_targetCountry, int p_armiesToAirlift, RiskMap p_riskMap) {
        d_armiesToAdvance = p_armiesToAirlift;
        d_ctxPlayer = p_player;
        d_targetCountry = p_targetCountry;
        d_sourceCountry = sourceCountry;
        d_riskMap = p_riskMap;
    }
    @Override
    public void executeOrder() {
        if (d_ctxPlayer != null && d_riskMap != null) {
            Country sourceCountry = d_riskMap.getCountryById(d_sourceCountry);
            Country targetCountry = d_riskMap.getCountryById(d_targetCountry);

            if (sourceCountry != null && targetCountry != null) {
                // Check if the player owns the source and target countries
                if (d_ctxPlayer.isCountryOwned(sourceCountry) && d_ctxPlayer.isCountryOwned(targetCountry)) {
                    // Check if the source country has enough armies for the airlift
                    if (d_armiesToAirlift <= sourceCountry.getArmy()) {
                        // Move armies from source to target country
                        sourceCountry.setArmy(sourceCountry.getArmy() - d_armiesToAirlift);
                        targetCountry.setArmy(targetCountry.getArmy() + d_armiesToAirlift);



                        // Print a message or log the action if needed
                        System.out.println("Airlift successful: Moved " + d_armiesToAirlift + " armies from " +
                                sourceCountry.getName() + " to " + targetCountry.getName());
                    } else {
                        System.out.println("Airlift order failed: Not enough armies in the source country.");
                    }
                } else {
                    System.out.println("Airlift order failed: Player doesn't own the source or target country.");
                }
            } else {
                System.out.println("Airlift order failed: Invalid source or target country.");
            }
        } else {
            System.out.println("Airlift order failed: Player or RiskMap is null.");
        }
    }


    }

