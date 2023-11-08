package game.States;

/**
 * This enum provides the states of the game
 * at a given point
 *
 * @author Soham
 */
public enum GameStates {

    /**
     * Starting state of game.
     */
    GameStart,

    /**
     * this state acts as the init state
     * for a new round of the game, if game has not
     * been started, changes state quietly.
     */
    RoundInitState,

    /**
     * Deploying state of the game.
     */
    IssueOrder,

    /**
     * Executing state of the game.
     */
    ExecuteOrder,

    /**
     * GameOver state
     */
    GameOver,

}
