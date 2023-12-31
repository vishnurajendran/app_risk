package common;

/**
 * This interface act as the basis of any sub-applicaton
 * it provides the required methods that needs to be defined to
 * be compatible with application class.
 *
 * @author vishnurajendran
 */
public interface ISubApplication {

    /**
     * This method will be called by the application class
     * when sub application instance is created, its intended
     * to be an initialisation call.
     */
    public void initialise();

    /**
     * This function is used to check if a sub-application has quit
     * on its own without a user input, in cases where the sub-application
     * is not able to proceed safely, it can set this function to return true
     * to make the Application layer perform an auto-quit, post a command query.
     *
     * @return true if sub-application has quit, else false
     */
    public boolean hasQuit();

    /**
     * This method will be called by the application class
     * when it is processing a command from the user. This
     * method will validate if a certain command can be processed
     * by this instance.
     *
     * @param p_cmdName name of the command for validation.
     * @return true if cmdName can be processed, else false
     */
    public boolean canProcess(String p_cmdName);

    /**
     * This method will be called by the application class to
     * submit a command for processing to the sub application.
     *
     * @param p_command
     */
    public void submitCommand(Command p_command);

    /**
     * This method will be called by the application class
     * when its about to discard the sub application, its intended
     * to be a final chance to clean up any resources used.
     */
    public void shutdown();

    /**
     * @return a help string for this application.
     */
    public String getHelp();

}
