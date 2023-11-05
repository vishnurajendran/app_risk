package mapEditer;

import application.ApplicationConstants;
import common.Command;

import java.text.MessageFormat;
import java.util.Map;

/**
 * This class handles all the phases in map editor:
 * PreEdit : this states takes the commands and checks if the command is valid.
 * InEdit : this state performs the edit functions on entities of context class.
 * PostEdit : this state saves the final map after all the edit operations are done.
 *
 * @author TaranjeetKaur
 */
public abstract class Phase {

    MapEditor d_mapEditor;

    Phase (){
        d_mapEditor = null;
    }

    Phase (MapEditor p_mapEditor){
        d_mapEditor = p_mapEditor;
    }

    /**
     * This method is for phase1-PreEdit: command and input paramters validation
     *
     * @param p_command command object passed down by application
     * @return status true if command is valid, false otherwise.
     */
    abstract boolean isValidCommand(Command p_command);

    /**
     * This method is for phase2-InEdit: command execution
     *
     * @param p_command command object passed down by application
     * @return status of command operation
     */
    abstract boolean executeCommand(Command p_command);

    /**
     * This method is for phase2-PostEdit: Logging and other cleanup.
     *
     * @return  status of application.
     */
    abstract boolean postExecute();

    /**
     * This method is called to log invalid commands in different phases.
     */
    public void InvalidCommandMessage(){
        System.out.println("The command is invalid and cannot be processed. please try again");
    }

}
