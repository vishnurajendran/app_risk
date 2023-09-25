package Application;

/**
 * This Class is the first layer.
 * Application will manage switching of modes
 * and also interpreting first layer of commands.
 * @author vishnurajendran
 * Dated 23-09-2023
 */
public class Application {
    private ISubApplication d_activeSubApplication;

    /**
     * This method is intended to initialise
     * this instance.
     */
    public void startup(){
        //Todo: add startup code.
    }

    /**
     * this method process the command provided.
     * @param p_command command passed to application
     */
    public void processCommand(ApplicationCommand p_command){
        System.out.println(p_command.getCmdName() + " : " + p_command.getCmdOption()  + " : " + p_command.getCmdArgs());
    }

    /**
     * This method is intended to do clean pass. to
     * release any resources that was used by application.
     * this instance.
     */
    public void cleanup(){
        //Todo: add cleanup code
    }

    /**
     * @return true if application is quit, else false
     */
    public boolean hasQuit(){
        //Todo: drive this by a boolean
        return false;
    }
}
