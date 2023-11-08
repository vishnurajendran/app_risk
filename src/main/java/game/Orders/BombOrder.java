package game.Orders;
import entity.Country;
import entity.Player;
import entity.RiskMap;


/**
 * Bomb order class used to execute the bomb order.
 */
public class BombOrder extends Order {

    public BombOrder(){
        d_ctxPlayer = null;
        d_targetCountry = 0;
        d_sourceCountry = 0;
        d_armiesToAdvance = 0;
        d_armiesToAirlift = 0;
        d_riskMap = null;
    }


    public BombOrder(Player p_player, int p_targetCountry, RiskMap p_riskMap) {
        d_ctxPlayer = p_player;
        d_targetCountry = p_targetCountry;
        d_riskMap = p_riskMap;
    }

    @Override
    public void executeOrder() {
        // Get the current player
        Player currentPlayer = d_ctxPlayer;

        // Get the target country by its ID from the game's map
        Country targetCountry = d_riskMap.getCountryById(d_targetCountry);

        // Check if the current player can perform the bomb action based on the game rules
        if (currentPlayer != null && targetCountry != null) {
            // Check if the target country is not owned by the current player
            if (!currentPlayer.isCountryOwned(targetCountry)) {
                // Reduce the number of armies in the target country
                int armiesInTargetCountry = targetCountry.getArmy();
                if (armiesInTargetCountry > 1) {
                    // You can define your own logic for how many armies are reduced in the bomb action.
                    int armiesToReduce = Math.min(armiesInTargetCountry - 1, 3); // Example: Reduce up to 3 armies
                    targetCountry.setArmy(armiesInTargetCountry - armiesToReduce);

                    // Update the game state accordingly
                    System.out.println("Bomb action executed: " + armiesToReduce + " armies reduced in " + targetCountry.getName());
                } else {
                    System.out.println("Bomb action failed: The target country doesn't have enough armies.");
                }
            } else {
                System.out.println("Bomb action failed: You cannot bomb a country you own.");
            }
        } else {
            // Handle the case where the current player or target country is not found.
            System.out.println("Bomb action failed: Invalid player or target country.");
        }
    }
}


