package game.Actions;

import application.ApplicationConstants;
import common.Command;
import entity.PlayerHandler;
import game.Context;
import game.GameCommands;
import game.GameEngine;

import java.text.MessageFormat;

/**
 * @author Soham
 */
public class UpdatePlayersAction extends IssueOrderAction {

    /**
     * This method adds players to the game players list and displays them in the console
     */
    public static void cmdUpdatePlayers(Command p_cmd) {
        for (int i = 0; i < GameEngine.d_CmdOption.size(); i++) {
            if (GameEngine.d_CmdOption.get(i).equals(GameCommands.CMD_GAME_PLAYER_OPTION_ADD) && !GameEngine.d_CmdArguments.isEmpty()) {
                PlayerHandler.addGamePlayers(GameEngine.d_CmdArguments.get(i));
            } else if (GameEngine.d_CmdOption.get(i).equals(GameCommands.CMD_GAME_PLAYER_OPTION_REMOVE) && !GameEngine.d_CmdArguments.isEmpty()) {
                PlayerHandler.removeGamePlayers(GameEngine.d_CmdArguments.get(i));
            } else {
                System.out.println(MessageFormat.format(ApplicationConstants.ERR_MSG_INVALID_CMD, p_cmd.getCmdName()));
            }
        }
        PlayerHandler.displayGamePlayers();
    }

    /**
     * Used to assign countries to a player
     *
     * @param p_cmd
     */
    public static void cmdAssignCountries(Command p_cmd) {

        if (PlayerHandler.getGamePlayers().size() <= 1) {
            System.out.println("Not enough players to start the game. the game needs at-least 2 players");
            return;
        }

        PlayerHandler.assignCountriesToPlayer(GameEngine.d_LoadedMap);
    }

    @Override
    public void SetContext(Context ctx) {

    }

    @Override
    public void execute(Command cmd) {

    }

    @Override
    public void cleanup() {

    }
}
