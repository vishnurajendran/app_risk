package entity;

/**
 * Interface for read adapter
 */
public interface MapReader {
    /**
     * Abstract method for readMap interface
     * @return RiskMap after loaded
     */
    public RiskMap readMap(String p_mapName);
}
