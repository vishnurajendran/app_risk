package game;

import application.ApplicationConstants;
import common.*;
import common.Logging.Logger;
import entity.MapLoader;
import entity.Player;
import entity.PlayerHandler;
import mapShow.MapViewer;

import java.text.MessageFormat;
import java.util.*;


/**
 * This is the game class that handles the main logic of the game
 * executes registering of commands and methods based on the commands.
 *
 * @author Soham
 */

public class GameEngine implements ISubApplication {
    private static MapLoader d_loadedMap;
    private static boolean d_hasQuit;
    private final HashMap<String, IMethod> d_cmdtoGameAction;
    private ArrayList<ArrayList<String>> d_cmdArguments;
    private ArrayList<String> d_cmdOption;
    private GameState d_gameState = GameState.Initial;

    /**
     * default constructor
     */
    public GameEngine() {
        d_cmdtoGameAction = new HashMap<>();
        d_cmdArguments = new ArrayList<>();
        d_cmdOption = new ArrayList<>();
        d_hasQuit = false;
    }

    /**
     * @return current instance of map loader
     */
    public static MapLoader getLoadedMap() {
        return d_loadedMap;
    }

    /**
     * quits the game.
     */
    public static void quitGame() {
        d_hasQuit = true;
    }

    /**
     * This method stores the action and arguments
     * to d_cmdOption and d_cmdArguments, respectively for better readability of the code
     */
    private void loadArgumentsAndOption(Command p_cmd) {
        d_cmdArguments = new ArrayList<>();
        d_cmdOption = new ArrayList<>();
        for (int i = 0; i < p_cmd.getCmdAttributes().size(); i++) {
            if (!p_cmd.getCmdAttributes().isEmpty()) {
                d_cmdArguments.add(p_cmd.getCmdAttributes().get(i).getArguments());
            }
            if (!p_cmd.getCmdAttributes().isEmpty()) {
                d_cmdOption.add(p_cmd.getCmdAttributes().get(i).getOption());
            }
        }

    }

    /**
     * This method registers game commands and associates them with corresponding action methods.
     */
    private void registerGameCommands() {
        Logger.log("Registering game commands");
        d_cmdtoGameAction.put(GameCommands.CMD_LOAD_MAP, this::loadGameMap);
        d_cmdtoGameAction.put(GameCommands.CMD_ASSIGN_COUNTRIES_TO_PLAYER, this::assignCountries);
        d_cmdtoGameAction.put(GameCommands.CMD_GAME_PLAYER, this::updatePlayers);
        d_cmdtoGameAction.put(GameCommands.CMD_DEPLOY_COUNTRIES, this::cmdDeploy);
        d_cmdtoGameAction.put(GameCommands.CMD_SHOWMAP, this::showMap);
    }

    /**
     * This method loads the map into a variable
     * quits the game stage if the map cannot be loaded.
     *
     * @param p_cmd is the command that was passed from the application phase
     */
    private void loadGameMap(Command p_cmd) {
        try {
            d_loadedMap = new MapLoader(d_cmdArguments.get(0).get(0));
            if (!d_hasQuit) {
                System.out.println("Loaded map: " + d_cmdArguments);
            } else {
                System.out.println("ERROR: Error loading map, check the Map Name again.");
            }

        } catch (Exception e) {
            System.out.println("ERROR: Error loading map, check the Map Name again.");
            d_hasQuit = true;
        }
    }

