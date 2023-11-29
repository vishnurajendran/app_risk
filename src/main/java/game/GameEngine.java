package game;

import common.Command;
import common.IMethod;
import common.ISubApplication;
import common.Logging.Logger;
import entity.MapSaveData;
import entity.Player;
import entity.PlayerHandler;
import entity.RiskMap;
import game.Data.Context;
import game.Data.GameSaveData;
import game.Orders.Order;
import game.Orders.Serailisation.OrderSaveConverter;
import game.Orders.Serailisation.OrderSaveData;
import game.States.GameStateFactory;
import game.States.GameStates;
import game.States.IGameState;
import mapShow.MapViewer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * This is the game class that handles the main logic of the game
 * executes registering of commands and methods based on the commands.
 * @author Soham
 */

public class GameEngine implements ISubApplication , IEngine {

    //essential to save map data.
    private String d_mapPath;
    private RiskMap d_map;
    private boolean d_HasQuit;

    private GameStates d_gameState = GameStates.GameStart;

    private IGameState d_currentState;

    private boolean d_gameStarted = false;

    private Map<String, IMethod> d_localCommands;

    /**
     * default constructor
     */
    public GameEngine() {
        d_HasQuit = false;
    }

    /**
     * sets current instance of map
     */
    public void setMap(RiskMap p_map, String p_mapPath) {
        d_mapPath = p_mapPath;
        d_map = p_map;
        if(p_map == null)
            quitGame();
    }

    /**
     * @return current instance of map
     */
    public RiskMap getLoadedMap() {
        return d_map;
    }

    /**
     * quits the game.
     */
    public void quitGame() {
        d_HasQuit = true;
    }

    public GameStates getGameState() {
        return d_gameState;
    }

    /**
     * Change the state of the game
     * @param p_newState is the new state
     */
    public void changeState(GameStates p_newState) {
        Logger.log("Changing State From " + d_gameState + " >>> " + p_newState);
        d_gameState = p_newState;
        d_currentState = GameStateFactory.get(p_newState);
        d_currentState.setContext(new Context(PlayerHandler.getCurrentPlayer(), this));
        MapViewer.tryUpdateMapViewer();
    }


    @Override
    public void initialise() {
        d_localCommands = new HashMap<>();
        d_localCommands.put(GameCommands.CMD_SHOWMAP, this::viewMap);
        d_localCommands.put(GameCommands.CMD_SAVE_GAME, this::saveGame);
        d_localCommands.put(GameCommands.CMD_LOAD_GAME, this::loadGame);
        changeState(GameStates.GameStart);
    }

    /**
     * This method checks if the application has quit due to an unexpected error
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
        return d_localCommands.containsKey(p_cmdName) || d_currentState.canProcessCommand(p_cmdName);
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
        if(d_localCommands.containsKey(p_command.getCmdName())){
            d_localCommands.get(p_command.getCmdName()).invoke(p_command);
            return;
        }

        if (d_currentState.canProcessCommand(p_command.getCmdName())) {
            d_currentState.performAction(p_command);
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

    @Override
    public String getHelp() {
        String msg = "[ Game Commands ]\n";
        msg += "\tshowmap\n";
        msg += "\tsavegame filename\n";
        msg += "\tloadgame filename\n";
        msg += (d_currentState != null ? d_currentState.getHelp() : "");
        return msg;
    }

    /**
     * @return returns the instance of the map loaded.
     */
    public RiskMap getMap() {
        return d_map;
    }

    /**
     * @return true if game has started
     */
    public boolean gameStarted(){
        return d_gameStarted;
    }

    /**
     * sets the game engine to mark the start of the game.
     */
    public void setGameStared(){
        d_gameStarted = true;
    }

    /**
     * shows loaded map
     * @param p_command command to process further.
     */
    private void viewMap(Command p_command){
        if(d_map == null) {
            System.out.println("No map loaded!!");
            return;
        }
        MapViewer.showMap(d_map);
    }

    /**
     * saves current game state
     * @param p_command command to process further.
     */
    private void saveGame(Command p_command){

        if(!gameStarted()){
            System.out.println("A game needs to be active, to be saved");
            System.out.println("\t- A game starts after players are added and the countries are assigned.");
            return;
        }

        Logger.log("saving...");
        String l_path = "";
        if(!p_command.getCmdAttributes().isEmpty()){
            l_path = p_command.getCmdAttributes().get(0).getArguments().get(0);
        }
        try {
            GameSaveData l_data = new GameSaveData(d_gameState, PlayerHandler.getPlayerSaveData(), MapSaveData.createSaveData(d_map, d_mapPath));
            if (GameSaveManager.saveGame(l_data, l_path)) {
                System.out.println("Game Saved!!");
            } else
                System.out.println("Could not save game");
        }
        catch (Exception ex){
            System.out.println("Could not save game");
            Logger.logError(ex.getMessage());
        }
    }

    /**
     * loads game state from file.
     * @param p_command command to process further.
     */
    private void loadGame(Command p_command){
        if(p_command.getCmdAttributes().isEmpty()){
            System.out.println(GameCommands.CMD_LOAD_GAME + " commands require a file name.");
            quitGame();
            return;
        }

        // load data from file
        String l_path = p_command.getCmdAttributes().get(0).getArguments().get(0);
        GameSaveData l_data = GameSaveManager.loadSave(l_path);
        if(l_data == null) {
            System.out.println("Cannot load from file, Data is corrupted or invalid.");
            quitGame();
            return;
        }

        // setup players
        setMap(MapSaveData.parseSaveData(l_data.getMapData()), l_data.getMapData().getMapFilepath());
        PlayerHandler.loadFromPlayerSaveData(l_data.getPlayerSaveData());
        for(Player player : PlayerHandler.getGamePlayers()){
            player.setMapReference(d_map);
        }

        // load all the orders to the players
        List<Player> l_playerList = PlayerHandler.getGamePlayers();
        for(Player l_player : l_playerList){
            int l_id = l_player.getPlayerId();
            if(!l_data.getPlayerSaveData().getPlayerOrderMap().containsKey(l_id))
            {
                System.out.println("Cannot load from file, Data is corrupted or invalid.");
                quitGame();
                return;
            }

            List<OrderSaveData> l_orderSaves = l_data.getPlayerSaveData().
                                                getPlayerOrderMap().get(l_id);
            //add all the orders back
            for(OrderSaveData l_orderSave : l_orderSaves){
                Order l_order = OrderSaveConverter.parseOrderSaveData(l_orderSave,l_playerList,d_map);
                l_player.issueOrder(l_order);
            }
        }

        setGameStared();
        // change state of the game.
        changeState(l_data.getGameState());
        System.out.println("Loaded game from "+l_path+"!!");
    }
}
