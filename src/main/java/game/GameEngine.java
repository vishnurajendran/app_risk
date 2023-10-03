package game;
import application.ApplicationConstants;
import common.*;
import mapEditer.MapLoader;

import java.text.MessageFormat;
import java.util.*;


/**
 * This is the game class
 * Handles registering of commands and executing methods based on the commands.
 * @author Soham
 */

public class GameEngine implements ISubApplication {
    private final HashMap<String, IMethod> d_cmdtoGameAction;
    private ArrayList<ArrayList<String>> d_cmdArguments;
    private ArrayList<String> d_cmdOption;
    private MapLoader d_loadedMap;
    private GameState d_gameState = GameState.Initial;



    public GameEngine() {
        d_cmdtoGameAction = new HashMap<>();
        d_cmdArguments = new ArrayList<>();
        d_cmdOption = new ArrayList<>();
        //d_gamePlayers = new HashSet<>();
    }

    /**
     * This method stores the action and arguments
     * to d_cmdOption and d_cmdArguments, respectively.
     */
    private void loadArgumentsAndOption(Command p_cmd){
        d_cmdArguments = new ArrayList<>();
        d_cmdOption = new ArrayList<>();
        for(int i = 0; i<p_cmd.getCmdAttributes().size();i++){
            if(!p_cmd.getCmdAttributes().isEmpty()){
                d_cmdArguments.add(p_cmd.getCmdAttributes().get(i).getArguments());
            }
            if(!p_cmd.getCmdAttributes().isEmpty()){
                d_cmdOption.add(p_cmd.getCmdAttributes().get(i).getOption());
            }
        }

    }

    /**
     * This method registers game commands and associates them with corresponding action methods.
     */
    private void registerGameCommands(){
        Logger.log("Registering game commands");
        d_cmdtoGameAction.put(GameCommands.CMD_LOAD_MAP, this::loadGameMap);
        d_cmdtoGameAction.put(GameCommands.CMD_ASSIGN_COUNTRIES_TO_PLAYER, this::assignCountries);
        d_cmdtoGameAction.put(GameCommands.CMD_GAME_PLAYER, this::updatePlayers);
    }

    private void loadGameMap(Command p_cmd){
        d_loadedMap = new MapLoader(d_cmdArguments.get(0).get(0));
        System.out.println(d_loadedMap.getMap().getCountryIds());
        System.out.println("Loading map " + d_cmdArguments);
    }

    private void updatePlayers(Command p_cmd){
        //Logger.log(d_cmdOption + ":" + GameCommands.CMD_GAME_PLAYER_OPTION_ADD);
        for(int i = 0; i<d_cmdOption.size(); i++){
            if(d_cmdOption.get(i).equals(GameCommands.CMD_GAME_PLAYER_OPTION_ADD ) && !d_cmdArguments.isEmpty()){
                PlayerHandler.addGamePlayers(d_cmdArguments.get(i));
                PlayerHandler.displayGamePlayers();
            } else if (d_cmdOption.get(i).equals(GameCommands.CMD_GAME_PLAYER_OPTION_REMOVE) && !d_cmdArguments.isEmpty()){
                PlayerHandler.removeGamePlayers(d_cmdArguments.get(i));
                PlayerHandler.displayGamePlayers();
            } else {
                System.out.println(MessageFormat.format(ApplicationConstants.MSG_INVALID_CMD, p_cmd.getCmdName()));
            }
        }


    }

    private void assignCountries(Command p_cmd){
        PlayerHandler.assignCountriesToPlayer(d_loadedMap);
        d_gameState = GameState.DeployMode;
    }

    @Override
    public void initialise() {
        registerGameCommands();
    }

    // returns true/false based on if the command is present in GameCommands.java
    @Override
    public boolean canProcess(String p_cmdName) {
        if(d_gameState.equals(GameState.Initial)){
            return GameCommands.CHECKVALIDCOMMANDSFORINITIAL.contains(p_cmdName);
        } else if(d_gameState.equals(GameState.DeployMode)){
            return p_cmdName.equals(GameCommands.CMD_DEPLOY_COUNTRIES);
        }
        return false;
    }

    @Override
    public void submitCommand(Command p_command) {
       //d_gamePlayers = p_gamePlayers;
        if(d_gameState.equals(GameState.DeployMode)){
            int canIssueOrder = PlayerHandler.issueOrder(p_command);

            Logger.log(String.valueOf(canIssueOrder));
            if(canIssueOrder == 4){
                int l_availableReinforcements = 3;
                for(int i = 0; i<PlayerHandler.getGamePlayers().size(); i++){
                    l_availableReinforcements = PlayerHandler.getGamePlayers().get(PlayerHandler.getPlayerTurn() % PlayerHandler.getGamePlayers().size()).getAvailableReinforcements();
                    if(l_availableReinforcements != 0){
                        System.out.println(PlayerHandler.getGamePlayers().get(PlayerHandler.getPlayerTurn()%PlayerHandler.getGamePlayers().size()).getPlayerName()
                                + "'s turn, Reinforcements left: " + l_availableReinforcements);
                        return;
                    } else{
                        PlayerHandler.increasePlayerTurn(1);
                    }
                }
                System.out.println("Everyone deployed their reinforcements");

            }
        } else {
            loadArgumentsAndOption(p_command);
            if (d_cmdtoGameAction.containsKey(p_command.getCmdName())) {
                d_cmdtoGameAction.get(p_command.getCmdName()).invoke(p_command);
            }
        }
    }

    @Override
    public void shutdown() {

    }
}
