package game;

import common.Command;
import common.IMethod;
import game.Actions.DeployOrderActions;
import game.Actions.UpdatePlayersAction;
import game.States.IGameState;
import mapShow.MapViewer;

import java.util.HashMap;

/**
 * @author Soham
 */
public class IssueOrder implements IGameState {

    private final HashMap<String, IMethod> d_cmdToActionMap;
    private Context d_context;

    public IssueOrder(){
        d_cmdToActionMap = new HashMap<>();
    }

    @Override
    public void performAction(Context p_context, Command p_command) {
        setContext(p_context);
        d_cmdToActionMap.get(p_command.getCmdName()).invoke(p_command);
    }

    public void ShowMap(Command p_command) {
        MapViewer.showMap(GameEngine.d_LoadedMap.getMap());
    }

    @Override
    public boolean canProcessCommand(String p_cmdName) {
        if(GameCommands.CHECK_VALID_COMMANDS_FOR_ISSUEORDER.contains(p_cmdName)){
            d_cmdToActionMap.put(GameCommands.CMD_SHOWMAP,this::ShowMap);
            d_cmdToActionMap.put(GameCommands.CMD_GAME_PLAYER, UpdatePlayersAction::cmdUpdatePlayers);
            d_cmdToActionMap.put(GameCommands.CMD_ASSIGN_COUNTRIES_TO_PLAYER, UpdatePlayersAction::cmdAssignCountries);
            d_cmdToActionMap.put(GameCommands.CMD_DEPLOY_COUNTRIES, DeployOrderActions::cmdDeployAction);
            return true;
        }
        return false;
    }

    @Override
    public void setContext(Context p_ctx) {
        d_context = p_ctx;
    }
}
