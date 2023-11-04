package game.Actions;

import application.ApplicationConstants;
import common.Command;
import entity.PlayerHandler;
import game.GameCommands;
import game.GameEngine;

import java.text.MessageFormat;
import java.util.ArrayList;

/**
 * @author vishnurajendran
 */
public class AddRemovePlayerAction extends GameAction{

    ArrayList<ArrayList<String>> d_CmdArguments;
    ArrayList<String> d_CmdOption;

    /**
     * This method adds players to the game players list and displays them in the console
     */
    @Override
    public void execute(Command p_cmd) {
        loadArgumentsAndOption(p_cmd);
        GameEngine l_engine = d_context.getEngine();
        for (int i = 0; i < d_CmdOption.size(); i++) {
            if (d_CmdOption.get(i).equals(GameCommands.CMD_GAME_PLAYER_OPTION_ADD) && !d_CmdArguments.isEmpty()) {
                PlayerHandler.addGamePlayers(d_CmdArguments.get(i), d_context.getEngine().getMap());
            } else if (d_CmdOption.get(i).equals(GameCommands.CMD_GAME_PLAYER_OPTION_REMOVE) && !d_CmdArguments.isEmpty()) {
                PlayerHandler.removeGamePlayers(d_CmdArguments.get(i));
            } else {
                System.out.println(MessageFormat.format(ApplicationConstants.ERR_MSG_INVALID_CMD, p_cmd.getCmdName()));
            }
        }
        PlayerHandler.displayGamePlayers();
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
    public void postExecute() {
        //nothing here.
    }

}
