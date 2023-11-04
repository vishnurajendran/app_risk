package game.Actions;

import common.Command;
import game.Context;

/**
 * @author Soham
 */
public abstract class IssueOrderAction {
    public abstract void SetContext(Context ctx);
    public abstract void execute(Command cmd);
    public abstract void cleanup();
}
