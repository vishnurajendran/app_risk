package game;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class GameCommands {
    public static final String CMD_LOAD_MAP = "loadmap";
    public static final String CMD_GAME_PLAYER = "gameplayer";
    public static final String CMD_GAME_PLAYER_OPTION_ADD = "add";
    public static final String CMD_GAME_PLAYER_OPTION_REMOVE = "remove";
    public static final String CMD_ASSIGN_COUNTRIES_TO_PLAYER = "assigncountries";
    public static final String CMD_DEPLOY_COUNTRIES = "deploy";
    public static final Set<String> CHECKVALIDCOMMANDSFORINITIAL = new HashSet<>(Arrays.asList(CMD_LOAD_MAP, CMD_GAME_PLAYER, CMD_ASSIGN_COUNTRIES_TO_PLAYER));
    public static final ArrayList<String> DEPLOYERRORMESSAGE = new ArrayList<>(Arrays.asList(
            "The given deploy command isn't valid, please try again",
            "The player doesn't own this country, please try another one",
            "The armies requested to deploy are more than what the player has"));

}
