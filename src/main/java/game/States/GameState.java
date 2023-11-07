package game.States;

import game.Data.Context;

/**
 * @author Soham
 */
public abstract class GameState implements IGameState {
    protected Context d_context;
    @Override
    public void setContext(Context p_ctx) {
        this.d_context = p_ctx;
    }
}