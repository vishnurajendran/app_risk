package game;

import common.Command;
import common.IMethod;
import common.ISubApplication;
import entity.MapLoader;
import entity.PlayerHandler;
import game.States.GameStates;
import game.States.IGameState;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * This is the game class that handles the main logic of the game
 * executes registering of commands and methods based on the commands.
 *
 * @author Soham
 */

public class GameEngine implements ISubApplication {
    public static MapLoader d_LoadedMap;
    public static boolean d_HasQuit;
    private final HashMap<String, IMethod> d_cmdtoGameAction;
    public static ArrayList<ArrayList<String>> d_CmdArguments;
    public static ArrayList<String> d_CmdOption;
    private GameStates d_gameState = GameStates.GameStart;

    private IGameState d_activeGameStateHandler;

    private static IGameState d_currentState;

    /**
     * default constructor
     */
    public GameEngine() {
        d_cmdtoGameAction = new HashMap<>();
        d_CmdArguments = new ArrayList<>();
        d_CmdOption = new ArrayList<>();
        d_HasQuit = false;
        d_currentState = new GameStart();
    }

    /**
     * @return current instance of map loader
     */
    public static MapLoader getLoadedMap() {
        return d_LoadedMap;
    }

    /**
     * quits the game.
     */
    public static void quitGame() {
        d_HasQuit = true;
    }

    public static void changeState(GameStates p_newState) {
        d_currentState = GameStateFactory.get(p_newState);
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

    /**
     * This method registers game commands and associates them with corresponding action methods.
     */
//    private void registerGameCommands() {
//        Logger.log("Registering game commands");
//    }


    @Override
    public void initialise() {

    }

    /**
     * This method checks if the application has quit due to an unexpected error
     *
     * @return a boolean which says whether the application has quit.
     */
    @Override
    public boolean hasQuit() {
        return d_HasQuit;
    }

    /**
     * Checks if the command can be processed based on the current game state.
     *
     * @param p_cmdName name of the command for validation.
     * @return returns true/false based on if the command is present in GameCommands.java
     */
    @Override
    public boolean canProcess(String p_cmdName) {

        if (p_cmdName.equals(GameCommands.CMD_SHOWMAP))
            return true;
        return d_currentState.canProcessCommand(p_cmdName);
    }

    /**
     * This method firstly checks the gamestate of game.
     * If it is deploy mode, then it runs a game loop in round-robin fashion
     * parsing through all players
     * else it checks through other commands and runs their function
     *
     * @param p_command
     */
    @Override
    public void submitCommand(Command p_command) {
        loadArgumentsAndOption(p_command);
        if (p_command.getCmdName().equals(GameCommands.CMD_LOAD_MAP) && p_command.getCmdAttributes().isEmpty()) {
            d_HasQuit = true;
        }
        if (d_currentState.canProcessCommand(p_command.getCmdName())) {
            d_currentState.performAction(new Context(d_currentState), p_command);
        }
    }

    /**
     * shuts down the game and clears all data
     * held for gameplay.
     */
    @Override
    public void shutdown() {
        PlayerHandler.cleanup();
    }
}
