package mapEditer;

import common.*;
import entity.Continent;
import entity.Country;
import entity.MapLoader;
import entity.RiskMap;
import mapShow.MapViewer;

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

    private final HashMap<String, IMethod> d_cmdToActionMap;
    private RiskMap d_map;
    private String d_filename;
    private boolean d_isMapInitialised = false;

    /**
     * Constructor for mapEditor
     */
    public MapEditor() {
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
     * This function is used to check if a sub-application has quit
     * on its own without a user input, in cases where the sub-application
     * is not able to proceed safely, it can set this function to return true
     * to make the Application layer perform an auto-quit, post a command query.
     *
     * @return true if sub-application has quit, else false
     */
    @Override
    public boolean hasQuit() {
        return false;
    }

    /**
     * This method registers all map editor commands and the methods
     * to be invoked for those commands with an internal command map.
     */
    private void registerMapEditorCommands() {
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
        Logger.log("MapEditor has shutdown.");
    }

    /**
     * This method checks whether the editcontinent command is valid or not.
     *
     * @param p_command command objects as a parameter.
     * @return true if command is valid, false otherwise.
     */
    private boolean isValidEditContinentCommand(Command p_command) {
        ArrayList<CommandAttribute> l_commandAttributes = p_command.getCmdAttributes();
        if (l_commandAttributes.isEmpty()) {
            return false;
        }
        for (CommandAttribute l_cAttribute : l_commandAttributes) {
            if (l_cAttribute.getOption().isEmpty() || !VALIDOPTIONS.contains(l_cAttribute.getOption())) {
                Logger.log("Edit continent: Invalid option");
                return false;
            }
            if (l_cAttribute.getArguments().isEmpty()) {
                Logger.log("Edit continent: No arguments");
                return false;
            }
            if (CMD_OPTION_ADD.equals(l_cAttribute.getOption()) && l_cAttribute.getArguments().size() % 2 != 0) {
                Logger.log("Odd number of arguments for -add continent");
                return false;
            }
        }
        return true;
    }

    /**
     * This method executes add continent command
     *
     * @param p_args    list of args(continent_id, continent_value)
     * @param p_riskMap temporary map object to perform command operation
     * @return true is operation is successful, false otherwise.
     */
    private boolean executeAddContinent(ArrayList<String> p_args, RiskMap p_riskMap) {
        for (int i = 0; i < p_args.size(); i++) {
            int l_continentId = Integer.parseInt(p_args.get(i++));
            int l_continentValue = Integer.parseInt(p_args.get(i));
            if (p_riskMap.hasContinent(l_continentId)) {
                System.out.println("Continent already present in the map!");
                return false;
            } else {
                Continent l_continent = new Continent(l_continentId, String.valueOf(l_continentId), l_continentValue, DEFAULT_CONTINENT_COLOR);
                p_riskMap.addContinent(l_continent);
                Logger.log("Continent added: " + l_continent.toString());
            }
        }
        return true;
    }

    /**
     * This method executes remove continent command
     *
     * @param p_args    list of args(continent_id)
     * @param p_riskMap temporary map object to perform command operation
     * @return true is operation is successful, false otherwise.
     */
    private boolean executeRemoveContinent(ArrayList<String> p_args, RiskMap p_riskMap) {
        for (String l_arg : p_args) {
            int l_continentId = Integer.parseInt(l_arg);
            if (!p_riskMap.hasContinent(l_continentId)) {
                System.out.println("Continent not present in the map!");
                return false;
            } else {
                Continent l_continent = p_riskMap.getContinentById(l_continentId);
                p_riskMap.removeContinent(l_continent);
                Logger.log("Continent removed:" + l_continent.toString());
            }
        }
        return true;
    }

    /**
     * This method executes editcontinent commands
     *
     * @param p_command command object passed down by application
     */
    private void cmdEditContinent(Command p_command) {
        if (!d_isMapInitialised) {
            System.out.println("Map not loaded for editing, use editmap for loading an existing map or creating a new one!");
            return;
        }
        if (isNull(d_map)) {
            Logger.logError("Inconsistent state!, something went wrong!");
            return;
        }

        if (!isValidEditContinentCommand(p_command)) {
            System.out.println("Incorrect command!" + p_command.toString());
            return;
        }

        //execute commands - add and remove
        ArrayList<CommandAttribute> l_CommandAttributes = p_command.getCmdAttributes();
        RiskMap l_tempRiskMap = d_map.clone();
        boolean l_status = true;
        for (CommandAttribute l_cAttribute : l_CommandAttributes) {
            if (CMD_OPTION_ADD.equals(l_cAttribute.getOption())) {
                l_status &= executeAddContinent(l_cAttribute.getArguments(), l_tempRiskMap);
            } else if (CMD_OPTION_REMOVE.equals(l_cAttribute.getOption())) {
                l_status &= executeRemoveContinent(l_cAttribute.getArguments(), l_tempRiskMap);
            }
            if (!l_status) return;
        }

        d_map = l_tempRiskMap;
        Logger.log("Edit continent command executed successfully!");
    }

    /**
     * This method checks whether the editcountry command is valid or not.
     *
     * @param p_command command objects as a parameter.
     * @return true if command is valid, false otherwise.
     */
    private boolean isValidEditCountryCommand(Command p_command) {
        ArrayList<CommandAttribute> l_commandAttributes = p_command.getCmdAttributes();
        if (l_commandAttributes.isEmpty()) {
            return false;
        }
        for (CommandAttribute l_cAttribute : l_commandAttributes) {
            if (l_cAttribute.getOption().isEmpty() || !VALIDOPTIONS.contains(l_cAttribute.getOption())) {
                Logger.log("Edit country: Invalid option");
                return false;
            }
            if (l_cAttribute.getArguments().isEmpty()) {
                Logger.log("Edit country: No arguments");
                return false;
            }
            if (CMD_OPTION_ADD.equals(l_cAttribute.getOption()) && l_cAttribute.getArguments().size() % 2 != 0) {
                Logger.log("Odd number of arguments for -add country");
                return false;
            }
        }
        return true;
    }

    /**
     * This method executes add country command
     *
     * @param p_args    list of args(country_id, continent_id)
     * @param p_riskMap temporary map object to perform command operation
     * @return true is operation is successful, false otherwise.
     */
    private boolean executeAddCountry(ArrayList<String> p_args, RiskMap p_riskMap) {
        for (int i = 0; i < p_args.size(); i++) {
            int l_countryId = Integer.parseInt(p_args.get(i++));
            int l_continentId = Integer.parseInt(p_args.get(i));
            if (!p_riskMap.hasContinent(l_continentId)) {
                System.out.println("Continent not present in the map!");
                return false;
            } else {
                Continent l_continent = p_riskMap.getContinentById(l_continentId);
                if (l_continent.hasCountry(l_countryId)) {
                    System.out.println("Country already present in the continent!");
                    return false;
                } else {
                    Country l_country = new Country(l_countryId, String.valueOf(l_countryId), l_continentId);
                    p_riskMap.addCountry(l_country);
                    Logger.log("Country added:" + l_country.toString());
                }
            }
        }
        return true;
    }

    /**
     * This method executes remove countinent command
     *
     * @param p_args    list of args(country_id)
     * @param p_riskMap temporary map object to perform command operation
     * @return true is operation is successful, false otherwise.
     */
    private boolean executeRemoveCountry(ArrayList<String> p_args, RiskMap p_riskMap) {
        for (String l_arg : p_args) {
            int l_countryId = Integer.parseInt(l_arg);
            if (p_riskMap.hasCountry(l_countryId)) {
                System.out.println("Country not present in the map!");
                return false;
            } else {
                Country l_country = p_riskMap.getCountryById(l_countryId);
                p_riskMap.removeCountry(l_country);
                Logger.log("Country removed:" + l_country.toString());
            }
        }
        return true;
    }

    /**
     * This method executes editcountry commands
     *
     * @param p_command command object passed down by application
     */
    private void cmdEditCountry(Command p_command) {
        if (!d_isMapInitialised) {
            System.out.println("Map not loaded for editing, use editmap for loading an existing map or creating a new one!");
            return;
        }
        if (isNull(d_map)) {
            Logger.logError("Inconsistent state!, something went wrong!");
            return;
        }

        if (!isValidEditCountryCommand(p_command)) {
            System.out.println("Incorrect command!" + p_command.toString());
            return;
        }

        //execute commands - add and remove
        ArrayList<CommandAttribute> l_CommandAttributes = p_command.getCmdAttributes();
        RiskMap l_tempRiskMap = d_map.clone();
        boolean l_status = true;
        for (CommandAttribute l_cAttribute : l_CommandAttributes) {
            if (CMD_OPTION_ADD.equals(l_cAttribute.getOption())) {
                l_status &= executeAddCountry(l_cAttribute.getArguments(), l_tempRiskMap);
            } else if (CMD_OPTION_REMOVE.equals(l_cAttribute.getOption())) {
                l_status &= executeRemoveCountry(l_cAttribute.getArguments(), l_tempRiskMap);
            }
            if (!l_status) return;
        }

        d_map = l_tempRiskMap;
        Logger.log("Edit Country command executed successfully!");
    }

    /**
     * This method checks whether the editneighbor command is valid or not.
     *
     * @param p_command command objects as a parameter.
     * @return true if command is valid, false otherwise.
     */
    private boolean isValidEditNeighbourCommand(Command p_command) {
        ArrayList<CommandAttribute> l_commandAttributes = p_command.getCmdAttributes();
        if (l_commandAttributes.isEmpty()) {
            return false;
        }
        for (CommandAttribute l_cAttribute : l_commandAttributes) {
            if (l_cAttribute.getOption().isEmpty() || !VALIDOPTIONS.contains(l_cAttribute.getOption())) {
                Logger.log("Edit Neighbor: Invalid option");
                return false;
            }
            if (l_cAttribute.getArguments().isEmpty()) {
                Logger.log("Edit Neighbor: No arguments");
                return false;
            }
            if (l_cAttribute.getArguments().size() % 2 != 0) {
                Logger.log("Odd number of arguments for edit neighbor");
                return false;
            }
        }
        return true;
    }

    /**
     * This method executes add neigbor command
     *
     * @param p_args    list of args(country_id, neighborCountryId)
     * @param p_riskMap temporary map object to perform command operation
     * @return true is operation is successful, false otherwise.
     */
    private boolean executeAddNeighbor(ArrayList<String> p_args, RiskMap p_riskMap) {
        for (int i = 0; i < p_args.size(); i++) {
            int l_countryId = Integer.parseInt(p_args.get(i++));
            int l_neighborCountryId = Integer.parseInt(p_args.get(i));
            if (!p_riskMap.hasCountry(l_countryId) || !p_riskMap.hasCountry(l_neighborCountryId)) {
                System.out.println("Country not present in the map!");
                return false;
            } else {
                Country l_country = p_riskMap.getCountryById(l_countryId);
                Country l_neighborCountry = p_riskMap.getCountryById(l_neighborCountryId);
                if (l_country.getBorders().containsKey(l_neighborCountry.getDId())) {
                    System.out.println("Countries are neighbors already!");
                    return false;
                }
                l_country.addBorder(l_neighborCountry);
                Logger.log("Neighbor added:" + l_country.toString());
            }
        }
        return true;
    }

    /**
     * This method executes remove neigbhor command
     *
     * @param p_args    list of args(country_id, neighborCountryId)
     * @param p_riskMap temporary map object to perform command operation
     * @return true is operation is successful, false otherwise.
     */
    private boolean executeRemoveNeighbor(ArrayList<String> p_args, RiskMap p_riskMap) {
        for (int i = 0; i < p_args.size(); i++) {
            int l_countryId = Integer.parseInt(p_args.get(i++));
            int l_neighborCountryId = Integer.parseInt(p_args.get(i));
            if (!p_riskMap.hasCountry(l_countryId) || !p_riskMap.hasCountry(l_neighborCountryId)) {
                System.out.println("Country not present in the map!");
                return false;
            } else {
                Country l_country = p_riskMap.getCountryById(l_countryId);
                Country l_neighborCountry = p_riskMap.getCountryById(l_neighborCountryId);

                if (!l_country.getBorders().containsKey(l_neighborCountry.getDId())) {
                    System.out.println("Countries are not neighbors!");
                    return false;
                }
                l_country.removeBorder(l_neighborCountry);
            }
        }
        return true;
    }


    /**
     * This method executes editneighbor commands
     *
     * @param p_command command object passed down by application
     */
    private void cmdEditNeighbor(Command p_command) {
        if (!d_isMapInitialised) {
            System.out.println("Map not loaded for editing, use editmap for loading an existing map or creating a new one!");
            return;
        }
        if (isNull(d_map)) {
            Logger.logError("Inconsistent state!, something went wrong!");
            return;
        }

        if (!isValidEditNeighbourCommand(p_command)) {
            System.out.println("Incorrect command!" + p_command.toString());
            return;
        }

        //execute commands - add and remove
        ArrayList<CommandAttribute> l_CommandAttributes = p_command.getCmdAttributes();
        RiskMap l_tempRiskMap = d_map.clone();
        boolean l_status = true;
        for (CommandAttribute l_cAttribute : l_CommandAttributes) {
            if (CMD_OPTION_ADD.equals(l_cAttribute.getOption())) {
                l_status &= executeAddNeighbor(l_cAttribute.getArguments(), l_tempRiskMap);
            } else if (CMD_OPTION_REMOVE.equals(l_cAttribute.getOption())) {
                l_status &= executeRemoveNeighbor(l_cAttribute.getArguments(), l_tempRiskMap);
            }
            if (!l_status) return;
        }

        d_map = l_tempRiskMap;
        Logger.log("Edit Neighbor command executed successfully!");

    }

    private void cmdShowMap(Command p_command) {
        //call to display map.
        MapViewer.showMap();
    }

    private void cmdSaveMap(Command p_command) {
        //write the map object to file.
        if (p_command.getCmdAttributes().isEmpty() || p_command.getCmdAttributes().get(0).getArguments().isEmpty()) {
            System.out.println("File name missing to save map.");
            return;
        }

        if (!p_command.getCmdAttributes().get(0).getOption().isEmpty()) {
            System.out.println("This command does not support options.");
            return;
        }

        if (!MapValidator.validateMap(d_map)) {
            System.out.println("Error, this map cannot be saved, Validation failed");
        }

        String fileName = p_command.getCmdAttributes().get(0).getArguments().get(0);
        try {
            File file = new File(fileName);
            MapSave.saveMapFile(d_map, file);
            System.out.println("Map successfully saved");
        } catch (Exception ex) {
            System.out.println("Map save was unsuccessful");
        }
    }

    /**
     * Creates an empty map file
     *
     * @param p_name name of the file to be created
     * @return true if file creation is successful, false otherwise.
     */
    private boolean createNewMapFile(String p_name) {
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
        if (p_command.getCmdAttributes().isEmpty()) {
            //creating new map file for editmap
            Logger.log("File name not found!, Creating a map from scratch");
            if (createNewMapFile(NEW_MAP_FILE_NAME)) {
                d_filename = NEW_MAP_FILE_NAME;
                d_map = new RiskMap(NEW_MAP_FILE_NAME);
                d_isMapInitialised = true;
            }
            return;
        } else if (!p_command.getCmdAttributes().get(0).getOption().isEmpty()) {
            System.out.println("Incorrect command, invalid option found" + p_command.toString());
            return;
        } else if (!p_command.getCmdAttributes().get(0).getArguments().isEmpty()) {
            l_filename = p_command.getCmdAttributes().get(0).getArguments().get(0);
        } else {
            System.out.println("Invalid arguments!" + p_command.toString());
            return;
        }

        //load the map from existing file and validate before proceeding further
        MapLoader l_mapLoader = new MapLoader(l_filename);
        RiskMap l_riskMap = l_mapLoader.getMap();
        if (isNull(l_riskMap) || !MapValidator.validateMap(l_riskMap)) {
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
    private void cmdValidateMap(Command p_command) {
        if (!d_isMapInitialised || isNull(d_map)) {
            System.out.println("No Map loaded in the mapEditor!");
            return;
        }
        boolean l_isMapValid = MapValidator.validateMap(d_map);
        if (!l_isMapValid) {
            System.out.println("Map is invalid!");
        } else {
            System.out.println("Map is Valid!");
        }
        Logger.log("Command " + p_command.toString() + " Successful! isMapValid:" + l_isMapValid);
    }


}