    /**
     * This method adds players to the game players list and displays them in the console
     */
    private void updatePlayers(Command p_cmd) {
        for (int i = 0; i < d_cmdOption.size(); i++) {
            if (d_cmdOption.get(i).equals(GameCommands.CMD_GAME_PLAYER_OPTION_ADD) && !d_cmdArguments.isEmpty()) {
                PlayerHandler.addGamePlayers(d_cmdArguments.get(i));
            } else if (d_cmdOption.get(i).equals(GameCommands.CMD_GAME_PLAYER_OPTION_REMOVE) && !d_cmdArguments.isEmpty()) {
                PlayerHandler.removeGamePlayers(d_cmdArguments.get(i));
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
    private void assignCountries(Command p_cmd) {

        if (PlayerHandler.getGamePlayers().size() <= 1) {
            System.out.println("Not enough players to start the game. the game needs at-least 2 players");
            return;
        }

        PlayerHandler.assignCountriesToPlayer(d_loadedMap);
        d_gameState = GameState.DeployMode;
    }

    @Override
    public void initialise() {
        registerGameCommands();
    }

    /**
     * This method checks if the application has quit due to an unexpected error
     *
     * @return a boolean which says whether the application has quit.
     */
    @Override
    public boolean hasQuit() {
        return d_hasQuit;
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

        if (d_gameState.equals(GameState.Initial)) {
            return GameCommands.CHECKVALIDCOMMANDSFORINITIAL.contains(p_cmdName);
        } else if (d_gameState.equals(GameState.DeployMode)) {
            return p_cmdName.equals(GameCommands.CMD_DEPLOY_COUNTRIES);
        }

        return false;
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
            d_hasQuit = true;
        }
        if (d_cmdtoGameAction.containsKey(p_command.getCmdName())) {
            d_cmdtoGameAction.get(p_command.getCmdName()).invoke(p_command);
        }
    }

    /**
     * this method handles how deploy cmd is executed.
     *
     * @param p_command command for further processing.
     */
    private void cmdDeploy(Command p_command) {
        if (d_gameState.equals(GameState.DeployMode)) {
            int canIssueOrder = PlayerHandler.issueOrder(p_command);

            Logger.log(String.valueOf(canIssueOrder));
            if (canIssueOrder == PlayerHandler.ISSUEORDER_SUCCESS) {
                int l_availableReinforcements;
                // runs a loop through all the players to check if they have armies left
                for (int i = 0; i < PlayerHandler.getGamePlayers().size(); i++) {
                    Player l_currentPlayer = PlayerHandler.getGamePlayers().get(PlayerHandler.getPlayerTurn() % PlayerHandler.getGamePlayers().size());
                    l_availableReinforcements = l_currentPlayer.getAvailableReinforcements();
                    // once it finds a player with armies>0, it stops and lets the player deploy
                    if (l_availableReinforcements != 0) {
                        System.out.println(l_currentPlayer.getPlayerName()
                                + "'s turn, Reinforcements left: " + l_availableReinforcements);
                        PlayerHandler.displayGamePlayersCountries(l_currentPlayer);
                        return;
                    } else {
                        PlayerHandler.increasePlayerTurn(1);
                    }
                }
                // When everyone has depleted their armies, it executes all the orders
                System.out.println("Everyone deployed their reinforcements");
                executeOrders();
                PlayerHandler.reassignValuesForNextTurn();
            } else {
                System.out.println(GameCommands.DEPLOYERRORMESSAGE.get(canIssueOrder - 1));
            }
        }
    }

    /**
     * this methods opens the map viewer
     *
     * @param p_command command for further processing.
     */
    private void showMap(Command p_command) {
        MapViewer.showMap(d_loadedMap.getMap());
    }

    /**
     * This method executes all the orders
     * in a round-robin fashion as they were generated by the user
     */
    private void executeOrders() {
        int l_index = 0;
        Order orderToExecute = PlayerHandler.getGamePlayers().get(0).nextOrder();
        do {
            orderToExecute.executeOrder();
            Logger.log("Executing order for: " + PlayerHandler.getGamePlayers().get(l_index % PlayerHandler.getGamePlayers().size()).getPlayerName() + ", Orders remaining: " + PlayerHandler.getGamePlayers().get(l_index % PlayerHandler.getGamePlayers().size()).getOrderSize());
            l_index = (l_index + 1) % PlayerHandler.getGamePlayers().size();
            for (int i = 0; i < PlayerHandler.getGamePlayers().size(); i++) {
                orderToExecute = PlayerHandler.getGamePlayers().get(l_index % PlayerHandler.getGamePlayers().size()).nextOrder();
                if (orderToExecute == null) {
                    l_index = (l_index + 1) % PlayerHandler.getGamePlayers().size();
                } else {
                    break;
                }

            }

        } while (orderToExecute != null);
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
