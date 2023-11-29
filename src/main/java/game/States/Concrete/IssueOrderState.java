package game.States.Concrete;

import common.Command;
import common.IMethod;
import entity.Player;
import entity.PlayerHandler;
import game.Actions.ActionExecStatus;
import game.Actions.GameAction;
import game.Actions.GameActionFactory;
import game.Data.Context;
import game.GameCommands;
import game.States.GameState;
import game.States.GameStates;

import java.util.HashMap;

/**
 * This state is handles the issue order state.
 * all commands are accepted in this state.
 * @author Soham
 */
public class IssueOrderState extends GameState {

    private final HashMap<String, IMethod> d_cmdToActionMap;

    /**
     * the default constructor initialises the command map with the
     * respective methods to invoke, when the appropriate command
     * is entered by the player.
     */
    public IssueOrderState(){
        //
        d_cmdToActionMap = new HashMap<>();
        d_cmdToActionMap.put(GameCommands.CMD_DEPLOY_COUNTRIES, this::cmdDeployAction);
        d_cmdToActionMap.put(GameCommands.CMD_BOMB, this::cmdBombAction);
        d_cmdToActionMap.put(GameCommands.CMD_BLOCKADE, this::cmdBlockadeAction);
        d_cmdToActionMap.put(GameCommands.CMD_ADVANCE, this::cmdAdvanceAction);
        d_cmdToActionMap.put(GameCommands.CMD_AIRLIFT, this::cmdAirliftAction);
        d_cmdToActionMap.put(GameCommands.CMD_COMMIT, this::cmdCommitAction);
        d_cmdToActionMap.put(GameCommands.CMD_DIPLOMACY, this::cmdNegotiateAction);
    }

    /**
     * setContext method overridden to display current player
     * details if game has already started (this is only invoked when
     * issue order state is switched to after execution state).
     * @param p_ctx context to set
     */
    @Override
    public void setContext(Context p_ctx) {
        super.setContext(p_ctx);
        if(d_context.getEngine().gameStarted()){
            displayPlayerDetails();
        }
    }

    /**
     * invokes a method from the command to action map
     * according to the command.
     * @param p_command command to perform
     */
    @Override
    public void performAction(Command p_command) {
        d_cmdToActionMap.get(p_command.getCmdName()).invoke(p_command);
        issueOrdersForAI();
        super.setContext(new Context(PlayerHandler.getCurrentPlayer(), d_context.getEngine()));
        d_context.getCurrentPlayer().setStrategyContext(d_context.getEngine());
    }

    public void issueOrdersForAI(){
        while(!PlayerHandler.getCurrentPlayer().isPlayerHuman() && !PlayerHandler.isCommittedPlayer(PlayerHandler.getCurrentPlayer())){
            PlayerHandler.getCurrentPlayer().setStrategyContext(d_context.getEngine());
            PlayerHandler.getCurrentPlayer().issueOrder();
            PlayerHandler.increasePlayerTurn(1);
        }
        if(!d_context.getCurrentPlayer().equals(PlayerHandler.getCurrentPlayer())){
            displayPlayerDetails();
        }
    }

    /**
     * Executes actions and checks if more commands can be allowed.
     * @param p_action is the current action to be performed.
     * @param p_command command that is to be processed.
     */
    private void executeAction(GameAction p_action, Command p_command){
        // we want the updated current player to go to this state
        p_action.setContext(new Context(PlayerHandler.getCurrentPlayer(), d_context.getEngine()));
        p_action.execute(p_command);
        p_action.postExecute();

        // if the action failed, we stay in the same player turn.
        if(!d_context.getEngine().gameStarted() || p_action.getExecutionStatus() == ActionExecStatus.Fail)
            return;

        updateState();
    }

