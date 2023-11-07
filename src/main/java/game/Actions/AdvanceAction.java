package game.Actions;

import common.Command;
import entity.Player;
import game.GameCommands;
import game.Orders.AdvanceOrder;

/**
 * @author Soham
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

    private final int ADVANCE_ORDER_MORE_THAN_AVAIALBLE = 2;

    private final int ADVANCE_ORDER_COUNTRIES_NOT_ADJACENT = 3;

    private final int ADVANCE_ORDER_SUCCESS = 4;

    /*
    * // format advance countrynamefrom countrynameto numarmies;
    * List of things to do:
    * 1. Basic checks: If player owns this country and if player has the amount of armies in this country
    * 2. Check if the country that is to be attacked is its neighbour
    * 3. Check if the player owns this country, in that case advance armies and move on
    * 4. Otherwise, attack the country (60% chance the attacking army kills at least one defending army,
    * and 70% chance the defending armies kill at least one attacking army)
    * */

    @Override
    public void execute(Command p_cmd) {
        if(p_cmd.getCmdAttributes().size() != 3){
            System.out.println(GameCommands.ADVANCE_ERROR_MESSAGES.get(0));
            return;
        }

        // Using Ids to get the country
        try {
            d_sourceCountry =  Integer.parseInt(p_cmd.getCmdAttributes().get(0).getArguments().get(0));
            d_targetCountry = Integer.parseInt(p_cmd.getCmdAttributes().get(0).getArguments().get(1));
            d_armiesToAdvance = Integer.parseInt(p_cmd.getCmdAttributes().get(0).getArguments().get(2));
            d_armiesInSourceCountry = d_currentPlayer.getCountriesOwned().get(d_context.getEngine().getMap().getCountryById(d_sourceCountry).getArmy()).getArmy();
            d_armiesInTargetCountry = d_context.getEngine().getMap().getCountryById(d_targetCountry).getArmy();

        } catch (Exception e){
            System.out.println(GameCommands.ADVANCE_ERROR_MESSAGES.get(0));
            return;
        }
        int l_canProcessCommand = checkCommandValidity();
        if(l_canProcessCommand == ADVANCE_ORDER_SUCCESS){
            d_currentPlayer.setTempOrder(new AdvanceOrder(d_currentPlayer, d_sourceCountry, d_targetCountry, d_armiesToAdvance));
            d_currentPlayer.issueOrder();
        } else{
            System.out.println(GameCommands.ADVANCE_ERROR_MESSAGES.get(l_canProcessCommand));
            d_execStatus = ActionExecStatus.Fail;
        }

    }

    @Override
    public void postExecute() {

    }


    // TODO: Add the negotiate check for advancing

    /**
     * Checks if the advance command is valid
     * @return an integer value on which the caller determines if the command passed the check
     * The errors are listed as
     * 1. Advance command invalid
     * 2. Player doesn't own this country
     * 3. Armies greater than what player has on that country
     * 4. The countries are not adjacent;
     */
    private int checkCommandValidity(){

        // checks if player owns the country
        if(d_currentPlayer.getCountriesOwned().stream().noneMatch((a) -> a.getDId() == d_armiesInSourceCountry)) {
            return ADVANCE_ORDER_PLAYER_DOESNT_OWN_COUNTRY;
        }
        // checks if that country has enough armies to deploy
        else if(d_armiesInSourceCountry > d_context.getEngine().getMap().getCountryArmyById(d_sourceCountry)){
            return ADVANCE_ORDER_MORE_THAN_AVAIALBLE;
        }
        // check if countries are adjacent
        else if(!d_context.getEngine().getMap().getCountryById(d_sourceCountry).isNeighbour(d_targetCountry)){
            return ADVANCE_ORDER_COUNTRIES_NOT_ADJACENT;
        }
        return ADVANCE_ORDER_SUCCESS;
    }
}
