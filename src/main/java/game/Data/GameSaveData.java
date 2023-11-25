package game.Data;
import entity.MapSaveData;
import entity.PlayerSaveData;
import entity.RiskMap;
import game.States.GameStates;

/**
 * this class holds the game save data.
 * and also provides a save and loader functions.
 * @author vishnurajendran
 */
public class GameSaveData {

    private GameStates d_gameState;
    private PlayerSaveData d_playerSaveData;
    private MapSaveData d_mapData;

    /**
     * default constructor
     */
    private GameSaveData(){
    }

    /**
     * parameterised constructor, initialises this class with
     * all needed data.
     * @param p_playerSaveData the player data to save.
     * @param p_mapData the map_data to save.
     */
    public GameSaveData(GameStates l_currState, PlayerSaveData p_playerSaveData, MapSaveData p_mapData){
        this.d_gameState = l_currState;
        this.d_mapData = p_mapData;
        this.d_playerSaveData = p_playerSaveData;
    }

    /**
     * get player data
     * @return list of players saved
     */
    public PlayerSaveData getPlayerSaveData() {
        return d_playerSaveData;
    }

    /**
     * get map data
     * @return instance of RiskMap saved.
     */
    public MapSaveData getMapData() {
        return d_mapData;
    }

    /**
     * get the saved game state.
     * @return saved state for game.
     */
    public GameStates getGameState() {
        return d_gameState;
    }
}
