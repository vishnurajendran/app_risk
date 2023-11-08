package game.Orders;

import entity.Country;
import entity.Player;
import entity.RiskMap;

/**
 * The BombOrder class represents an order to execute a bomb action in the Risk game.
 * It allows a player to reduce the number of armies in a target country by half, provided
 * the target country is not owned by the current player. This class extends the Order class.
 *
 * @see Order
 */
public class BombOrder extends Order {

    private Player d_CtxPlayer;
    private int d_TargetCountry;
    private RiskMap d_RiskMap;

    /**
     * Default constructor for the {@code BombOrder} class.
     * Initializes the context player, target country ID and the game's Risk map to null.
     */
    public BombOrder() {
        d_CtxPlayer = null;
        d_TargetCountry = 0;
        d_RiskMap = null;
    }

    /**
     * Constructor for the {@code BombOrder} class.
     *
     * @param p_Player       The player who is issuing the bomb order.
     * @param p_TargetCountry The ID of the target country to be bombed.
     * @param p_RiskMap      The RiskMap representing the game map.
     */
    public BombOrder(Player p_Player, int p_TargetCountry, RiskMap p_RiskMap) {
        d_CtxPlayer = p_Player;
        d_TargetCountry = p_TargetCountry;
        d_RiskMap = p_RiskMap;
    }

    /**
     * Executes the bomb order by reducing the number of armies in the target country by half.
     * The action is executed if the current player does not own the target country and
     * the target country has more than 1 army.
     * If the action is successful, the game state is updated, and a message is printed.
     */
    @Override
    public void executeOrder() {
        // Get the current player
        Player l_CurrentPlayer = d_CtxPlayer;

        // Get the target country by its ID from the game's map
        Country l_TargetCountry = d_RiskMap.getCountryById(d_TargetCountry);

        // Check if the current player can perform the bomb action based on the game rules
        if (l_CurrentPlayer != null && l_TargetCountry != null) {
            // Check if the target country is not owned by the current player
            if (!l_CurrentPlayer.isCountryOwned(l_TargetCountry)) {
                // Reduce the number of armies in the target country by half
                int l_ArmiesInTargetCountry = l_TargetCountry.getArmy();
                if (l_ArmiesInTargetCountry > 1) {
                    int l_ArmiesToReduce = l_ArmiesInTargetCountry / 2; // Reduce by half
                    l_TargetCountry.setArmy(l_ArmiesInTargetCountry - l_ArmiesToReduce);

                    // Updating the game state accordingly
                    System.out.println("Bomb action executed: " + l_ArmiesToReduce + " armies reduced in " + l_TargetCountry.getName());
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
