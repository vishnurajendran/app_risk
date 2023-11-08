package game.Actions;

import common.Command;
import entity.PlayerHandler;

/**
 * This action handles assigncountries command.
 * @author vishnurajendran
 */
public class AssignCountriesAction extends GameAction{

    /**
     * This function assigns countries to a player on a random basis.
     */
    @Override
    public void execute(Command p_cmd) {
        if (PlayerHandler.getGamePlayers().size() <= 1) {
            d_execStatus = ActionExecStatus.Fail;
            System.out.println("Not enough players to start the game. the game needs at-least 2 players");
            return;
        }
        PlayerHandler.assignCountriesToPlayer(d_context.getEngine().getMap());
        PlayerHandler.countriesAssigned(true);
    }

    @Override
    public void postExecute() {

    }
}
