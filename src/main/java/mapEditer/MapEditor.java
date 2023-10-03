package mapEditer;

import common.Command;
import common.IMethod;
import common.ISubApplication;
import common.Logger;
import entity.RiskMap;
import mapValidator.MapValidator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static java.util.Objects.isNull;
import static mapEditer.MapEditorCommands.*;


/**
 * This class handles all the map editor commands
 * - registration and execution.
 *
 * @author TaranjeetKaur
 */
public class MapEditor implements ISubApplication {

    private RiskMap d_map;
    private String d_filename;
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
        RiskMap l_riskMap = d_map;
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

    /**
     * Creates an empty map file
     *
     * @param p_name    name of the file to be created
     * @return  true if file creation is successful, false otherwise.
     */
    private boolean createNewMapFile(String p_name){
        try {
            File l_mapFile = new File(p_name);
            if (l_mapFile.createNewFile()) {
                Logger.log("New map file created: " + p_name);
            } else {
                Logger.log("File already exists!");
            }
        } catch (IOException e) {
            Logger.logError("Exception: " + e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Implementation of command-editmap (arg1)
     * creates a new map file if no arg is passed
     * loads the map from map file passed as an argument.
     * Prints an error message is file loaded is invalid.
     *
     * @param p_command Command object passed down from application.
     */
    private void cmdEditMap(Command p_command) {
        String l_filename = "";
        if(p_command.getCmdAttributes().isEmpty()) {
            //creating new map file for editmap
            Logger.log("File name not found!, Creating a map from scratch");
            if(createNewMapFile(NEW_MAP_FILE_NAME)){
                d_filename = NEW_MAP_FILE_NAME;
                d_map = new RiskMap(NEW_MAP_FILE_NAME);
                d_isMapInitialised = true;
            }
            return;
        }
        else if(!p_command.getCmdAttributes().get(0).getOption().isEmpty()) {
            System.out.println("Incorrect command, invalid option found" + p_command.toString());
            return;
        }
        else if(!p_command.getCmdAttributes().get(0).getArguments().isEmpty()){
            l_filename =  p_command.getCmdAttributes().get(0).getArguments().get(0);
        }
        else {
            System.out.println("Invalid arguments!" + p_command.toString());
            return;
        }

        //load the map from existing file and validate before proceeding further
        MapLoader l_mapLoader = new MapLoader(l_filename);
        RiskMap l_riskMap = l_mapLoader.getMap();
        if(isNull(l_riskMap) || !MapValidator.validateMap(l_riskMap)) {
            System.out.println("Invalid map!, load another file or start from scratch!");
            return;
        }

        d_isMapInitialised = true;
        d_map = l_riskMap;  //clone?
        d_filename = l_filename;
        Logger.log("Command " + p_command.toString() + " Successful!");
    }

    /**
     * Implementation of command-validatemap
     * Validates a map and prints the result to the console ,if the map is loaded in the mapEditor
     *
     * @param p_command Command object passed down from application.
     */
    private void cmdValidateMap(Command p_command){
        if(!d_isMapInitialised || isNull(d_map)) {
            System.out.println("No Map loaded in the mapEditor!");
            return;
        }
        boolean l_isMapValid = MapValidator.validateMap(d_map);
        if(!l_isMapValid){
            System.out.println("Map is invalid!");
        }
        else {
            System.out.println("Map is Valid!");
        }
        Logger.log("Command " + p_command.toString() + " Successful! isMapValid:" + l_isMapValid);
    }


}
