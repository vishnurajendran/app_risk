package Tournament;

import common.Command;
import common.CommandAttribute;
import common.IMethod;
import common.ISubApplication;
import common.Logging.Logger;
import entity.MapLoader;
import entity.Player;
import entity.PlayerHandler;
import entity.RiskMap;
import game.IEngine;
import mapEditer.MapValidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static Tournament.TournamentConstants.*;

/**
 * This class handles all the tournament commands
 * - registration and execution./
 *
 * @author TaranjeetKaur
 */
public class Tournament implements ISubApplication, IEngine {


    private boolean d_HasQuit;

    private Map<String, IMethod> d_localCommands;

    private int d_numberOfGames;

    private int d_numberOfTurns;

    private ArrayList<RiskMap> d_maps;

    private ArrayList<ArrayList<String>> d_tournamentResult;

    private ArrayList<String> d_playerStrategies;


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
        boolean l_isValid = IsCommandValid(p_command);
        if(l_isValid){
            startTournament();
        }
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
                System.out.println(TournamentConstants.CMD_INVALID_ARGUMENTS);
                Logger.log("Invalid option for command:" + p_command.getCmdName());
                return false;
            }
            if (l_cAttribute.getArguments().isEmpty()) {
                System.out.println(TournamentConstants.CMD_INVALID_ARGUMENTS);
                Logger.log("No arguments given for command:" + p_command.getCmdName());
                return false;
            }

            //validate map option::
            if(l_cAttribute.getOption().equals(TournamentConstants.CMD_OPTION_MAP)){
                if(l_cAttribute.getArguments().size() > 5){
                    System.out.println(TournamentConstants.CMD_TOO_MANY_ARGUMENTS);
                    return false;
                }
                if(!intialiseMapsForTournament(l_cAttribute.getArguments())){
                    System.out.println(CMD_INVALID_MAP);
                    return false;
                }
            }


            //validate player strategy option::
            if(l_cAttribute.getOption().equals(TournamentConstants.CMD_OPTION_PLAYERSTRATEGY)){
                if(l_cAttribute.getArguments().size() < 2 ){
                    Logger.log("Number of allowed players is greater than 2" + p_command.getCmdName());
                    System.out.println(TournamentConstants.CMD_TOO_FEW_ARGUMENTS);
                    return false;
                }
                if(l_cAttribute.getArguments().size() > 4 ){
                    Logger.log("Number of allowed players is 4" + p_command.getCmdName());
                    System.out.println(TournamentConstants.CMD_TOO_MANY_ARGUMENTS);
                    return false;
                }
                if(!intialisePlayerStrategyForTournament(l_cAttribute.getArguments())){
                    System.out.println(TournamentConstants.CMD_INVALID_STRATEGY);
                    return false;
                }
            }


            //validate number of games option::
            if(l_cAttribute.getOption().equals(TournamentConstants.CMD_OPTION_GAMES)){
                if(l_cAttribute.getArguments().size() != 1 ){
                    System.out.println(TournamentConstants.CMD_TOO_MANY_ARGUMENTS);
                    return false;
                }
                int l_games = Integer.parseInt(l_cAttribute.getArguments().get(0));
                if(l_games > 5 || l_games < 0){
                    Logger.log("Number of allowed games is 1-5" + p_command.getCmdName());
                    System.out.println(TournamentConstants.CMD_INVALID_ARGUMENTS);
                    return false;
                }
                d_numberOfGames = l_games;
                Logger.log("The number of games in the tournament set to" + d_numberOfGames);
            }


            //validate number of turns::
            if(l_cAttribute.getOption().equals(TournamentConstants.CMD_OPTION_MAXIMUM_TURNS)){
                if(l_cAttribute.getArguments().size() != 1 ){
                    System.out.println(TournamentConstants.CMD_TOO_MANY_ARGUMENTS);
                    return false;
                }
                int l_turns = Integer.parseInt(l_cAttribute.getArguments().get(0));
                if(l_turns > 50 || l_turns < 10){
                    Logger.log("Number of allowed turns is 10-50" + p_command.getCmdName());
                    System.out.println(TournamentConstants.CMD_INVALID_ARGUMENTS);
                    return false;
                }
                d_numberOfTurns = l_turns;
                Logger.log("The number of maximum turns in the tournament set to" + d_numberOfTurns);
            }

        }
        return true;
    }

    /**
     *  This method validates the Strategies input for player and initialise player list
      * @param p_playerStrategyList List of Strategies
     * @return  true if intialisation successful, false otherwise.
     */
    private boolean intialisePlayerStrategyForTournament(ArrayList<String> p_playerStrategyList){
        for (String l_playerStrategy : p_playerStrategyList){
            if(!TOURNAMENT_VALIDSTRATEGIES.contains(l_playerStrategy)){
                System.out.println(CMD_INVALID_STRATEGY);
                return false;
            }
            d_playerStrategies.add(l_playerStrategy);
            Logger.log("Tournament :: Player strategy added " + l_playerStrategy);
        }
        return true;
    }

    /**
     *  This method validates the list of map files
     * @param p_mapList List of map files.
     * @return  true if intialisation successful, false otherwise.
     */
    private boolean intialiseMapsForTournament(ArrayList<String> p_mapList){
        for(String l_map : p_mapList){
            boolean l_isMapLoaded  = MapLoader.loadMap(l_map);
            if(!l_isMapLoaded){
                Logger.log("Invalid map:" + l_map);
                System.out.println(CMD_INVALID_MAP);
                return false;
            }
            boolean l_isMapValid = MapValidator.validateMap(MapLoader.getMap());
            if(!l_isMapValid){
                Logger.log("Invalid map:" + l_map);
                System.out.println(CMD_INVALID_MAP);
                return false;
            }
            d_maps.add(MapLoader.getMap());
        }
        return true;
    }


    /**
     *  This method starts the Tournament and sets the results.
     */
    private void startTournament() {
        d_tournamentResult = new ArrayList<>(d_maps.size());
        for(int i=0;i<d_maps.size();i++){
            d_tournamentResult.set(i, new ArrayList<String>(d_numberOfGames));
            for (int j=0;j<d_numberOfGames;j++){
                // game j on map i;
                PlayerHandler.addGamePlayers(d_playerStrategies, d_playerStrategies, d_maps.get(i));
                PlayerHandler.assignCountriesToPlayer(d_maps.get(i));
                PlayerHandler.countriesAssigned(true);

                PlayerHandler.getCurrentPlayer().setStrategyContext(this);
                PlayerHandler.getCurrentPlayer().issueOrder();
                PlayerHandler.increasePlayerTurn(1);

            }
        }
    }

    /**
     *  This method displays the Tournament results
     */
    private void displayTournamentResult() {
        System.out.println("Tournament ended with the following results::");
        for(int i=1; i<=d_numberOfGames; i++){
            System.out.print("\t\t Game " + i);
        }
        System.out.println();
        for(int i=0;i<d_tournamentResult.size();i++){
            System.out.print("Map " + Integer.toString(i+1) + "\t");
            for (int j=0;j<d_tournamentResult.get(i).size();j++){
                System.out.print(d_tournamentResult.get(i).get(j) + "\t");
            }
        }
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
