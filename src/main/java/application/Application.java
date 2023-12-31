package application;

import common.*;
import common.Logging.Logger;
import game.GameCommands;

import java.text.MessageFormat;
import java.util.HashMap;

/**
 * This Class is the first layer.
 * Application will manage switching of modes
 * and also interpreting first layer of commands.
 *
 * @author vishnurajendran
 */


public class Application {

    private final ISubAppInstantiator d_gameInstantiator;
    private final ISubAppInstantiator d_mapEditorInstantiator;
    private final ISubAppInstantiator d_tournamentInstantiator;
    private final HashMap<String, IMethod> d_cmdToActionMap;
    private ISubApplication d_activeSubApplication;
    private boolean d_initialised;
    private boolean d_hasQuit;
    private AppState d_appState = AppState.Standard;


    /**
     * Default constructor,
     * sets the object to partially initialised state.
     * ie: the application will accept app commands only.
     */
    public Application() {
        this(null, null, null);
    }

    /**
     * Parameterised constructor for Application
     *
     * @param p_gameInstantiator      instance of the game instantiator
     * @param p_mapEditorInstantiator instance of the map-editor instantiator
     */
    public Application(ISubAppInstantiator p_gameInstantiator, ISubAppInstantiator p_mapEditorInstantiator, ISubAppInstantiator p_tournamentInstantiator) {
        d_cmdToActionMap = new HashMap<>();
        d_gameInstantiator = p_gameInstantiator;
        d_mapEditorInstantiator = p_mapEditorInstantiator;
        d_tournamentInstantiator = p_tournamentInstantiator;
    }

    /**
     * @return true if application is initialised else false
     */
    public boolean isInitialised() {
        return d_initialised;
    }

    /**
     * @return current state of application, values can be Standard, Game or MapEditor
     */
    public AppState getAppState() {
        return d_appState;
    }

    /**
     * Fetches the application quit status
     *
     * @return true if application has quit, else false
     */
    public boolean hasQuit() {
        return d_hasQuit;
    }

    /**
     * This method is intended to initialise
     * This instance.
     */
    public void startup() {
        if (d_gameInstantiator == null || d_mapEditorInstantiator == null || d_tournamentInstantiator == null) {
            Logger.logWarning("application has no references for map editor,game or tournament instantiators available, no commands will be processed");
            return;
        }
        registerAppCommands();
        d_initialised = true;
    }

    /**
     * This method processes the command provided.
     *
     * @param p_command command passed to application
     */
    public void processCommand(Command p_command) {

        if (d_hasQuit)
            return;

        if (!d_initialised) {
            Logger.logWarning("application is not initialised, all commands are ignored");
            return;
        }

        //check if command is an app command
        if (d_cmdToActionMap.containsKey(p_command.getCmdName())) {
            //run command at application level.
            Logger.log("Found application level command " + p_command.getCmdName());
            d_cmdToActionMap.get(p_command.getCmdName()).invoke(p_command);
        } else if (d_activeSubApplication != null && d_activeSubApplication.canProcess(p_command.getCmdName())) {
            //send it to the active sub-application.
            Logger.log("sending command to sub-application " + p_command.getCmdName());
            d_activeSubApplication.submitCommand(p_command);
        } else
            printInvalidCommandMessage(p_command);

        //check if a command has put the sub-app to a closed state.
        //if yes, we simply close this instance and return standard application state.
        if (d_activeSubApplication != null && d_activeSubApplication.hasQuit())
            closeCurrSubAppInstance();
    }

    /**
     * Prints the invalid command to the console
     *
     * @param p_cmd the command that was input by the user.
     */
    private void printInvalidCommandMessage(Command p_cmd) {
        System.out.println(MessageFormat.format(ApplicationConstants.ERR_MSG_INVALID_CMD, p_cmd.getCmdName()));
    }

    /**
     * print invalid messages.
     * @param state
     * @param p_cmdName
     */
    private void printInvalidStateStartCmdUsage(AppState state, String p_cmdName) {
        switch (state) {
            case Game:
                System.out.println(
                        MessageFormat.format(
                                ApplicationConstants.ERR_MSG_INVALID_START_GAME_CMD_USAGE,
                                p_cmdName));
                break;
            case MapEditor:
                System.out.println(ApplicationConstants.ERR_MSG_INVALID_MAP_EDITOR_GAME_CMD_USAGE);
                break;
            case Tournament:
                System.out.println(ApplicationConstants.ERR_MSG_INVALID_TOURNAMENT_CMD_USAGE);
                break;
            default:
                System.out.println(ApplicationConstants.ERR_INVALID_CMD_USAGE);
        }
    }

    /**
     * This method does a clean remove of the current active sub-application
     * and sets app state to Standard
     */
    private void closeCurrSubAppInstance() {
        if (d_activeSubApplication != null) {
            System.out.println("closing current sub-application");
            d_activeSubApplication.shutdown();
            d_activeSubApplication = null;
        }
        d_appState = AppState.Standard;
    }

    /**
     * This method is intended to do clean pass to
     * release any resources that was used by application.
     */
    public void cleanup() {
        closeCurrSubAppInstance();
        d_cmdToActionMap.clear();
    }


    //region Aplication commands

