package mapEditer;

import entity.RiskMap;

import java.io.File;

/**
 * Adapter class for map writer
 */
public class MapWriterAdapter {
    private static MapWriter d_mapWriter;

    /**
     * Constructor for adapter
     * @param p_mapWriter The map writer needed
     */
    public MapWriterAdapter(MapWriter p_mapWriter){
        d_mapWriter=p_mapWriter;
    }

    /**
     * Save the map using map writer and adapt to different file types
     * @param p_map RiskMap needed to be saved
     * @param p_file File needed to write
     */
    public void saveFile(RiskMap p_map, String p_file){
        d_mapWriter.writeMap(p_map,p_file);
    }

}
