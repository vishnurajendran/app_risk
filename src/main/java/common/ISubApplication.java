package common;

/**
 * @author vishnurajendran
 * Dated 23-09-2023
 */
public interface ISubApplication {

    /**
     * this method will be called by the application class
     * when sub application instance is created, its intended
     * to be an initialisation call.
     */
    public void initialise();

    /**
     * this method will be called by the application class to
     * submit a command for processing to the sub application.
     * @param p_command
     */
    public void submitCommand(Command p_command);

    /**
     * this method will be called by the application class
     * when its about to discard the sub application, its intended
     * to be a final chance to clean up any resources used.
     */
    public void shutdown();
}
