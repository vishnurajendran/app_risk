package game;

import common.Command;
import common.IMethod;
import game.Actions.DeployOrderActions;
import game.Actions.UpdatePlayersAction;
import game.States.IGameState;

import java.util.HashMap;

/**
 * @author Soham
 */
public class IssueOrder implements IGameState {

    private final HashMap<String, IMethod> d_cmdToActionMap;


    public IssueOrder(){
        d_cmdToActionMap = new HashMap<>();
    }

    @Override
    public void performAction(GameEngine gameEngine, Command command) {
        d_cmdToActionMap.put(GameCommands.CMD_GAME_PLAYER, UpdatePlayersAction::cmdUpdatePlayers);
        d_cmdToActionMap.put(GameCommands.CMD_ASSIGN_COUNTRIES_TO_PLAYER, UpdatePlayersAction::cmdAssignCountries);
        d_cmdToActionMap.put(GameCommands.CMD_DEPLOY_COUNTRIES, DeployOrderActions::cmdDeployAction);
    }

    @Override
    public boolean canProcessCommand(String p_cmdName) {
        return p_cmdName.equals(GameCommands.CMD_DEPLOY_COUNTRIES);
    }

    @Override
    public void setContext(Context p_ctx) {

    }
}
