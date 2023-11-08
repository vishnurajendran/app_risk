package game.Actions;

import common.Command;
import common.Logging.Logger;
import entity.PlayerHandler;
import game.GameCommands;

/**
 * @author Soham
 */
public class DeployAction extends GameAction {

    @Override
    public void execute(Command p_command){
        int l_issueOrderResult = PlayerHandler.issueOrder(p_command);
        if(l_issueOrderResult != PlayerHandler.ISSUEORDER_SUCCESS){
            d_execStatus = ActionExecStatus.Fail;
            System.out.println(GameCommands.DEPLOYERRORMESSAGE.get(l_issueOrderResult-1));
        }
    }

    @Override
    public void postExecute() {
        // nothing here
    }
}
