package game.States;

import game.Context;

/**
 * @author Soham
 */
public abstract class GameState implements IGameState {
    protected Context ctx;
    @Override
    public void setContext(Context ctx) {
        this.ctx = ctx;
    }
}
