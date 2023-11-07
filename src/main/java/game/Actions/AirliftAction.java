package game.Actions;

import common.Command;
import entity.Player;
import game.GameCommands;
import game.Orders.AirliftOrder;
import entity.CardType;

/**
 * @author Soham
 */
public class AirliftAction extends GameAction {


    private int d_sourceCountry;
    private int d_targetCountry;
    private int d_armiesInTargetCountry;
    private final Player d_currentPlayer;
    private int d_armiesInSourceCountry;
    private int d_armiesToAdvance;

    public AirliftAction(){
        d_currentPlayer = d_context.getCurrentPlayer();
    }

    private final int AIRLIFT_ORDER_ERROR = 0;

    private final int AIRLIFT_ORDER_PLAYER_DOESNT_OWN_AIRLIFT_CARD=1;

    private final int AIRLIFT_ORDER_PLAYER_DOESNT_OWN_COUNTRY = 2;

    private final int AIRLIFT_ORDER_MORE_THAN_AVAIALBLE = 3;

    private final int AIRLIFT_ORDER_PLAYER_DOESNT_OWN_TARGET_COUNTRY = 4;

    private final int AIRLIFT_ORDER_SUCCESS = 5;

    /*
     * // format airlift sourcecountryID targetcountryID numarmies;
     * List of things to do:
     * 1. Basic checks: Does player have the airlift card,
     * 2. If player owns this country and if player has the amount of armies in this country
     * 3. Check if the country sending the armies to is owned by player
     * 4. Check if the player owns the target country, in that case airlift armies
     * 5. Otherwise, airlift armies to the country
     * */

    @Override
    public void execute(Command p_cmd) {
        if(p_cmd.getCmdAttributes().size() != 3){
            System.out.println(GameCommands.AIRLIFT_ERROR_MESSAGES.get(AIRLIFT_ORDER_ERROR));
            return;
        }

        // Using Ids to get the country
        try {
            d_sourceCountry =  Integer.parseInt(p_cmd.getCmdAttributes().get(0).getArguments().get(0));
            d_targetCountry = Integer.parseInt(p_cmd.getCmdAttributes().get(0).getArguments().get(1));
            d_armiesToAdvance = Integer.parseInt(p_cmd.getCmdAttributes().get(0).getArguments().get(2));
            d_armiesInSourceCountry = d_context.getEngine().getMap().getCountryArmyById(d_sourceCountry);
            d_armiesInTargetCountry = d_context.getEngine().getMap().getCountryArmyById(d_targetCountry);

        } catch (Exception e){
            System.out.println(GameCommands.AIRLIFT_ERROR_MESSAGES.get(AIRLIFT_ORDER_ERROR));
            return;
        }
        int l_canProcessCommand = checkCommandValidity();
        if(l_canProcessCommand == AIRLIFT_ORDER_SUCCESS){
            d_currentPlayer.setTempOrder(new AirliftOrder(d_currentPlayer, d_sourceCountry, d_targetCountry, d_armiesToAdvance));
            d_currentPlayer.issueOrder();
        } else{
            System.out.println(GameCommands.AIRLIFT_ERROR_MESSAGES.get(l_canProcessCommand));
            d_execStatus = ActionExecStatus.Fail;
        }

    }

    @Override
    public void postExecute() {

    }
    /**
     * Checks if the airlift command is valid
     * @return an integer value on which the caller determines if the command passed the check
     * The errors are listed as
     * 1. Airlift command invalid
     * 2. Player doesnt have the Airlift card
     * 3. Player doesn't own this country
     * 4. Armies greater than what player has on that country
     * 5. The target country is not owned by the player;
     */
    private int checkCommandValidity(){

        //check if player has the airlift card
        if(d_currentPlayer.isCardAvailable(CardType.Airlift)){
            return AIRLIFT_ORDER_PLAYER_DOESNT_OWN_AIRLIFT_CARD;
        }

        // checks if player owns the country
        if(d_currentPlayer.isCountryOwned(d_context.getEngine().getMap().getCountryById(d_sourceCountry))) {
            return AIRLIFT_ORDER_PLAYER_DOESNT_OWN_COUNTRY;
        }
        // checks if that country has enough armies to send
        else if(d_armiesInSourceCountry > d_context.getEngine().getMap().getCountryArmyById(d_sourceCountry)){
            return AIRLIFT_ORDER_MORE_THAN_AVAIALBLE;
        }
        // check if target country is owned by the player
        else if(d_currentPlayer.isCountryOwned(d_context.getEngine().getMap().getCountryById(d_targetCountry))) {
            return AIRLIFT_ORDER_PLAYER_DOESNT_OWN_TARGET_COUNTRY;
        }
        return AIRLIFT_ORDER_SUCCESS;
    }
}

