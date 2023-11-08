package game.States.Concrete;

import common.Command;
import entity.MapLoader;
import entity.RiskMap;
import game.GameCommands;
import game.States.GameState;
import game.States.GameStates;
import mapEditer.MapValidator;

import java.util.ArrayList;

/**
 * This state handles the map load and any initialisations
 * that needs to be done on a new fresh game.
 * @author Soham
 */
public class GameStartState extends GameState {

    /**
     * This method loads the map into a variable
     * quits the game stage if the map cannot be loaded.
     * @param p_cmd is the command that was passed from the application phase
     */
    private void loadGameMap(Command p_cmd) {
        RiskMap l_map = null;

        // only load map if command has valid arguments.
        if(!p_cmd.getCmdAttributes().isEmpty() && !p_cmd.getCmdAttributes().get(0).getArguments().isEmpty()) {
            String l_mapFile = p_cmd.getCmdAttributes().get(0).getArguments().get(0);
            l_map = new MapLoader(l_mapFile).getMap();
        }

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
        d_context.getEngine().changeState(GameStates.IssueOrder);
    }


    /**
     * loads a map when loadmap is entered.
     * @param p_command command to perform
     */
    @Override
    public void performAction(Command p_command) {
        if (p_command.getCmdName().equals(GameCommands.CMD_LOAD_MAP)) {
            loadGameMap(p_command);
        }
    }

    /**
     * returns true if loadmap is the entered command.
     * @param p_cmdName command to check
     * @return true is p_cmdName is loadmap
     */
    @Override
    public boolean canProcessCommand(String p_cmdName) {
        return p_cmdName.equals(GameCommands.CMD_LOAD_MAP);
    }

}
