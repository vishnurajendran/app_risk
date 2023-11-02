package game;

import application.ApplicationConstants;
import common.Command;
import entity.MapLoader;
import entity.PlayerHandler;

import java.text.MessageFormat;

/**
 * @author Soham
 */
public class InitialGame implements IGameStateHandler{

    private boolean d_hasQuit;

    InitialGame(){
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
            } else {
                System.out.println("ERROR: Error loading map, check the Map Name again.");
            }

        } catch (Exception e) {
            System.out.println("ERROR: Error loading map, check the Map Name again.");
            GameEngine.d_HasQuit = true;
        }
    }

    /**
     * This method adds players to the game players list and displays them in the console
     */
    private void updatePlayers(Command p_cmd) {
        for (int i = 0; i < GameEngine.d_CmdOption.size(); i++) {
            if (GameEngine.d_CmdOption.get(i).equals(GameCommands.CMD_GAME_PLAYER_OPTION_ADD) && !GameEngine.d_CmdArguments.isEmpty()) {
                PlayerHandler.addGamePlayers(GameEngine.d_CmdArguments.get(i));
            } else if (GameEngine.d_CmdOption.get(i).equals(GameCommands.CMD_GAME_PLAYER_OPTION_REMOVE) && !GameEngine.d_CmdArguments.isEmpty()) {
                PlayerHandler.removeGamePlayers(GameEngine.d_CmdArguments.get(i));
            } else {
                System.out.println(MessageFormat.format(ApplicationConstants.MSG_INVALID_CMD, p_cmd.getCmdName()));
            }
        }
        PlayerHandler.displayGamePlayers();
    }

    /**
     * Used to assign countries to a player
     *
     * @param p_cmd
     */
    private void assignCountries(Command p_cmd) {

        if (PlayerHandler.getGamePlayers().size() <= 1) {
            System.out.println("Not enough players to start the game. the game needs at-least 2 players");
            return;
        }

        PlayerHandler.assignCountriesToPlayer(GameEngine.d_LoadedMap);
        GameEngine.changeState(new DeployHandler());
    }

    @Override
    public void performAction(GameEngine gameEngine, Command p_command) {
        switch (p_command.getCmdName()) {
            case GameCommands.CMD_LOAD_MAP -> loadGameMap(p_command);
            case GameCommands.CMD_GAME_PLAYER -> updatePlayers(p_command);
            case GameCommands.CMD_ASSIGN_COUNTRIES_TO_PLAYER -> assignCountries(p_command);
        }
    }

    @Override
    public boolean canProcessCommand(String p_cmdName) {
        return GameCommands.CHECKVALIDCOMMANDSFORINITIAL.contains(p_cmdName);
    }
}