    /**
     * update game state accordingly.
     * either switch to execute state or stay in same state.
     */
    private void updateState(){
        //since advance can be called multiple times, we need to keep waiting for all players to commit :/
        //if all commited, we change state to execute order.
        if(PlayerHandler.getCommittedPlayerCount() >= PlayerHandler.getGamePlayers().size()) {
            d_context.getEngine().changeState(GameStates.ExecuteOrder);
            return;
        }
        PlayerHandler.increasePlayerTurn(1);
        displayPlayerDetails();
    }

    /**
     * display current player details
     */
    private void displayPlayerDetails(){
        Player l_player = PlayerHandler.getCurrentPlayer();
        if(l_player == null)
            return;

        String p_header = "\n\n[  CURRENT TURN (" + PlayerHandler.getPlayerTurn() + ")  ]\n";
        System.out.println(p_header + l_player);
        PlayerHandler.displayGamePlayersCountries(l_player);
    }

    /**
     * @param p_cmdName command to check
     * @return true if command is processable in this state.
     */
    @Override
    public boolean canProcessCommand(String p_cmdName) {
        return GameCommands.CHECK_VALID_COMMANDS_FOR_ISSUEORDER.contains(p_cmdName);
    }

    /**
     * executes deploy action
     * @param p_command p_command for further processing
     */
    private void cmdDeployAction(Command p_command){
        if(!validGameCommandAtThisTime())
            return;

        GameAction l_action = GameActionFactory.getDeployAction();
        executeAction(l_action, p_command);
    }

    /**
     * executes advance action
     * @param p_command p_command for further processing
     */
    private void cmdAdvanceAction(Command p_command){
        if(!validGameCommandAtThisTime())
            return;

        GameAction l_action = GameActionFactory.getAdvanceAction();
        executeAction(l_action, p_command);
    }

    /**
     * executes airlift action
     * @param p_command p_command for further processing
     */
    private void cmdAirliftAction(Command p_command){
        if(!validGameCommandAtThisTime())
            return;

        GameAction l_action = GameActionFactory.getAirliftAction();
        executeAction(l_action, p_command);
    }

    /**
     * executes blockade action
     * @param p_command p_command for further processing
     */
    private void cmdBlockadeAction(Command p_command){
        if(!validGameCommandAtThisTime())
            return;

        GameAction l_action = GameActionFactory.getBlockadeAction();
        executeAction(l_action, p_command);
    }

    /**
     * executes bomb action
     * @param p_command p_command for further processing
     */
    private void cmdBombAction(Command p_command){
        if(!validGameCommandAtThisTime())
            return;

        GameAction l_action = GameActionFactory.getBombAction();
        executeAction(l_action, p_command);
    }

    /**
     * executes commit action
     * @param p_command p_command for further processing
     */
    private void cmdCommitAction(Command p_command){
        if(!validGameCommandAtThisTime())
            return;

        GameAction l_action = GameActionFactory.getCommitAction();
        executeAction(l_action, p_command);
    }

    /**
     * executes negotiate action
     * @param p_command p_command for further processing
     */
    private void cmdNegotiateAction(Command p_command){
        if(!validGameCommandAtThisTime())
            return;

        GameAction l_action = GameActionFactory.getNegotiateAction();
        executeAction(l_action, p_command);
    }

    /**
     * @return true if game commands are supported at this time
     */
    private boolean validGameCommandAtThisTime(){
        if(!d_context.getEngine().gameStarted()){
            System.out.println("You cannot use this command at this time.");
            System.out.println("this command can only be done after " + GameCommands.CMD_ASSIGN_COUNTRIES_TO_PLAYER);
            return false;
        }

        return true;
    }

    /**
     * a help message with all commands supported.
     * @return help string
     */
    @Override
    public String getHelp() {
        String help = "";
        help += "\tdeploy countryID numarmies\n";
        help += "\tadvance countrynamefrom countynameto numarmies\n";
        help += "\tbomb countryID\n";
        help += "\tblockade countryID\n";
        help += "\tairlift sourcecountryID targetcountryID numarmies\n";
        help += "\tnegotiate playerID";
        help += "\tcommit when all orders are issued";
        return help;
    }
}
