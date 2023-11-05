package mapEditer;

import common.Command;
import common.Logging.Logger;

/**
 * This class implements the operations in "POSTEDITMODE" Phase of the mapEditor
 * MapEditor is the context class for this phase.
 *
 * @author TaranjeetKaur
 */
public class PostEditPhase extends Phase{
    public PostEditPhase(MapEditor p_mapEditor) {
            super(p_mapEditor);
        }

    /**
     * This method is for phase1-PreEdit: command and input paramters validation
     *
     * @param p_command command object passed down by application
     * @return false.
     */
    @Override
    boolean isValidCommand(Command p_command) {
        InvalidCommandMessage();
        return false;
    }

    /**
     * This method is for phase2-InEdit: command execution
     *
     * @param p_command command object passed down by application
     * @return status of command operation
     */
    @Override
    boolean executeCommand(Command p_command) {
        InvalidCommandMessage();
        return false;
    }

    /**
     * This method is for phase2-PostEdit: Logging and other cleanup.
     *
     * @return  status of application.
     */
    @Override
    boolean postExecute() {
        Logger.log("MapEditor Operation Successful");
        return true;
    }
}
