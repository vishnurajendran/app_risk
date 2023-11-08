package game.Actions;

import common.Command;
import entity.CardType;
import entity.Country;
import entity.Player;
import entity.PlayerHandler;
import game.GameCommands;
import game.Orders.BombOrder;

/**
 * Functionality related to bombing a country owned by the current player.
 * This action allows the current player to attempt a bomb attack on a specified country
 * if certain conditions are met, such as owning the Bomb card, owning the target country,
 * having the target country as a neighbor, and having a diplomacy with the target country's owner.
 *
 * @author Rachit
 */
public class BombAction extends GameAction {

    Player d_currentPlayer;
    int d_countryToBomb;

    /**
     * Executes the bomb action based on the provided command.
     * Checks if the command is valid and, if so, adds a BombOrder to the player's orders.
     *
     * @param p_cmd Command to run the action with.
     */
    @Override
    public void execute(Command p_cmd) {
        // Check the validity of the command
        if (p_cmd.getCmdAttributes().size() != 1) {
            System.out.println(GameCommands.BOMB_ERROR_MESSAGES.get(0));
            d_execStatus = ActionExecStatus.Fail;
            return;
        }

        // Extract the country to bomb from the command
        d_countryToBomb = Integer.parseInt(p_cmd.getCmdAttributes().get(0).getArguments().get(0));
        d_currentPlayer = d_context.getCurrentPlayer();

        // Check if the player can issue the bomb order
        int canIssueOrder = checkCommands();

        // If all checks pass, add a BombOrder to the player's orders
        if (canIssueOrder == 5) {
            d_currentPlayer.issueOrder(new BombOrder(d_currentPlayer, d_countryToBomb, d_context.getEngine().getMap()));
        } else {
            System.out.println(GameCommands.BOMB_ERROR_MESSAGES.get(canIssueOrder));
            d_execStatus = ActionExecStatus.Fail;
        }
    }

    /**
     * Performs post-execution actions for the bomb action.
     * (This method is currently empty.)
     */
    @Override
    public void postExecute() {
        // Perform post-execution actions if needed
    }

    /**
     * Checks various conditions to determine if a bomb order can be issued:
     * 1. Checks if the player has the Bomb card available.
     * 2. Checks if the player owns the target country.
     * 3. Checks if the player has the target country as a neighbor.
     * 4. Checks if the player has a diplomacy with the target country's owner.
     *
     * @return An integer representing the result of the checks:
     * - 1: Player does not have the Bomb card.
     * - 2: Player does not own the target country.
     * - 3: Player does not have the target country as a neighbor.
     * - 4: Player does not have a diplomacy with the target country's owner.
     * - 5: All checks pass, and the player can issue the bomb order.
     */
    private int checkCommands() {
        int isCommandValid = 4;

        // Check if the player has the Bomb card available
        if (!d_currentPlayer.isCardAvailable(CardType.Bomb)) {
            isCommandValid = 1;
            return isCommandValid;
        }

        // Check if the player owns the target country
        else if (d_currentPlayer.isCountryOwned(d_context.getEngine().getMap().getCountryById(d_countryToBomb))) {
            isCommandValid = 2;
            return isCommandValid;
        }

        // Check if the player has the target country as one of the neighbors
        for (Country country : d_currentPlayer.getCountriesOwned()) {
            if (country.isNeighbour(d_countryToBomb)) {
                isCommandValid = 5;
                return isCommandValid;
            }
            isCommandValid = 3;
        }

        // Check if the player has a diplomacy with the target country's owner
        for (Player player : PlayerHandler.getGamePlayers()) {
            if (player.isCountryOwned(d_context.getEngine().getMap().getCountryById(d_countryToBomb))) {
                if (d_currentPlayer.isPlayerNegotiated(player)) {
                    isCommandValid = 4;
                    return isCommandValid;
                }
            }
        }

        return isCommandValid;
    }
}
