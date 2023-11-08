package game.Actions;

import common.Command;
import entity.CardType;
import entity.Country;
import entity.Player;
import entity.PlayerHandler;
import game.GameCommands;
import game.Orders.BombOrder;

/**
 * Functionality related to bomb a country owned by the current player,
 *
 * @author Rachit
 */
public class BombAction extends GameAction {

    Player d_currentPlayer;

    int d_countryToBomb;





    /**
     * Do checks before adding order and if all checks pass, add this order to player.
     *
     * @param p_cmd command to run the action with.
     */
    @Override
    public void execute(Command p_cmd) {
        if (p_cmd.getCmdAttributes().size() != 1) {
            System.out.println(GameCommands.BOMB_ERROR_MESSAGES.get(0));
            d_execStatus = ActionExecStatus.Fail;
            return;
        }

        d_countryToBomb = Integer.parseInt(p_cmd.getCmdAttributes().get(0).getArguments().get(0));
        d_currentPlayer = d_context.getCurrentPlayer();
        int canIssueOrder = checkCommands();
        if(canIssueOrder == 5){
            d_currentPlayer.setTempOrder(new BombOrder(d_currentPlayer, d_countryToBomb, d_context.getEngine().getMap()));
            d_currentPlayer.issueOrder();
        } else{
            System.out.println(GameCommands.BOMB_ERROR_MESSAGES.get(canIssueOrder));
        }
    }


    @Override
    public void postExecute() {

    }

    /*
    Things to check for bomb
    1. check if player owns the card
    2. check if player itself owns the country
    3. check if the country is adjacent to one of the player's countries
    4. check if the player has negotiated with the target country's player
     */
    private int checkCommands() {
        int isCommandValid = 4;
        //checks if player has the card available
        if(!d_currentPlayer.isCardAvailable(CardType.Bomb)){
            isCommandValid = 1;
            return isCommandValid;
        }
        // checks if player owns the country
        else if(d_currentPlayer.isCountryOwned(d_context.getEngine().getMap().getCountryById(d_countryToBomb))){
            isCommandValid = 2;
            return isCommandValid;
        }
        // checks if player has the target country as one of the neighbours
        for(Country country: d_currentPlayer.getCountriesOwned()){
            if(country.isNeighbour(d_countryToBomb)){
                isCommandValid = 5;
                return isCommandValid;
            }
            isCommandValid = 3;
        }
        // checks if player has a diplomacy with the target country's owner
        for(Player player : PlayerHandler.getGamePlayers()){
            if(player.isCountryOwned(d_context.getEngine().getMap().getCountryById(d_countryToBomb))){
                if(d_currentPlayer.isPlayerNegotiated(player)){
                    isCommandValid = 4;
                    return isCommandValid;
                }
            }
        }

        return isCommandValid;
    }
}