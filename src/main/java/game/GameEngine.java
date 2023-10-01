package game;
import application.ApplicationConstants;
import common.*;

import java.text.MessageFormat;
import java.util.*;


/**
 * This is the game class
 * Handles registering of commands and executing methods based on the commands.
 * @author Soham
 */
public class GameEngine implements ISubApplication {
    private final HashMap<String, IMethod> d_cmdtoGameAction;
    private ArrayList<String> d_cmdArguments;
    private String d_cmdOption;
    public Set<Player> d_gamePlayers;


    // TODO: Make d_gamePlayers global to avoid reinitialization during calls.
    public GameEngine() {
        d_cmdtoGameAction = new HashMap<>();
        d_cmdArguments = new ArrayList<>();
        d_cmdOption = "";
        d_gamePlayers = new HashSet<>();
    }

    /**
     * This method stores the action and arguments
     * to d_cmdOption and d_cmdArguments, respectively.
     */
    private void loadArgumentsAndOption(Command p_cmd){
        if(!p_cmd.getCmdAttributes().isEmpty()){
            d_cmdArguments = p_cmd.getCmdAttributes().get(0).getArguments();
        }
        if(!p_cmd.getCmdAttributes().isEmpty()){
            d_cmdOption = p_cmd.getCmdAttributes().get(0).getOption();
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

    private void addGamePlayer(ArrayList<String> p_playerNamesToAdd){
        System.out.println("Added " + p_playerNamesToAdd.size() + " players to the game:");
        for(String name: p_playerNamesToAdd){
            d_gamePlayers.add(new Player(name));
            System.out.println(name);
        }
    }

    /**
     * @param p_playerNamesToRemove contains all the player names in an array format
     * The method creates an iterator to iterate through d_gameplayers
     * It compares based on names to remove the one that matches
     */
    private void removeGamePlayer(ArrayList<String> p_playerNamesToRemove){

        Iterator<Player> l_iterator = d_gamePlayers.iterator();
        for (String name : p_playerNamesToRemove) {
            while (l_iterator.hasNext()) {
                Player player = l_iterator.next();
                if (player.getPlayerName().equals(name)) {
                    l_iterator.remove();
                }
            }
        }


        System.out.println("The new list of game Players is: ");
        for(Player name: d_gamePlayers){
            System.out.println(name.playerName);
        }
    }

    private void loadGameMap(Command p_cmd){
        System.out.println("Loading map " + d_cmdArguments);
    }

    private void updatePlayers(Command p_cmd){
        //Logger.log(d_cmdOption + ":" + GameCommands.CMD_GAME_PLAYER_OPTION_ADD);
        if(d_cmdOption.equals(GameCommands.CMD_GAME_PLAYER_OPTION_ADD ) && !d_cmdArguments.isEmpty()){
            addGamePlayer(d_cmdArguments);
        } else if (d_cmdOption.equals(GameCommands.CMD_GAME_PLAYER_OPTION_REMOVE) && !d_cmdArguments.isEmpty()){
            removeGamePlayer(d_cmdArguments);
        } else {
            System.out.println(MessageFormat.format(ApplicationConstants.MSG_INVALID_CMD, p_cmd.getCmdName()));
        }

    }

    private void assignCountries(Command p_cmd){

    }

    @Override
    public void initialise() {
        registerGameCommands();
    }

    // returns true/false based on if the command is present in GameCommands.java
    @Override
    public boolean canProcess(String p_cmdName) {
        return GameCommands.CHECKVALIDCOMMANDS.contains(p_cmdName);
    }

    @Override
    public void submitCommand(Command p_command) {
        loadArgumentsAndOption(p_command);
        if(d_cmdtoGameAction.containsKey(p_command.getCmdName())){
            d_cmdtoGameAction.get(p_command.getCmdName()).invoke(p_command);
        }
    }

    @Override
    public void shutdown() {

    }
}
