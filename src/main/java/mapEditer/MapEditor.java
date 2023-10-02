package mapEditer;

import common.Command;
import common.IMethod;
import common.ISubApplication;
import common.Logger;
import entity.RiskMap;
import mapValidator.MapValidator;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * This class handles all the map editor commands
 * - registration and execution.
 *
 * @author TaranjeetKaur
 */
public class MapEditor implements ISubApplication {

    private RiskMap d_map;
    private boolean d_isMapInitialised = false;
    private boolean d_isMapInEditMode = false;


    private final HashMap<String, IMethod> d_cmdToActionMap;

    /**
     * Constructor for mapEditor
     */
    public MapEditor(){
        d_cmdToActionMap = new HashMap<>();
    }

    /**
     * This method will be called by the application class
     * when sub application instance is created, its intended
     * to be an initialisation call.
     */
    @Override
    public void initialise() {
        registerMapEditorCommands();
    }

    /**
     * This method registers all map editor commands and the methods
     * to be invoked for those commands with an internal command map.
     */
    private void registerMapEditorCommands(){
        Logger.log("Registering Map editor commands");
        d_cmdToActionMap.put(MapEditorCommands.CMD_EDIT_CONTINENT, this::cmdEditContinent);
        d_cmdToActionMap.put(MapEditorCommands.CMD_EDIT_COUNTRY, this::cmdEditCountry);
        d_cmdToActionMap.put(MapEditorCommands.CMD_EDIT_NEIGHBOR, this::cmdEditNeighbor);
        d_cmdToActionMap.put(MapEditorCommands.CMD_SHOW_MAP, this::cmdShowMap);
        d_cmdToActionMap.put(MapEditorCommands.CMD_SAVE_MAP, this::cmdSaveMap);
        d_cmdToActionMap.put(MapEditorCommands.CMD_EDIT_MAP, this::cmdEditMap);
        d_cmdToActionMap.put(MapEditorCommands.CMD_VALIDATE_MAP, this::cmdValidateMap);
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
        d_cmdToActionMap.get(p_command.getCmdName()).invoke(p_command);
    }

    /**
     * This method will be called by the application class
     * when its about to discard the sub application, its intended
     * to be a final chance to clean up any resources used.
     */
    @Override
    public void shutdown() {
        //cleanup if any;
        Logger.log("MapEditor has shutdown.");
    }

    /**
     *
     * @param p_command
     */
    private void cmdEditContinent(Command p_command){
        if(d_isMapInEditMode) {
            String l_option = p_command.getCmdAttributes().get(0).getOption();
            if(!MapEditorCommands.VALIDOPTIONS.contains(l_option)){
                //invalid option; exception; exit;
            }
            else{
                ArrayList<String> l_attributes = p_command.getCmdAttributes().get(0).getArguments();
                if(MapEditorCommands.CMD_OPTION_ADD.equals(l_option)){
                    //add validations.
                }
                else if(MapEditorCommands.CMD_OPTION_REMOVE.equals(l_option)){
                    //validations;
                }
            }
        }
        else{
            Logger.log("map not loaded?");
        }
    }

    /**
     *
     * @param p_command
     */
    private void cmdEditCountry(Command p_command){

    }

    /**
     *
     * @param p_command
     */
    private void cmdEditNeighbor(Command p_command){

    }

    private void cmdShowMap(Command p_command){
        //call to display map.
    }

    private void cmdSaveMap(Command p_command){
        //write the map object to file.
    }

    private void cmdEditMap(Command p_command){
        //nullchecks;
        // filname -> loadmap;
        String l_filename = p_command.getCmdAttributes().get(0).getArguments().get(0);
        if(l_filename.isEmpty()) {
            l_filename = "newMap.map";
            //create an empty file.
        }
        MapLoader l_mapLoader = new MapLoader(l_filename);
        d_map = l_mapLoader.getMap();
        //empty;
        //existing -> validate;
        d_isMapInitialised = true;
        d_isMapInEditMode = true;
    }

    private void cmdValidateMap(Command p_command){
        MapValidator.validateMap(d_map);
        //handle empty chks;
    }


}
