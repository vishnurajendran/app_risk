package game.Actions;

import common.Command;
import entity.Player;
import game.GameCommands;
import game.Orders.AirliftOrder;
import entity.CardType;

/**
 * AirliftAction represents the action of performing an airlift in the Risk game.
 * Airlift allows a player to transfer armies from one of their countries
 * to another in a single turn, even if not adjacent.
 *
 * @author Shravani
 */
public class AirliftAction extends GameAction {

    private int d_sourceCountry;
    private int d_targetCountry;
    private int d_armiesInTargetCountry;
    private Player d_currentPlayer;
    private int d_armiesInSourceCountry;
    private int d_armiesToAirlift;

    /**
     * Constructs an AirliftAction.
     * Initializes the current player to the player who issues the Airlift action.
     */
    public AirliftAction() {

    }

    private final int AIRLIFT_ORDER_ERROR = 0;
    private final int AIRLIFT_ORDER_PLAYER_DOESNT_OWN_AIRLIFT_CARD = 1;
    private final int AIRLIFT_ORDER_PLAYER_DOESNT_OWN_COUNTRY = 2;
    private final int AIRLIFT_ORDER_MORE_THAN_AVAILABLE = 3;
    private final int AIRLIFT_ORDER_PLAYER_DOESNT_OWN_TARGET_COUNTRY = 4;
    private final int AIRLIFT_ORDER_SUCCESS = 5;

    /**
     * Executes the Airlift action based on the provided command.
     *
     * @param p_cmd The command specifying the airlift action in the format:
     * "airlift sourceCountryID targetCountryID numArmies"
     *  where sourceCountryID is the ID of the source country,
     *  targetCountryID is the ID of the target country, and
     *  numArmies is the number of armies to airlift.
     */
    @Override
    public void execute(Command p_cmd) {
        d_currentPlayer = d_context.getCurrentPlayer();
        if (p_cmd.getCmdAttributes().isEmpty()){
        System.out.println(GameCommands.AIRLIFT_ERROR_MESSAGES.get(AIRLIFT_ORDER_ERROR));
        d_execStatus = ActionExecStatus.Fail;
        return;
        }
        // Check if the command format is valid
            else if (p_cmd.getCmdAttributes().get(0).getArguments().size() != 3) {
            System.out.println(GameCommands.AIRLIFT_ERROR_MESSAGES.get(AIRLIFT_ORDER_ERROR));
            d_execStatus = ActionExecStatus.Fail;
            return;
        }

        // Using IDs to get the source and target countries
        try {
            d_sourceCountry = Integer.parseInt(p_cmd.getCmdAttributes().get(0).getArguments().get(0));
            d_targetCountry = Integer.parseInt(p_cmd.getCmdAttributes().get(0).getArguments().get(1));
            d_armiesToAirlift = Integer.parseInt(p_cmd.getCmdAttributes().get(0).getArguments().get(2));
            d_armiesInSourceCountry = d_context.getEngine().getMap().getCountryArmyById(d_sourceCountry);
            d_armiesInTargetCountry = d_context.getEngine().getMap().getCountryArmyById(d_targetCountry);
        } catch (Exception e) {
            System.out.println(GameCommands.AIRLIFT_ERROR_MESSAGES.get(AIRLIFT_ORDER_ERROR));
            d_execStatus = ActionExecStatus.Fail;
            return;
        }

        int l_canProcessCommand = checkCommandValidity();
        if (l_canProcessCommand == AIRLIFT_ORDER_SUCCESS) {
            d_currentPlayer.issueOrder(new AirliftOrder(d_currentPlayer, d_sourceCountry, d_targetCountry, d_armiesToAirlift, d_context.getEngine().getMap()));
        } else {
            System.out.println(GameCommands.AIRLIFT_ERROR_MESSAGES.get(l_canProcessCommand));
            d_execStatus = ActionExecStatus.Fail;
        }
    }

    @Override
    public void postExecute() {
        // Implementation for any post-execution actions, if needed.
    }

    /**
     * Checks if the airlift command is valid.
     *
     * @return An integer value representing the result of the command validity check.
     * The possible error codes are:
     * 1. AIRLIFT_ORDER_ERROR: Airlift command is invalid.
     * 2. AIRLIFT_ORDER_PLAYER_DOESNT_OWN_AIRLIFT_CARD: Player doesn't have the Airlift card.
     * 3. AIRLIFT_ORDER_PLAYER_DOESNT_OWN_COUNTRY: Player doesn't own the source country.
     * 4. AIRLIFT_ORDER_MORE_THAN_AVAILABLE: Armies requested for airlift exceed what the source country has.
     * 5. AIRLIFT_ORDER_PLAYER_DOESNT_OWN_TARGET_COUNTRY: The target country is not owned by the player.
     */
    private int checkCommandValidity() {
        // Check if the player has the airlift card
        if (!d_currentPlayer.isCardAvailable(CardType.Airlift)) {
            return AIRLIFT_ORDER_PLAYER_DOESNT_OWN_AIRLIFT_CARD;
        }

        // Check if the player owns the source country
        if (!d_currentPlayer.isCountryOwned(d_context.getEngine().getMap().getCountryById(d_sourceCountry))) {
            return AIRLIFT_ORDER_PLAYER_DOESNT_OWN_COUNTRY;
        }

        // Check if the source country has enough armies for the airlift
        else if (d_armiesToAirlift > d_context.getEngine().getMap().getCountryArmyById(d_sourceCountry)) {
            return AIRLIFT_ORDER_MORE_THAN_AVAILABLE;
        }

        // Check if the target country is owned by the player
        else if (!d_currentPlayer.isCountryOwned(d_context.getEngine().getMap().getCountryById(d_targetCountry))) {
            return AIRLIFT_ORDER_PLAYER_DOESNT_OWN_TARGET_COUNTRY;
        }

        return AIRLIFT_ORDER_SUCCESS;
    }
}
