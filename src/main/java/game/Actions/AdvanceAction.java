package game.Actions;

import common.Command;
import entity.Player;
import entity.PlayerHandler;
import game.GameCommands;
import game.Orders.AdvanceOrder;

/**
 * @author Soham
 *
 * this class checks all the necessary conditions for executing advance command
 * if they pass, then it creates an order in the player
 * which can be executed once the player runs execute
 */
public class AdvanceAction extends GameAction {


    private int d_sourceCountry;
    private int d_targetCountry;
    private int d_armiesInTargetCountry;
    private final Player d_currentPlayer;
    private int d_armiesInSourceCountry;
    private int d_armiesToAdvance;

    public AdvanceAction(){
         d_currentPlayer = d_context.getCurrentPlayer();
    }

    private final int ADVANCE_ORDER_ERROR = 0;

    private final int ADVANCE_ORDER_PLAYER_DOESNT_OWN_COUNTRY = 1;

    private final int ADVANCE_ORDER_MORE_THAN_AVAILABLE = 2;

    private final int ADVANCE_ORDER_COUNTRIES_NOT_ADJACENT = 3;

    public static final int ADVANCE_ORDER_NEGOTIATED_PLAYER = 4;

    public static final int ADVANCE_ORDER_SUCCESS = 5;


    /**
     * This method initialises all the variables with the command attributes
     * It also checks if the command is of correct length.
     * Then, if everything runs, it adds advance order to the list of orders in Player.
     * @param p_cmd command to run the action with.
     */
    @Override
    public void execute(Command p_cmd) {
        if(p_cmd.getCmdAttributes().size() != 3){
            System.out.println(GameCommands.ADVANCE_ERROR_MESSAGES.get(ADVANCE_ORDER_ERROR));
            d_execStatus = ActionExecStatus.Fail;
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
            System.out.println(GameCommands.ADVANCE_ERROR_MESSAGES.get(ADVANCE_ORDER_ERROR));
            d_execStatus = ActionExecStatus.Fail;
            return;
        }
        int l_canProcessCommand = checkCommandValidity();
        if(l_canProcessCommand == ADVANCE_ORDER_SUCCESS){
            d_currentPlayer.setTempOrder(new AdvanceOrder(d_currentPlayer, d_sourceCountry, d_targetCountry, d_armiesToAdvance, d_context.getEngine().getMap()));
            d_currentPlayer.issueOrder();
        } else{
            System.out.println(GameCommands.ADVANCE_ERROR_MESSAGES.get(l_canProcessCommand));
            d_execStatus = ActionExecStatus.Fail;
        }

    }

    @Override
    public void postExecute() {

    }

    /**
     * Checks if the advance command is valid
     * @return an integer value on which the caller determines if the command passed the check
     * The errors are listed as
     * 1. Advance command invalid
     * 2. Player doesn't own this country
     * 3. Armies greater than what player has on that country
     * 4. The countries are not adjacent;
     * 5. Checks if current player has negotiated with one of the players that owns the target country
     */
    private int checkCommandValidity(){

        // checks if player owns the country
        if(!d_currentPlayer.isCountryOwned(d_context.getEngine().getMap().getCountryById(d_sourceCountry))) {
            return ADVANCE_ORDER_PLAYER_DOESNT_OWN_COUNTRY;
        }
        // checks if that country has enough armies to deploy
        else if(d_armiesToAdvance > d_context.getEngine().getMap().getCountryArmyById(d_sourceCountry)){
            return ADVANCE_ORDER_MORE_THAN_AVAILABLE;
        }
        // check if countries are adjacent
        else if(!d_context.getEngine().getMap().getCountryById(d_sourceCountry).isNeighbour(d_targetCountry)){
            return ADVANCE_ORDER_COUNTRIES_NOT_ADJACENT;
        }
        // check if player is negotiated with the player that owns the target country
        for(Player player : PlayerHandler.getGamePlayers()){
            if(player.isCountryOwned(d_context.getEngine().getMap().getCountryById(d_targetCountry))){
                if(d_currentPlayer.isPlayerNegotiated(player)){
                    return ADVANCE_ORDER_NEGOTIATED_PLAYER;
                }
            }
        }
        return ADVANCE_ORDER_SUCCESS;
    }
}
