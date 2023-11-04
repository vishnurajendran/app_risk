package game.Actions;

import common.Command;
import game.Data.Context;

/**
 * @author Soham
 */
public abstract class GameAction {
    protected Context d_context;
    public void SetContext(Context p_ctx){
        this.d_context = p_ctx;
    }
    public abstract void execute(Command cmd);
    public abstract void postExecute();
}
