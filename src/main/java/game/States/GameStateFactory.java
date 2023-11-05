package game.States;
import game.States.Concrete.*;

/**
 * @author Soham
 */
public class GameStateFactory {
    private GameStateFactory(){

    }

    /**
     * Get an instance of the game state corresponding to the game state enum.
     * @param p_newState state of which an instance is required
     * @return an instance of the state requested
     */

    public static IGameState get(GameStates p_newState) {
        return switch (p_newState) {
            case GameStart -> new GameStartState();
            case IssueOrder -> new IssueOrderState();
            case ExecuteOrder -> new ExecuteOrderState();
            case GameOver -> new GameOver();
                default -> null;
        };
    }
}
