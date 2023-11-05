package mapEditer;

import common.Command;
import common.CommandAttribute;
import common.Logging.Logger;
import entity.Continent;
import entity.Country;
import entity.MapLoader;
import entity.RiskMap;
import mapShow.MapViewer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static java.util.Objects.isNull;
import static mapEditer.MapEditorCommands.*;

/**
 * This class implements the operations in "EDITMODE" Phase of the mapEditor
 * MapEditor is the context class for this phase.
 *
 * @author TaranjeetKaur
 */
public class InEditPhase extends Phase{
    public InEditPhase(MapEditor p_mapEditor) {
        super(p_mapEditor);
    }

    /**
     * Command validation is not an operation for InEdit Phase
     * @param p_command
     * @return  false
     */
    public boolean isValidCommand(Command p_command) {
        InvalidCommandMessage();
        return false;
    }

    /**
     * Executes the command savemap
     *
     * @param p_command The command objects passed down by the application
     * @return true if file operation is successful, false otherwise.
     */
    private boolean executeCmdSaveMap(Command p_command) {
        //write the map object to file.
        if (!MapValidator.validateMap(d_mapEditor.d_map)) {
            System.out.println("Error, this map cannot be saved, Validation failed");
            return false;
        }

        String fileName = p_command.getCmdAttributes().get(0).getArguments().get(0);
        try {
            File file = new File(fileName);
            MapSave.saveMapFile(d_mapEditor.d_map, file);
            return true;
        } catch (Exception ex) {
            return false;
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
     * Executes the command editmap
     *
     * @param p_command The command objects passed down by the application
     * @return  rue is operation is successful, false otherwise.
     */
    private boolean executeCmdEditMap(Command p_command) {
        String l_filename = "";
        if (p_command.getCmdAttributes().isEmpty()) {
            //creating new map file for editmap
            Logger.log("File name not found!, Creating a map from scratch");
            if (createNewMapFile(NEW_MAP_FILE_NAME)) {
                d_mapEditor.d_filename = NEW_MAP_FILE_NAME;
                d_mapEditor.d_map = new RiskMap(NEW_MAP_FILE_NAME);
                d_mapEditor.d_isMapInitialised = true;
                Logger.log("Command " + p_command.toString() + " Successful!");
            }
            return true;
        } else if (!p_command.getCmdAttributes().get(0).getOption().isEmpty()) {
            System.out.println("Incorrect command, invalid option found" + p_command.toString());
            d_mapEditor.quitMapEditor();
            return false;
        } else if (!p_command.getCmdAttributes().get(0).getArguments().isEmpty()) {
            l_filename = p_command.getCmdAttributes().get(0).getArguments().get(0);
        } else {
            System.out.println("Invalid arguments!" + p_command.toString());
            d_mapEditor.quitMapEditor();
            return false;
        }

        //load the map from existing file and validate before proceeding further
        MapLoader l_mapLoader = new MapLoader(l_filename);
        RiskMap l_riskMap = l_mapLoader.getMap();
        if (isNull(l_riskMap) || !MapValidator.validateMap(l_riskMap)) {
            System.out.println("Invalid map!, load another file or start from scratch!");
            d_mapEditor.quitMapEditor();
            return false;
        }

        d_mapEditor.d_isMapInitialised = true;
        d_mapEditor.d_map = l_riskMap;
        d_mapEditor.d_filename = l_filename;
        Logger.log("Command " + p_command.toString() + " Successful!");
        return true;
    }


    /**
     * Executes the command showmap
     *
     * @param p_command The command objects passed down by the application
     * @return  status true always.
     */
    private boolean executeCmdShowMap(Command p_command) {
        MapViewer.showMap(d_mapEditor.d_map);
        return true;
    }

    /**
     * Implementation of command-validatemap
     * Validates a map and prints the result to the console ,if the map is loaded in the mapEditor
     *
     * @param p_command Command object passed down from application.
     * @return  status true always.
     */
    private boolean executeCmdValidateMap(Command p_command) {
        boolean l_isMapValid = MapValidator.validateMap(d_mapEditor.d_map);
        if (!l_isMapValid) {
            System.out.println("Map is invalid!");
        } else {
            System.out.println("Map is Valid!");
        }
        Logger.log("Command " + p_command.toString() + " Successful! isMapValid:" + l_isMapValid);
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
     * @return  status true if execution successful, false otherwise.
     */
    private boolean executeCmdEditNeighbor(Command p_command) {
        //execute commands - add and remove
        ArrayList<CommandAttribute> l_CommandAttributes = p_command.getCmdAttributes();
        RiskMap l_tempRiskMap = d_mapEditor.d_map.clone();
        boolean l_status = true;
        for (CommandAttribute l_cAttribute : l_CommandAttributes) {
            if (CMD_OPTION_ADD.equals(l_cAttribute.getOption())) {
                l_status &= executeAddNeighbor(l_cAttribute.getArguments(), l_tempRiskMap);
            } else if (CMD_OPTION_REMOVE.equals(l_cAttribute.getOption())) {
                l_status &= executeRemoveNeighbor(l_cAttribute.getArguments(), l_tempRiskMap);
            }
            if (!l_status)  return false;
        }

        d_mapEditor.d_map = l_tempRiskMap;
        Logger.log("Edit Neighbor command executed successfully!");
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
            } else if (p_riskMap.hasCountry(l_countryId)) {
                System.out.println("Country already present in the map!");
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
     * This method executes remove continent command
     *
     * @param p_args    list of args(country_id)
     * @param p_riskMap temporary map object to perform command operation
     * @return true is operation is successful, false otherwise.
     */
    private boolean executeRemoveCountry(ArrayList<String> p_args, RiskMap p_riskMap) {
        for (String l_arg : p_args) {
            int l_countryId = Integer.parseInt(l_arg);
            if (!p_riskMap.hasCountry(l_countryId)) {
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
     * @return  status true if execution successful, false otherwise.
     */
    private boolean executeCmdEditCountry(Command p_command) {
        //execute commands - add and remove
        ArrayList<CommandAttribute> l_CommandAttributes = p_command.getCmdAttributes();
        RiskMap l_tempRiskMap = d_mapEditor.d_map.clone();
        boolean l_status = true;
        for (CommandAttribute l_cAttribute : l_CommandAttributes) {
            if (CMD_OPTION_ADD.equals(l_cAttribute.getOption())) {
                l_status &= executeAddCountry(l_cAttribute.getArguments(), l_tempRiskMap);
            } else if (CMD_OPTION_REMOVE.equals(l_cAttribute.getOption())) {
                l_status &= executeRemoveCountry(l_cAttribute.getArguments(), l_tempRiskMap);
            }
            if (!l_status) return false;
        }

        d_mapEditor.d_map = l_tempRiskMap;
        Logger.log("Edit Country command executed successfully!");
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
     * @return  status true if execution successful, false otherwise.
     */
    private boolean executeCmdEditContinent(Command p_command) {
        //execute commands - add and remove
        ArrayList<CommandAttribute> l_CommandAttributes = p_command.getCmdAttributes();
        RiskMap l_tempRiskMap = d_mapEditor.d_map.clone();
        boolean l_status = true;
        for (CommandAttribute l_cAttribute : l_CommandAttributes) {
            if (CMD_OPTION_ADD.equals(l_cAttribute.getOption())) {
                l_status &= executeAddContinent(l_cAttribute.getArguments(), l_tempRiskMap);
            } else if (CMD_OPTION_REMOVE.equals(l_cAttribute.getOption())) {
                l_status &= executeRemoveContinent(l_cAttribute.getArguments(), l_tempRiskMap);
            }
            if (!l_status) return false;
        }

        d_mapEditor.d_map = l_tempRiskMap;
        Logger.log("Edit continent command executed successfully!");
        return true;
    }

    /**
     * This method executes all the valid commands for this phase
     *
     * @param p_command The command object passed by MapEditor
     * @return  status true if execution successful, false otherwise.
     */
    public boolean executeCommand(Command p_command){
        String l_command = p_command.getCmdName();
        boolean l_executionSuccessful = false;
        switch (l_command){
            case CMD_SAVE_MAP : {
                l_executionSuccessful = executeCmdSaveMap(p_command);
                break;
            }
            case CMD_EDIT_MAP : {
                l_executionSuccessful = executeCmdEditMap(p_command);
                break;
            }
            case CMD_SHOW_MAP: {
                l_executionSuccessful = executeCmdShowMap(p_command);
                break;
            }
            case CMD_VALIDATE_MAP: {
                l_executionSuccessful = executeCmdValidateMap(p_command);
                break;
            }
            case CMD_EDIT_COUNTRY: {
                l_executionSuccessful = executeCmdEditCountry(p_command);
                break;
            }
            case CMD_EDIT_CONTINENT: {
                l_executionSuccessful = executeCmdEditContinent(p_command);
                break;
            }
            case CMD_EDIT_NEIGHBOR: {
                l_executionSuccessful = executeCmdEditNeighbor(p_command);
                break;
            }
            default: {
                Logger.log("Inconsistent state! invalid command");
                break;
            }
        }

        return l_executionSuccessful;
    }

    /**
     * Command postExceute is not an operation for InEdit Phase
     *
     * @return  false
     */
    public boolean postExecute(){
        InvalidCommandMessage();
        return false;
    }
}
