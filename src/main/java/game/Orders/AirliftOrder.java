package game.Orders;

import entity.Country;
import entity.Player;
import entity.RiskMap;

/**
 * AirliftOrder represents an execute order to airlift armies from one country to another in the Risk game.
 */
public class AirliftOrder extends Order {

    private int d_armiesToAirlift;

    /**
     * Default Constructor.
     */
    public AirliftOrder() {
        d_armiesToAirlift = 0;
        d_ctxPlayer = null;
        d_sourceCountry = 0;
        d_targetCountry = 0;
    }

    /**
     * Constructor with assigned values.
     *
     * @param p_player          The current active player.
     * @param sourceCountry     The country from which the armies are to be airlifted.
     * @param p_targetCountry   The country to which the armies are to be airlifted.
     * @param p_armiesToAirlift The number of armies to airlift from source to targetCountry.
     * @param p_riskMap         The RiskMap for game.
     */
    public AirliftOrder(Player p_player, int sourceCountry, int p_targetCountry, int p_armiesToAirlift, RiskMap p_riskMap) {
        d_armiesToAirlift = p_armiesToAirlift;
        d_ctxPlayer = p_player;
        d_targetCountry = p_targetCountry;
        d_sourceCountry = sourceCountry;
        d_riskMap = p_riskMap;
    }

    /**
     * Executes the Airlift order, moving armies from the source country to the target country.
     */
    @Override
    public void executeOrder() {
        if (d_ctxPlayer != null && d_riskMap != null) {
            Country sourceCountry = d_riskMap.getCountryById(d_sourceCountry);
            Country targetCountry = d_riskMap.getCountryById(d_targetCountry);

            if (sourceCountry != null && targetCountry != null) {

                // Check if the source country has enough armies for the airlift
                if (d_armiesToAirlift < sourceCountry.getArmy()) {
                    // Move armies from source to target country
                    sourceCountry.setArmy(sourceCountry.getArmy() - d_armiesToAirlift);
                    targetCountry.setArmy(targetCountry.getArmy() + d_armiesToAirlift);

                    // Print a message
                    System.out.println("Airlift successful: Moved " + d_armiesToAirlift + " armies from " +
                            sourceCountry.getName() + " to " + targetCountry.getName());
                } else {
                    System.out.println("Airlift order failed: Not enough armies in the source country.");
                }
            } else {
                System.out.println("Airlift order failed: Invalid source or target country.");
            }
        } else {
            System.out.println("Airlift order failed: Player or RiskMap is null.");
        }
    }
}
