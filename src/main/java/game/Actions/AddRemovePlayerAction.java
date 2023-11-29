package game.Actions;

import application.ApplicationConstants;
import common.Command;
import entity.Player;
import entity.PlayerHandler;
import game.GameCommands;
import game.GameEngine;

import java.text.MessageFormat;
import java.util.ArrayList;

/**
 * This action implements gameplayer command to add or remove
 * players in a game.
 * @author vishnurajendran
 */
public class AddRemovePlayerAction extends GameAction{

    private ArrayList<ArrayList<String>> d_CmdArguments;
    private ArrayList<String> d_CmdOption;

    /**
     * This method adds players to the game players list and displays them in the console
     */
    @Override
    public void execute(Command p_cmd) {
        loadArgumentsAndOption(p_cmd);
        GameEngine l_engine = d_context.getEngine();
        for (int i = 0; i < d_CmdOption.size(); i++) {
            if (d_CmdOption.get(i).equals(GameCommands.CMD_GAME_PLAYER_OPTION_ADD) && !d_CmdArguments.isEmpty()) {
                if(d_CmdOption.size() > i+1 && d_CmdArguments.size() > i+1){
                    if(d_CmdOption.get(i+1).equals(GameCommands.CMD_GAME_PLAYER_OPTION_ADD_STRATEGIES) && d_CmdArguments.get(i).size() == d_CmdArguments.get(i+1).size()){
                        PlayerHandler.addGamePlayers(d_CmdArguments.get(i), d_CmdArguments.get(i+1), l_engine.getMap());
                        i++;
                    } else{
                        d_execStatus = ActionExecStatus.Fail;
                        System.out.println("ERROR: Invalid Strategy command");
                        return;
                    }
                } else{
                    PlayerHandler.addGamePlayers(d_CmdArguments.get(i), l_engine.getMap());
                }

            } else if (d_CmdOption.get(i).equals(GameCommands.CMD_GAME_PLAYER_OPTION_REMOVE) && !d_CmdArguments.isEmpty()) {
                PlayerHandler.removeGamePlayers(d_CmdArguments.get(i));
            } else {
                d_execStatus = ActionExecStatus.Fail;
                System.out.println(MessageFormat.format(ApplicationConstants.ERR_MSG_INVALID_CMD, p_cmd.getCmdName()));
            }
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

    /**
     * method override for postExecute, to print
     * player add result.
     */
    @Override
    public void postExecute() {
        System.out.println("\n[ Total Players In Game ]");
        for (Player l_player: PlayerHandler.getGamePlayers()) {
            System.out.println("\t- " + l_player.getPlayerName() + " | Strategy: " + l_player.getPlayerStrategy());
        }
    }

}
