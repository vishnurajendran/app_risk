package entity;

public class MapLoaderAdapter {
    private static MapReader d_mapReader;

    public MapLoaderAdapter(MapReader p_mapReader){
        d_mapReader =p_mapReader;
    }


    public RiskMap loadMap(String p_mapName){
        return d_mapReader.readMap(p_mapName);

    }
}
