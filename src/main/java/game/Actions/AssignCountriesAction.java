package game.Actions;

import common.Command;
import entity.PlayerHandler;

/**
 * @author vishnurajendran
 */
public class AssignCountriesAction extends GameAction{
    @Override
    public void execute(Command cmd) {
        if (PlayerHandler.getGamePlayers().size() <= 1) {
            System.out.println("Not enough players to start the game. the game needs at-least 2 players");
            return;
        }
        PlayerHandler.assignCountriesToPlayer(d_context.getEngine().getMap());
    }

    @Override
    public void postExecute() {

    }
}
