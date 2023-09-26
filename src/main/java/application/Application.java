package application;
import common.Command;
import common.ISubAppInstantiator;
import common.ISubApplication;
import common.Logger;

/**
 * This Class is the first layer.
 * Application will manage switching of modes
 * and also interpreting first layer of commands.
 * @author vishnurajendran
 * Dated 23-09-2023
 */
public class Application {
    private ISubAppInstantiator d_gameInstantiator;
    private ISubAppInstantiator d_mapEditorInstantiator;
    private ISubApplication d_activeSubApplication;

    private boolean initialised;

    public Application(){
        this(null,null);
    }

    /**
     * parameterised constructor for Application
     * @param p_gameInstantiator instance of the game instantiator
     * @param p_mapEditorInstantiator instance of the map-editor instantiator
     */
    public Application(ISubAppInstantiator p_gameInstantiator, ISubAppInstantiator p_mapEditorInstantiator){
        d_gameInstantiator = p_gameInstantiator;
        d_mapEditorInstantiator = p_mapEditorInstantiator;
    }

    /**
     * This method is intended to initialise
     * this instance.
     */
    public void startup(){
        //Todo: add startup code.
        if(d_gameInstantiator == null || d_mapEditorInstantiator == null)
            return;
        initialised = true;
    }

    /**
     * this method process the command provided.
     * @param p_command command passed to application
     */
    public void processCommand(Command p_command){
        if(!initialised){
            Logger.logWarning("Application is not initialised, skipping input command");
            return;
        }
    }

    /**
     * This method is intended to do clean pass to
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
