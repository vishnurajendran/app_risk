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

    abstract boolean isValidCommand(Command p_command);

    abstract boolean executeCommand(Command p_command);

    abstract boolean postExecute();

    public void InvalidCommandMessage(){
        System.out.println("The command is invalid and cannot be processed. please try again");
    }

}
