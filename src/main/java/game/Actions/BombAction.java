package game.Actions;

import common.Command;
import entity.CardType;
import entity.Country;
import entity.Player;
import entity.RiskMap;
import game.Orders.BombOrder;

import java.util.ArrayList;

import static java.util.Objects.isNull;

/**
 * Functionality related to bomb a country owned by the current player,
 * @author Rachit
 */
public class BombAction extends GameAction {

    /*
    Things to check for bomb
    1. check if player owns the card
    2. check if player itself owns the country
    3. check if the country is adjacent to one of the player's countries
    4. check if the player has negotiated with the target country's player
     */

    /**
     * Do checks before adding order and if all checks pass, add this order to player.
     * @param p_cmd command to run the action with.
     */
    @Override
    public void execute(Command p_cmd) {
        if(p_cmd.getCmdAttributes().size()!=1){
            System.out.println("ERROR: The bomb command isn't valid, please try again");
            d_execStatus = ActionExecStatus.Fail;
            return;
        }
        int countryToBomb = Integer.parseInt(p_cmd.getCmdAttributes().get(0).getArguments().get(0));
    }

        @Override
        public void postExecute() {

        }
}