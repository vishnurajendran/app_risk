package Tournament;

import common.Command;
import common.CommandAttribute;
import common.IMethod;
import common.ISubApplication;
import common.Logging.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static Tournament.TournamentConstants.TOURNAMENT_VALIDOPTIONS;

/**
 * This class handles all the tournament commands
 * - registration and execution./
 *
 * @author TaranjeetKaur
 */
public class Tournament implements ISubApplication {


    private boolean d_HasQuit;

    private Map<String, IMethod> d_localCommands;

    private ArrayList<String> maps;

    /**
     * default constructor
     */
    public Tournament(){
        d_HasQuit = false;
    }

    /**
     * This method will be called by the application class
     * when sub application instance is created, its intended
     * to be an initialisation call.
     */
    @Override
    public void initialise() {
        d_localCommands = new HashMap<>();
        d_localCommands.put(TournamentConstants.CMD_START_TOURNAMENT, this::startTournament);
    }

    /**
     * This function is used to check if a sub-application has quit
     * on its own without a user input, in cases where the sub-application
     * is not able to proceed safely, it can set this function to return true
     * to make the Application layer perform an auto-quit, post a command query.
     *
     * @return true if sub-application has quit, else false
     */
    @Override
    public boolean hasQuit() {
        return d_HasQuit;
    }

    /**
     * This method will be called by the application class
     * when it is processing a command from the user. This
     * method will validate if a certain command can be processed
     * by this instance.
     *
     * @param p_cmdName name of the command for validation.
     * @return true if cmdName can be processed, else false
     */
    @Override
    public boolean canProcess(String p_cmdName) {
        return d_localCommands.containsKey(p_cmdName);
    }

    /**
     * This method will be called by the application class to
     * submit a command for processing to the sub application.
     *
     * @param p_command
     */
    @Override
    public void submitCommand(Command p_command) {
        //validity of command entered.
        //tournament -M listofmapfiles -P listofplayerstrategies -G numberofgames -D maxnumberofturns
        //A tournament starts with the user choosing M = 1 to 5 different maps, P = 2 to 4 different computer players strategies, G = 1 to 5
        //games to be played on each map, D = 10 to 50 maximum number of turns for each game. A

        boolean isValid = IsCommandValid(p_command);
    }

    /**
     *  This method checks the validity of tournament command parameters.
     * @param p_command   Command object
     */
    private boolean IsCommandValid(Command p_command) {
        ArrayList<CommandAttribute> l_commandAttributes = p_command.getCmdAttributes();
        if(l_commandAttributes.size() != 4){
            System.out.println(TournamentConstants.CMD_INVALID_ARGUMENTS);
            return false;
        }
        for (CommandAttribute l_cAttribute : l_commandAttributes) {
            if (l_cAttribute.getOption().isEmpty() || !TOURNAMENT_VALIDOPTIONS.contains(l_cAttribute.getOption())) {
                Logger.log("Invalid option for command:" + p_command.getCmdName());
                return false;
            }
            if (l_cAttribute.getArguments().isEmpty()) {
                Logger.log("No arguments given for command:" + p_command.getCmdName());
                return false;
            }

                //A tournament starts with the user choosing M = 1 to 5 different maps, P = 2 to 4 different computer players strategies, G = 1 to 5
                //games to be played on each map, D = 10 to 50 maximum number of turns for each game. A

        }
        return true;
    }

    /**
     *
     * @param command
     */
    private void startTournament(Command command) {
    }

    /**
     * This method will be called by the application class
     * when its about to discard the sub application, its intended
     * to be a final chance to clean up any resources used.
     */
    @Override
    public void shutdown() {
        Logger.log("Tournament has shutdown.");
    }

    /**
     * @return a help string for this application.
     */
    @Override
    public String getHelp() {
        String msg = "[ Tournament Commands ]";
        msg += "tournament -M listofmapfiles -P listofplayerstrategies -G numberofgames -D maxnumberofturns";
        return msg;
    }
}
