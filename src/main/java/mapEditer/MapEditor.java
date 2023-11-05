package mapEditer;

import common.*;
import common.Logging.Logger;
import entity.RiskMap;


/**
 * This class handles all the map editor commands
 * - registration and execution./
 *
 * @author TaranjeetKaur
 */
public class MapEditor implements ISubApplication {

    private boolean d_hasQuit;
    public RiskMap d_map;
    public String d_filename;
    public boolean d_isMapInitialised = false;
    private Phase d_phase;

    /**
     * Constructor for mapEditor
     */
    public MapEditor() {
        d_hasQuit = false;
    }

    /**
     * quits the map editor.
     */
    public  void quitMapEditor() {
        d_hasQuit = true;
    }

    public boolean isMapEditorInitialised(){
        return d_isMapInitialised;
    }

    /**
     * This method will be called by the application class
     * when sub application instance is created, its intended
     * to be an initialisation call.
     */
    @Override
    public void initialise() {

    }

    /**
     * This function is used to check if a sub-application has quit
     * on its own without a user input, in cases where the sub-application
     * is not able to proceed safely, it can set this function to return true
     * to make the Application layer perform an auto-quit, post a command query.
     *
     * @return true if sub-application has quit, else false
     */
    @Override
    public boolean hasQuit() {
        return d_hasQuit;
    }

    /**
     * This method will be called by the application class
     * when it is processing a command from the user. This
     * method will validate if a certain command can be processed
     * by this instance.
     *
     * @param p_cmdName name of the command for validation.
     * @return true if cmdName can be processed, else false
     */
    @Override
    public boolean canProcess(String p_cmdName) {
        return MapEditorCommands.VALIDCOMMANDS.contains(p_cmdName);
    }

    /**
     * This method will be called by the application class to
     * submit a command for processing to the sub application.
     *
     * @param p_command
     */
    @Override
    public void submitCommand(Command p_command) {
        Logger.log("Map editor level command: " + p_command.getCmdName());
        d_phase = new PreEditPhase(this);
        boolean l_proceedToNextPhase = d_phase.isValidCommand(p_command);
        if(l_proceedToNextPhase){
           d_phase = new InEditPhase(this);
           boolean l_executionSuccessful = d_phase.executeCommand(p_command);
           if(l_executionSuccessful){
               d_phase = new PostEditPhase(this);
               d_phase.postExecute();
           }
           else{
               System.out.println("MapEdit Operation Unsuccessful!");
           }
        }
        else{
            System.out.println("Incorrect command!" + p_command.getCmdName());
            quitMapEditor();
        }
    }

    /**
     * This method will be called by the application class
     * when its about to discard the sub application, its intended
     * to be a final chance to clean up any resources used.
     */
    @Override
    public void shutdown() {
        Logger.log("MapEditor has shutdown.");
    }

}
