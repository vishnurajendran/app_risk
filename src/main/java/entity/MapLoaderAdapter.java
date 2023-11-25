package entity;

/**
 * Adapter class for map loader
 */
public class MapLoaderAdapter {
    private static MapReader d_mapReader;

    /**
     * Constructor for adapter, MapReader should be one type of map reader
     * @param p_mapReader The map reader needed
     */
    public MapLoaderAdapter(MapReader p_mapReader){
        d_mapReader =p_mapReader;
    }


    /**
     * Load the map using the map reader and adapt to riskMap
     * @param p_mapName
     * @return
     */
    public RiskMap loadMap(String p_mapName){
        return d_mapReader.readMap(p_mapName);

    }
}
