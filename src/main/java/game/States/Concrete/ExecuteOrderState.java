package game.States.Concrete;

import common.Command;
import entity.Player;
import entity.PlayerHandler;
import game.Data.Context;
import game.Orders.Order;
import game.States.GameState;
import game.States.GameStates;

import java.util.ArrayList;
import java.util.List;

/**
 * This state executes all orders in a round-robin fashion,
 * post execution switches to next round state or game-over
 * depending on the no of players remaining.
 * @author Soham
 */
public class ExecuteOrderState extends GameState {

    /**
     * overrides game state to execute orders and try to update
     * game state.
     * @param p_ctx context to set
     */
    @Override
    public void setContext(Context p_ctx) {
        super.setContext(p_ctx);
        executeOrders();
        tryCheckGameState();
    }

    /**
     * Executes order for each player in a round-robin fashion
     * Once no orders are left to execute, it terminates
     */
    public void executeOrders() {
        int l_index = 0;
        Order orderToExecute = PlayerHandler.getGamePlayers().get(0).nextOrder();
        do {
            orderToExecute.executeOrder();
            System.out.println("Executing order for: " + PlayerHandler.getGamePlayers().get(l_index % PlayerHandler.getGamePlayers().size()).getPlayerName() + ", Orders remaining: " + PlayerHandler.getGamePlayers().get(l_index % PlayerHandler.getGamePlayers().size()).getOrderSize());
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
     * checks game state and shifts state accordingly
     * there are two states possible
     * 1. IssueOrder of game can continue.
     * 2. GameOver is only one player remains.
     */
    private void tryCheckGameState(){
        List<Player> l_playersToRemove = new ArrayList<>();
        for (Player l_player: PlayerHandler.getGamePlayers()) {
            if(l_player.getCountriesOwned().isEmpty())
                l_playersToRemove.add(l_player);
        }

        while(!l_playersToRemove.isEmpty()){
            Player l_playerToRemove = l_playersToRemove.remove(0);
            PlayerHandler.getGamePlayers().remove(l_playerToRemove);
            System.out.println(l_playerToRemove.getPlayerName() + " was eliminated from the game");
        }

        //shift state to game over, if only 1 player exists
        if(PlayerHandler.getGamePlayers().size() == 1){
            d_context.getEngine().changeState(GameStates.GameOver);
        }
        else
            d_context.getEngine().changeState(GameStates.RoundInitState);
    }

    /**
     * no command can be performed in this state.
     * @param p_command command to perform
     */
    @Override
    public void performAction(Command p_command) {
        //nothing here.
    }

    /**
     * returns false, as this state is command less.
     * @param p_cmdName command to check (irrelevant to this state.)
     * @return false always.
     */
    @Override
    public boolean canProcessCommand(String p_cmdName) {
        return false;
    }
}
