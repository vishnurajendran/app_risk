package game;


import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class GameCommands {
    public static final String CMD_LOAD_MAP = "loadmap";
    public static final String CMD_GAME_PLAYER = "gameplayer";
    public static final String CMD_ASSIGN_COUNTRIES_TO_PLAYER = "assigncountries";
    public static final Set<String> CHECKVALIDCOMMANDS = new HashSet<>(Arrays.asList(CMD_LOAD_MAP, CMD_GAME_PLAYER, CMD_ASSIGN_COUNTRIES_TO_PLAYER));

}
