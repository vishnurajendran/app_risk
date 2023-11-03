package mapEditer;

import common.Command;

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

    abstract boolean isValidCommand(Command p_command);

    abstract boolean executeCommand(Command p_command);

    abstract boolean postExecute();

     //to-do: invalid cmd mssg
    public void InvalidCommandMessage(){
         System.out.println("invalid cmnd");
    }

}
