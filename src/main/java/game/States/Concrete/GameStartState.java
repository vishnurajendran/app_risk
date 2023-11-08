package game.States.Concrete;

import common.Command;
import common.IMethod;
import common.Logging.Logger;
import entity.MapLoader;
import entity.PlayerHandler;
import entity.RiskMap;
import game.Actions.ActionExecStatus;
import game.Actions.GameAction;
import game.Actions.GameActionFactory;
import game.Data.Context;
import game.GameCommands;
import game.States.GameState;
import game.States.GameStates;
import mapEditer.MapValidator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * This state handles the map load and any initialisations
 * that needs to be done on a new fresh game.
 * @author Soham
 */
public class GameStartState extends GameState {

    private final HashMap<String, IMethod> d_cmdToActionMap;

    public GameStartState(){
        d_cmdToActionMap = new HashMap<>();
        d_cmdToActionMap.put(GameCommands.CMD_LOAD_MAP, this::loadGameMap);
        d_cmdToActionMap.put(GameCommands.CMD_GAME_PLAYER, this::cmdUpdatePlayers);
        d_cmdToActionMap.put(GameCommands.CMD_ASSIGN_COUNTRIES_TO_PLAYER, this::cmdAssignCountries);
    }

    /**
     * @return true if setup commands are supported at this time
     */
    private boolean validSetupCommandAtThisTime(){
        if(d_context.getEngine().gameStarted()){
            System.out.println("You cannot use this command at this time.");
            return false;
        }

        return true;
    }

    /**
     * executes add/remove action
     * @param p_command p_command for further processing
     */
    private void cmdUpdatePlayers(Command p_command) {
        if(!validSetupCommandAtThisTime())
            return;

        GameAction l_action = GameActionFactory.getUpdatePlayerAction();
        executeAction(l_action, p_command);
    }

    /**
     * Executes actions and checks if more commands can be allowed.
     * @param p_action is the current action to be performed.
     * @param p_command command that is to be processed.
     */
    private void executeAction(GameAction p_action, Command p_command){
        // we want the updated current player to go to this state
        p_action.SetContext(new Context(PlayerHandler.getCurrentPlayer(), d_context.getEngine()));
        p_action.execute(p_command);
        p_action.postExecute();
    }

    /**
     * executes assigncountries action
     * @param p_command p_command for further processing
     */
    private void cmdAssignCountries(Command p_command) {
        if(!validSetupCommandAtThisTime())
            return;

        GameAction l_action = GameActionFactory.getAssignCountriesAction();
        executeAction(l_action, p_command);

        if(l_action.getExecutionStatus() == ActionExecStatus.Fail)
            return;

        // set game started to true.
        // and display the player turn.
        Logger.log("Countries Assigned");
        d_context.getEngine().setGameStared();
        Logger.log("Game Started");
        d_context.getEngine().changeState(GameStates.RoundInitState);
    }

    /**
     * This method loads the map into a variable
     * quits the game stage if the map cannot be loaded.
     * @param p_cmd is the command that was passed from the application phase
     */
    private void loadGameMap(Command p_cmd) {
        RiskMap l_map = null;
        String l_mapFile = "";

        if(p_cmd.getCmdAttributes().isEmpty() || p_cmd.getCmdAttributes().get(0).getArguments().isEmpty()) {
            System.out.println("Error: map file not specified");
            d_context.getEngine().changeState(GameStates.GameOver);
            return;
        }

        l_mapFile = p_cmd.getCmdAttributes().get(0).getArguments().get(0);
        MapLoader.loadMap(l_mapFile);
        l_map = MapLoader.getMap();

        // invalid map, change state to game-over.
        // since the players are not assigned, game over state just immediately quits.
        if(l_map == null){
            System.out.println("Error: map file to load is invalid!!");
            d_context.getEngine().changeState(GameStates.GameOver);
            return;
        }

        // if the map is invalid we change state to game-over
        // since the players are not assigned, game over state just immediately quits.
        if(!MapValidator.validateMap(l_map)){
            System.out.println("Error: loaded map is invalid!!");
            d_context.getEngine().changeState(GameStates.GameOver);
            return;
        }

        // set the map and change state.
        d_context.getEngine().setMap(l_map);
        System.out.println("Loaded map: " + l_mapFile);
    }


    /**
     * loads a map when loadmap is entered.
     * @param p_command command to perform
     */
    @Override
    public void performAction(Command p_command) {
        System.out.println(p_command);
        if(d_cmdToActionMap.containsKey(p_command.getCmdName())){
            d_cmdToActionMap.get(p_command.getCmdName()).invoke(p_command);
        }
        else {
            System.out.println(p_command.getCmdName() + " is an invalid command!");
        }
    }

    /**
     * returns true if loadmap is the entered command.
     * @param p_cmdName command to check
     * @return true is p_cmdName is loadmap
     */
    @Override
    public boolean canProcessCommand(String p_cmdName) {
        return d_cmdToActionMap.containsKey(p_cmdName);
    }

}
