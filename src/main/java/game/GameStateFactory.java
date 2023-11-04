package game;

import game.States.GameStates;
import game.States.IGameState;

/**
 * @author Soham
 */
public class GameStateFactory {
    private GameStateFactory(){

    }
    public static IGameState get(GameStates p_newState) {
        return switch (p_newState) {
            case GameStart -> new GameStart();
            case IssueOrder -> new IssueOrder();
            case ExecuteOrder -> new ExecuteOrders();
            case GameOver -> new GameOver();
                default -> null;
        };
    }
}
