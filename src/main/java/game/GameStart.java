package game;

import common.Command;
import entity.MapLoader;
import game.States.GameStates;
import game.States.IGameState;

/**
 * @author Soham
 */
public class GameStart implements IGameState {

    private boolean d_hasQuit;

    GameStart(){
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
            GameEngine.d_LoadedMap = new MapLoader(GameEngine.d_CmdArguments.get(0).get(0));
            if (!GameEngine.d_HasQuit) {
                System.out.println("Loaded map: " + GameEngine.d_CmdArguments);
                GameEngine.changeState(GameStates.IssueOrder);
            } else {
                System.out.println("ERROR: Error loading map, check the Map Name again.");
            }

        } catch (Exception e) {
            System.out.println("ERROR: Error loading map, check the Map Name again.");
            GameEngine.d_HasQuit = true;
        }
    }



    @Override
    public void performAction(GameEngine gameEngine, Command p_command) {
        if (p_command.getCmdName().equals(GameCommands.CMD_LOAD_MAP)) {
            loadGameMap(p_command);
        }
    }

    @Override
    public boolean canProcessCommand(String p_cmdName) {
        return p_cmdName.equals(GameCommands.CMD_LOAD_MAP);
    }

    @Override
    public void setContext(Context p_ctx) {

    }
}
