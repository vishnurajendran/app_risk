package game;

/**
 * @author Soham
 */
public class GameStateFactory {
    private GameStateFactory(){

    }
    public static IGameState get(GameStates p_newState) {
        return switch (p_newState) {
            case GameStates.Initial -> new InitialGame();
            case GameStates.DeployMode -> new DeployHandler();
                default -> null;
        };
    }
}