    /**
     * This method will register all application level commands
     * to the internal command map. which will be later invoked
     * when processing commands.
     */
    private void registerAppCommands() {
        Logger.log("Registering App Commands");
        d_cmdToActionMap.put(ApplicationConstants.CMD_EXIT_APP, this::cmdExitApp);
        d_cmdToActionMap.put(ApplicationConstants.CMD_EXIT_SUB_APPLICATION, this::cmdExitSubApp);
        d_cmdToActionMap.put(ApplicationConstants.CMD_START_GAME, this::cmdStartGame);
        d_cmdToActionMap.put(ApplicationConstants.CMD_LOAD_GAME, this::cmdStartGame);
        d_cmdToActionMap.put(ApplicationConstants.CMD_START_MAPEDITOR, this::cmdStartMapEditor);
        d_cmdToActionMap.put(ApplicationConstants.CMD_START_TOURNAMENT, this::cmdStartTournament);
        d_cmdToActionMap.put(ApplicationConstants.CMD_HELP, this::cmdHelp);
        Logger.log("Registered " + d_cmdToActionMap.size() + " Entries");
    }

    /**
     * This method closes the current active sub-application.
     *
     * @param p_command cmd object sent for additional processing
     */
    private void cmdExitSubApp(Command p_command) {
        if (d_activeSubApplication != null)
            closeCurrSubAppInstance();
        else
            System.out.println(ApplicationConstants.MSG_INVALID_EXIT_CMD);
    }

    /**
     * This method handles how the application handles app close.
     * This method is invoked by the process command
     * when app quit command is issued.
     *
     * @param p_command cmd object sent for additional processing
     */
    private void cmdExitApp(Command p_command) {
        closeCurrSubAppInstance();
        d_hasQuit = true;
        Logger.log("Application has quit.");
    }

    /**
     * This method loads a new instance of the game
     *
     * @param p_command cmd object sent for additional processing
     */
    private void cmdStartGame(Command p_command) {
        Logger.log("Loading new Game Instance");
        if (!d_appState.equals(AppState.Standard)) {
            printInvalidStateStartCmdUsage(AppState.Game, p_command.getCmdName());
            return;
        }

        closeCurrSubAppInstance();
        d_activeSubApplication = createInstance(d_gameInstantiator);

        if (d_activeSubApplication == null) {
            Logger.logError("Game Instance is null");
        } else {
            d_appState = AppState.Game;
            //This command will be further processed by the map editor
            d_activeSubApplication.submitCommand(p_command);
        }
    }

    /**
     * This method handles loads a new instance of the map editor.
     *
     * @param p_command cmd object sent for additional processing
     */
    private void cmdStartMapEditor(Command p_command) {
        Logger.log("Loading new Map editor Instance");
        if (!d_appState.equals(AppState.Standard)) {
            printInvalidStateStartCmdUsage(AppState.MapEditor, p_command.getCmdName());
            return;
        }

        closeCurrSubAppInstance();
        d_activeSubApplication = createInstance(d_mapEditorInstantiator);

        if (d_activeSubApplication == null) {
            Logger.logError("Map-Editor Instance is null");
        } else {
            d_appState = AppState.MapEditor;
            //This command will be further processed by the map editor
            d_activeSubApplication.submitCommand(p_command);
        }
    }

    /**
     * This method loads a new instance of the tournament mode game
     *
     * @param p_command cmd object sent for additional processing
     */
    private void cmdStartTournament(Command p_command) {
        Logger.log("Loading new Tournament Instance");
        if (!d_appState.equals(AppState.Standard)) {
            printInvalidStateStartCmdUsage(AppState.Game, p_command.getCmdName());
            return;
        }

        closeCurrSubAppInstance();
        d_activeSubApplication = createInstance(d_tournamentInstantiator);

        if (d_activeSubApplication == null) {
            Logger.logError("Tournament Instance is null");
        } else {
            d_appState = AppState.Tournament;
            //This command will be further processed by the map editor
            d_activeSubApplication.submitCommand(p_command);
        }
    }

    /**
     * this method handles the help command invoke
     * @param p_cmd cmd object sent for additional processing
     */
    private void cmdHelp(Command p_cmd){
        String l_msg = "---- Help ----\n";
        l_msg += "[ Application Commands ]\n";

        if(d_appState == AppState.Standard) {
            l_msg += "\tloadmap filename\n";
            l_msg += "\teditmap filename\n";
            l_msg += "\t loadgame filename\n";
            l_msg += "\texit\n";
        }

        l_msg += "\texitapp\n";

        if(d_activeSubApplication != null)
            l_msg += d_activeSubApplication.getHelp();

        System.out.println(l_msg);
    }

    /**
     * Creates an instance of type ISubApplication using the instantitator provided.
     * and also attempts at initialisation before returning the instance.
     *
     * @param p_instantiator the instantiator used for creating the instance for ISubApplication.
     * @return instance of ISubApplication using the p_instantiator
     */
    private ISubApplication createInstance(ISubAppInstantiator p_instantiator) {
        ISubApplication l_subApp = p_instantiator.createInstance();
        if (l_subApp != null)
            l_subApp.initialise();
        return l_subApp;
    }
}
