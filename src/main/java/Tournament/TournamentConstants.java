package Tournament;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * This class contains all constants used by Tournament class.
 *
 * @author TaranjeetKaur
 */
public class TournamentConstants {

    /**
     * Command to start tournament
     */
    public static final String CMD_START_TOURNAMENT = "tournament";

    /**
     * Valid options for tournament command
     */
    public static final String CMD_OPTION_MAP = "M";
    public static final String CMD_OPTION_PLAYERSTRATEGY = "P";
    public static final String CMD_OPTION_GAMES = "G";
    public static final String CMD_OPTION_MAXIMUM_TURNS = "D";

    /**
     * Player strategy constants
     */
    public static final String STRATEGY_AGGRESIVE = "Aggressive";
    public static final String STRATEGY_BENEVOLENT = "Benevolent";
    public static final String STRATEGY_RANDOM = "Random";
    public static final String STRATEGY_CHEATER = "Cheater";

    /**
     * tournament command options
     */
    public static final Set<String> TOURNAMENT_VALIDOPTIONS = new HashSet<>(Arrays.asList(CMD_OPTION_MAP, CMD_OPTION_GAMES, CMD_OPTION_PLAYERSTRATEGY, CMD_OPTION_MAXIMUM_TURNS));

    public static final Set<String> TOURNAMENT_VALIDSTRATEGIES = new HashSet<>(Arrays.asList(STRATEGY_AGGRESIVE, STRATEGY_BENEVOLENT, STRATEGY_RANDOM, STRATEGY_CHEATER));


    /**
     * Error messages for tournament commands
     */
    public static final String CMD_INVALID_ARGUMENTS = "Error!, Invalid arguments to tournament command";

    public static final String CMD_TOO_MANY_ARGUMENTS = "Error!, There are more arguments than allowed for the command";


    public static final String CMD_TOO_FEW_ARGUMENTS = "Error!, There are less arguments than required for the command";

    public static final String CMD_INVALID_MAP = "Error!, Map used for the tournament is invalid, please try again with valid map files";

    public static final String CMD_INVALID_STRATEGY = "Error!, Strategy used for the tournament is invalid, please try again with valid strategy";
}
