package game;


import mapShow.MapShowConstants;

import java.sql.Struct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * This command class will contain all the possible commands that
 * user can deploy during game phase.
 *
 * @author Soham
 */
public class GameCommands {

    /**
     * Command to load a map
     */
    public static final String CMD_LOAD_MAP = "loadmap";

    /**
     * Command to show map.
     */
    public static final String CMD_SHOWMAP = MapShowConstants.CMD_MAPSHOW;

    /**
     * Command to add/remove players
     */
    public static final String CMD_GAME_PLAYER = "gameplayer";
    /**
     * Option to add players in CMD_LOAD_MAP
     */
    public static final String CMD_GAME_PLAYER_OPTION_ADD = "add";
    /**
     * Option to remove players in CMD_LOAD_MAP
     */
    public static final String CMD_GAME_PLAYER_OPTION_REMOVE = "remove";
    /**
     * Command to assign countries and start the game loop
     */
    public static final String CMD_ASSIGN_COUNTRIES_TO_PLAYER = "assigncountries";
    /**
     * Command for deploy order
     */
    public static final String CMD_DEPLOY_COUNTRIES = "deploy";
    /**
     * Command for advancing armies
     */
    public static final String CMD_ADVANCE_ARMIES = "advance";
    /**
     * Valid command set
     */
    public static final Set<String> CHECK_VALID_COMMANDS_FOR_ISSUEORDER = new HashSet<>(Arrays.asList(CMD_GAME_PLAYER, CMD_ASSIGN_COUNTRIES_TO_PLAYER, CMD_SHOWMAP, CMD_DEPLOY_COUNTRIES));
    /**
     * Error messages for displaying to user
     */
    public static final ArrayList<String> DEPLOYERRORMESSAGE = new ArrayList<>(Arrays.asList(
            "ERROR: The given deploy command isn't valid, please try again",
            "ERROR: The player doesn't own this country, please try another one",
            "ERROR: The armies requested to deploy are more than what the player has"
    ));

    public static final ArrayList<String> ADVANCE_ERROR_MESSAGES = new ArrayList<>(Arrays.asList(
            "ERROR: The given advance command isn't valid, please try again",
            "ERROR: The player doesn't own this country",
            "ERROR: The armies requested to advance are more than what the player has",
            "ERROR: The given countries are not adjacent"
    ));

    public static final ArrayList<String> AIRLIFT_ERROR_MESSAGES = new ArrayList<>(Arrays.asList(
            "ERROR: The given airlift command isn't valid please try again",
            "ERROR: The player doesn't own the Airlift Card please try again",
            "ERROR: The player doesnt own this country",
            "ERROR: The armies requested to airlifted are more than what the player owns",
            "ERROR: The target country is not owned by the player"
    ));

}
