package game.Actions;

import common.Command;
import game.Data.Context;

/**
 * @author Soham
 */
public abstract class GameAction {
    protected Context d_context;
    protected ActionExecStatus d_execStatus = ActionExecStatus.Success;

    /**
     * sets the context for this action.
     * @param p_ctx context to set.
     */
    public void SetContext(Context p_ctx){
        this.d_context = p_ctx;
    }

    /**
     * the main action to run.
     * @param p_cmd command to run the action with.
     */
    public abstract void execute(Command p_cmd);

    /**
     * meant to be called after execute,
     * the main intention is to clean up any resources used by this action
     */
    public abstract void postExecute();

    /**
     * @return execution status for this action.
     */
    public ActionExecStatus getExecutionStatus() {
        return d_execStatus;
    }
}
