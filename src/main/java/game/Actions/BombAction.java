package game.Actions;

import common.Command;
import game.Context;

/**
 * @author Soham
 */
public class BombAction extends IssueOrderAction {
    private Context d_context;
    @Override
    public void SetContext(Context p_ctx) {
        this.d_context = p_ctx;
    }

    @Override
    public void execute(Command cmd) {

    }

    @Override
    public void cleanup() {

    }
}
