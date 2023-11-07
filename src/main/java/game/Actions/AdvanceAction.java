package game.Actions;

import common.Command;
import entity.Player;
import game.GameCommands;

/**
 * @author Soham
 */
public class AdvanceAction extends GameAction {



    /*
    * // format advance countrynamefrom countrynameto numarmies;
    * List of things to do:
    * 1. Basic checks: If player owns this country and if player has the amount of armies in this country
    * 2. Check if the country that is to be attacked is its neighbour
    * 3. Check if the player owns this country, in that case advance armies and move on
    * 4. Otherwise, attack the country (60% chance the attacking army kills at least one defending army,
    * and 70% chance the defending armies kill at least one attacking army)
    * */

    /**
     * The errors are listed as
     * 1. Advance command invalid
     * 2. Armies greater than what player has
     * 3. Player doesn't own this country
     * 4. The countries are not adjacent;
     */
    @Override
    public void execute(Command p_cmd) {
        if(p_cmd.getCmdAttributes().size() != 3){
            System.out.println(GameCommands.ADVANCE_ERROR_MESSAGES.get(0));
            return;
        }
        int l_countryNameFrom;
        int l_countryNameTo;

        int l_armiesInTargetCountry;
        Player l_currentPlayer = d_context.getCurrentPlayer();
        int l_armiesInAttackersCountry;

        // Using Ids to get the country name
        try {
            l_countryNameFrom =  Integer.parseInt(p_cmd.getCmdAttributes().get(0).getArguments().get(0));
            l_countryNameTo = Integer.parseInt(p_cmd.getCmdAttributes().get(0).getArguments().get(1));
            l_armiesInAttackersCountry = l_currentPlayer.getCountriesOwned().get(d_context.getEngine().getMap().getCountryById(l_countryNameFrom).getArmy()).getArmy();
            l_armiesInTargetCountry = d_context.getEngine().getMap().getCountryById(l_countryNameTo).getArmy();
        } catch (Exception e){
            System.out.println(GameCommands.ADVANCE_ERROR_MESSAGES.get(0));
            return;
        }






    }

    @Override
    public void postExecute() {

    }
}
