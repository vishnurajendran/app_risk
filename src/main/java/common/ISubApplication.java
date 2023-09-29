package common;

/**
 * This interface act as the basis of any sub-applicaton
 * it provides the required methods that needs to be defined to
 * be compatible with application class.
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
     * This method will be called by the application class
     * when it is processing a command from the user. This
     * method will validate if a certain command can be processed
     * by this instance.
     * @param p_cmdName name of the command for validation.
     * @return true if cmdName can be processed, else false
     */
    public boolean canProcess(String p_cmdName);

    /**
     * This method will be called by the application class to
     * submit a command for processing to the sub application.
     * @param p_command
     */
    public void submitCommand(Command p_command);

    /**
     * This method will be called by the application class
     * when its about to discard the sub application, its intended
     * to be a final chance to clean up any resources used.
     */
    public void shutdown();
}
