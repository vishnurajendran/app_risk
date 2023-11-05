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
        int canIssueOrder = PlayerHandler.issueOrder(p_command);
        Logger.log(String.valueOf(canIssueOrder));
        if(canIssueOrder != PlayerHandler.ISSUEORDER_SUCCESS){
            System.out.println(GameCommands.DEPLOYERRORMESSAGE.get(canIssueOrder-1));
        }
    }

    @Override
    public void postExecute() {
        // nothing here
    }
}
