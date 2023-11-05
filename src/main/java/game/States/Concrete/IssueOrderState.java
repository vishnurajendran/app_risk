package game.States.Concrete;

import common.Command;
import common.IMethod;
import entity.Player;
import entity.PlayerHandler;
import game.Actions.GameAction;
import game.Actions.GameActionFactory;
import game.GameCommands;
import game.States.GameState;
import game.States.GameStates;
import mapShow.MapViewer;

import java.util.HashMap;

/**
 * @author Soham
 */
public class IssueOrderState extends GameState {

    private final HashMap<String, IMethod> d_cmdToActionMap;

    public IssueOrderState(){
        d_cmdToActionMap = new HashMap<>();
        d_cmdToActionMap.put(GameCommands.CMD_SHOWMAP,this::cmdShowMap);
        d_cmdToActionMap.put(GameCommands.CMD_GAME_PLAYER, this::cmdUpdatePlayers);
        d_cmdToActionMap.put(GameCommands.CMD_ASSIGN_COUNTRIES_TO_PLAYER, this::cmdAssignCountries);
        d_cmdToActionMap.put(GameCommands.CMD_DEPLOY_COUNTRIES, this::cmdDeployAction);
    }

    @Override
    public void performAction(Command p_command) {
        d_cmdToActionMap.get(p_command.getCmdName()).invoke(p_command);
    }

    /**
     * this methods opens the map viewer
     *
     * @param p_command command for further processing.
     */
    public void cmdShowMap(Command p_command) {
        MapViewer.showMap(d_context.getEngine().getMap());
    }

    public void cmdUpdatePlayers(Command p_command) {
        GameAction action = GameActionFactory.getUpdatePlayerAction();
        executeAction(action, p_command);
    }

    public void cmdAssignCountries(Command p_command) {
        GameAction action = GameActionFactory.getAssignCountriesAction();
        executeAction(action, p_command);
    }

    public void cmdDeployAction(Command p_command){
        GameAction action = GameActionFactory.getDeployAction();
        executeAction(action, p_command);
    }

    /**
     * Executes actions and checks if more commands can be allowed.
     * @param action is the current action to be performed.
     * @param p_command command that is to be processed.
     */
    private void executeAction(GameAction action, Command p_command){
        action.SetContext(d_context);
        action.execute(p_command);
        action.postExecute();

        // can we allow more commands
        // approach 1. more commands possible? if yes -> switch to next player, when all player exhausted switch to execute.
        int l_availableReinforcements;
        Player l_currentPlayer = PlayerHandler.getCurrentPlayer();
        for (int i = 0; i < PlayerHandler.getGamePlayers().size() - 1;i++){
            l_availableReinforcements = l_currentPlayer.getAvailableReinforcements();
            if(l_availableReinforcements != 0){
                if(PlayerHandler.areCountriesAssigned()){
                    System.out.println(l_currentPlayer.getPlayerName()
                            + "'s turn, Reinforcements left: " + l_availableReinforcements);
                    PlayerHandler.displayGamePlayersCountries(l_currentPlayer);
                }
                return;
            }
            PlayerHandler.increasePlayerTurn(1);
            l_currentPlayer = PlayerHandler.getCurrentPlayer();
        }
        System.out.println("Issue Order stage is complete. Switching to Execute Order.");
        d_context.getEngine().changeState(GameStates.ExecuteOrder);

        // approach 2. wait for player commit. if yes -> switch to next player, when all commited switch state to execute.
    }

    @Override
    public boolean canProcessCommand(String p_cmdName) {
        return GameCommands.CHECK_VALID_COMMANDS_FOR_ISSUEORDER.contains(p_cmdName);
    }

}
