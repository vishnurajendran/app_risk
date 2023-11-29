package game.States.Concrete;

import common.Command;
import entity.Player;
import entity.PlayerHandler;
import game.Data.Context;
import game.States.GameState;
import game.States.GameStates;

/**
 * This state acts as the initialisation state for a new round
 * @author vishnurajendran
 */
public class RoundInitState extends GameState {

    @Override
    public void setContext(Context p_ctx) {
        super.setContext(p_ctx);
        PlayerHandler.reassignValuesForNextTurn();
        issueOrderForAI();
        d_context.getEngine().changeState(GameStates.IssueOrder);
        super.setContext(new Context(PlayerHandler.getCurrentPlayer(), d_context.getEngine()));
        d_context.getCurrentPlayer().setStrategyContext(d_context.getEngine());
    }

    /**
     * Handles issuing orders for AI players with strategies.
     */
    public void issueOrderForAI(){
        while(PlayerHandler.getCommittedPlayerCount() < PlayerHandler.getGamePlayers().size()){
            Player l_player = PlayerHandler.getCurrentPlayer();
            if(PlayerHandler.isCommittedPlayer(l_player)) {
                PlayerHandler.increasePlayerTurn(1);
                continue;
            }

            if(l_player.isPlayerHuman())
                break;

            displayPlayerDetails();
            PlayerHandler.getCurrentPlayer().setStrategyContext(d_context.getEngine());
            PlayerHandler.getCurrentPlayer().issueOrder();
            PlayerHandler.increasePlayerTurn(1);
        }

        if(PlayerHandler.getCommittedPlayerCount() >= PlayerHandler.getGamePlayers().size())
            d_context.getEngine().changeState(GameStates.ExecuteOrder);
    }

    /**
     * Used to display the player details
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
     * this state does not handle any commands, this method does nothing
     * here
     * @param command command to perform
     */
    @Override
    public void performAction(Command command) {
        //do nothing
    }

    /**
     * this method will always return false
     * as this state is not supposed to take any commands for execution
     * @param p_cmdName command to check
     * @return
     */
    @Override
    public boolean canProcessCommand(String p_cmdName) {
        return false;
    }
}
