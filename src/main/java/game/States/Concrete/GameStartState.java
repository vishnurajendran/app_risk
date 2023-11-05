package game.States.Concrete;

import common.Command;
import entity.MapLoader;
import game.GameCommands;
import game.States.GameState;
import game.States.GameStates;
import java.util.ArrayList;

/**
 * @author Soham
 */
public class GameStartState extends GameState {

    ArrayList<ArrayList<String>> d_CmdArguments;
    ArrayList<String> d_CmdOption;
    private boolean d_hasQuit;

    public GameStartState(){
        d_hasQuit = false;
    }

    /**
     * This method loads the map into a variable
     * quits the game stage if the map cannot be loaded.
     *
     * @param p_cmd is the command that was passed from the application phase
     */
    private void loadGameMap(Command p_cmd) {
        try {
            loadArgumentsAndOption(p_cmd);
            d_context.getEngine().setMap(new MapLoader( d_CmdArguments.get(0).get(0)).getMap());
            if (! d_context.getEngine().hasQuit()) {
                System.out.println("Loaded map: " +  d_CmdArguments);
                this.d_context.getEngine().changeState(GameStates.IssueOrder);
            } else {
                System.out.println("ERROR: Error loading map, check the Map Name again.");
            }

        } catch (Exception e) {
            System.out.println("ERROR: Error loading map, check the Map Name again.");
            d_context.getEngine().quitGame();
        }
    }


    /**
     * This method stores the action and arguments
     * to d_cmdOption and d_cmdArguments, respectively for better readability of the code
     */
    private void loadArgumentsAndOption(Command p_cmd) {
        d_CmdArguments = new ArrayList<>();
        d_CmdOption = new ArrayList<>();
        for (int i = 0; i < p_cmd.getCmdAttributes().size(); i++) {
            if (!p_cmd.getCmdAttributes().isEmpty()) {
                d_CmdArguments.add(p_cmd.getCmdAttributes().get(i).getArguments());
            }
            if (!p_cmd.getCmdAttributes().isEmpty()) {
                d_CmdOption.add(p_cmd.getCmdAttributes().get(i).getOption());
            }
        }
    }


    @Override
    public void performAction(Command p_command) {
        if (p_command.getCmdName().equals(GameCommands.CMD_LOAD_MAP)) {
            loadGameMap(p_command);
        }
    }

    @Override
    public boolean canProcessCommand(String p_cmdName) {
        return p_cmdName.equals(GameCommands.CMD_LOAD_MAP);
    }

}
