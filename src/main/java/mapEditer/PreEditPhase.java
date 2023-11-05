package mapEditer;

import common.Command;
import common.CommandAttribute;
import common.Logging.Logger;

import java.util.ArrayList;

import static java.util.Objects.isNull;
import static mapEditer.MapEditorCommands.*;

/**
 * This class implements the operations in "PREEDITMODE" Phase of the mapEditor
 * MapEditor is the context class for this phase.
 *
 * @author TaranjeetKaur
 */
public class PreEditPhase extends Phase{
    public PreEditPhase(MapEditor p_mapEditor) {
        super(p_mapEditor);
    }

    /**
     * This method checks whether the savemap command is valid.
     * verifies if filename is passed.
     *
     * @param p_command command object passed down by the application
     * @return  true if command valid, false otherwise.
     */
    private boolean isValidSaveCommand(Command p_command){
        if (p_command.getCmdAttributes().isEmpty() || p_command.getCmdAttributes().get(0).getArguments().isEmpty()) {
            System.out.println("File name missing to save map.");
            return false;
        }

        if (!p_command.getCmdAttributes().get(0).getOption().isEmpty()) {
            System.out.println("This command does not support options.");
            return false;
        }
        return true;
    }

    /**
     * This method checks whether the editmap command is valid.
     * verifies if filename is passed.
     *
     * @param p_command command object passed down by the application
     * @return  true if command valid, false otherwise.
     */
    private boolean isValidEditCommand(Command p_command){
        if(!p_command.getCmdAttributes().isEmpty() && !p_command.getCmdAttributes().get(0).getOption().isEmpty()) {
            System.out.println("Incorrect command, invalid option found" + p_command.toString());
            return false;
        }
        return true;
    }

    /**
     * This method checks whether the mapEditor is in valid state before proceeding to edit commands
     * verifies the map loaded for editing is initialised.
     *
     * @return  true if state is valid, false otherwise
     */
    private boolean isMapEditorInValidState(){
        if (!d_mapEditor.isMapEditorInitialised()) {
            System.out.println("Map not loaded for editing, use editmap for loading an existing map or creating a new one!");
            return false;
        }
        if (isNull(d_mapEditor.d_map)) {
            Logger.logError("Inconsistent state!, something went wrong!");
            return false;
        }
        return true;
    }

    /**
     * This method checks whether the edit commands for country, continent, neighbor are valid or not.
     * verifies the number of attributes are correct for add, remove operations.
     *
     * @param p_command command object passed down by the application
     * @return  true if command valid, false otherwise.
     */
    private boolean isValidEditEntityCommand(Command p_command){
        //validate the cmd attributes for edit-country,continent,neighbour:
        ArrayList<CommandAttribute> l_commandAttributes = p_command.getCmdAttributes();
        if (l_commandAttributes.isEmpty()) {
            return false;
        }
        for (CommandAttribute l_cAttribute : l_commandAttributes) {
            if (l_cAttribute.getOption().isEmpty() || !VALIDOPTIONS.contains(l_cAttribute.getOption())) {
                Logger.log("Invalid option for command:" + p_command.getCmdName());
                return false;
            }
            if (l_cAttribute.getArguments().isEmpty()) {
                Logger.log("No arguments given for command:" + p_command.getCmdName());
                return false;
            }
            if (CMD_OPTION_ADD.equals(l_cAttribute.getOption()) && l_cAttribute.getArguments().size() % 2 != 0) {
                Logger.log("Odd number of arguments for -add continent");
                return false;
            }
            if (CMD_EDIT_NEIGHBOR.equals(p_command.getCmdName()) && CMD_OPTION_REMOVE.equals(l_cAttribute.getOption()) && l_cAttribute.getArguments().size() % 2 != 0) {
                Logger.log("Odd number of arguments for edit neighbor");
                return false;
            }
        }
        return true;
    }

    /**
     * This method is for phase1-PreEdit: command and input paramters validation
     *
     * @param p_command command object passed down by application
     * @return status true if command is valid, false otherwise.
     */
    public boolean isValidCommand(Command p_command){
        String l_command = p_command.getCmdName();
        boolean isValidCommand = false;
        switch (l_command){
            case CMD_SAVE_MAP : {
                isValidCommand = isValidSaveCommand(p_command);
                break;
            }
            case CMD_EDIT_MAP : {
                isValidCommand =  isValidEditCommand(p_command);
                break;
            }
            case CMD_SHOW_MAP: {
                isValidCommand =  true;
                break;
            }
            case CMD_VALIDATE_MAP: {
                isValidCommand = isMapEditorInValidState();
                break;
            }
            default: {
                isValidCommand = isMapEditorInValidState() && isValidEditEntityCommand(p_command);
                break;
            }
        }

        return  isValidCommand;
    }

    /**
     * This method is for phase2-InEdit: command execution
     *
     * @param p_command command object passed down by application
     * @return status of command operation
     */
    public boolean executeCommand(Command p_command){
        InvalidCommandMessage();
        return false;
    }

    /**
     * This method is for phase2-PostEdit: Logging and other cleanup.
     *
     * @return  status of application.
     */
    public boolean postExecute(){
        InvalidCommandMessage();
        return false;
    }

}
