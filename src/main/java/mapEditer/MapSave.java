package mapEditer;


import entity.RiskMap;


import java.io.File;


/**
 * The main class to save the data in map file when the user creates a map.
 *
 * @author Rachit
 */

public class MapSave {

    /**
     * The method to write the map details to map file.
     *
     * @param p_map  object of the map which is being processed.
     * @param p_file object of file to store map data.
     * @param p_isConquest boolean to decide between writers.
     */
    public static void saveMapFile(RiskMap p_map, String p_file, boolean p_isConquest) {
        MapWriterAdapter adapter;


        if(p_isConquest){
            adapter=new MapWriterAdapter(new ConquestMapWriter());
        }else {
            adapter=new MapWriterAdapter(new DominationMapWriter());
        }
        adapter.saveFile(p_map,p_file);
    }





}