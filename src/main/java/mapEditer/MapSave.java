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
     */
    public static void saveMapFile(RiskMap p_map, String p_file, boolean isConquest) {
        MapWriterAdapter adapter;


        if(isConquest){
            adapter=new MapWriterAdapter(new ConquestMapWriter());
        }else {
            adapter=new MapWriterAdapter(new DominationMapWriter());
        }
        adapter.saveFile(p_map,p_file);
    }





}