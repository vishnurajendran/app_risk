package game.States;
import game.States.Concrete.*;

/**
 * @author Soham
 */
public class GameStateFactory {
    private GameStateFactory(){

    }
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
