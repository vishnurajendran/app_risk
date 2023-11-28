package entity;

import common.Logging.Logger;
import game.GameEngine;

import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

import static java.util.Objects.isNull;

public class MapLoader {
    private static RiskMap d_riskMap=null;

    /**
     * Default constructor that initialize an empty map.
     */
    public MapLoader() {
        cleanUp();
    }

    /**
     * Constructor that initialize an empty map with the given name.
     *
     * @param p_mapName
     */
    public MapLoader(String p_mapName) {
        cleanUp();
        loadMap(p_mapName);
    }

    /**
     * Load a map from a file with the given name.
     *
     * @param p_mapName The file name of the map.
     * @return A boolean of whether the load is succeeded.
     */
    public static boolean loadMap(String p_mapName) {
        MapLoaderAdapter adapter;
        if(isConquestMap(p_mapName)){
            adapter=new MapLoaderAdapter(new ConquestMapReader());
        }else{
            adapter=new MapLoaderAdapter(new DominationMapReader());
        }
        d_riskMap=adapter.loadMap(p_mapName);
        if(isNull(d_riskMap)){
            return false;
        }
        return true;
    }

    /**
     * Getter for the map.
     *
     * @return The map that is loaded.
     */
    public static RiskMap getMap() {
        return d_riskMap;
    }

    /**
     * Getter for the map.
     * @param p_mapName The file name of the map.
     * @return The map that is loaded.
     */
    public static RiskMap getMap(String p_mapName) {
        loadMap(p_mapName);
        return d_riskMap;
    }

    /**
     * Clean up the map in map loader
     * Point it to null for now
     */
    public static void cleanUp(){
        d_riskMap=null;
    }

    /**
     * Local method for pre-checking which format is there
     * If "[map]" is found at the beginning return true(which means it is conquest map)
     * @param p_mapName The file name of the map.
     * @return Whether it is ConquestMap
     */
    private static boolean isConquestMap(String p_mapName){
        Scanner l_scanner=null;
        try{
            File l_mapFile = new File(p_mapName);
            l_scanner = new Scanner(l_mapFile);
            String l_line = l_scanner.nextLine();
            if(l_line.trim().equals("[Map]")){
                return true;
            }else{
                return false;
            }
        }catch (Exception e){
            Logger.logError(e.getMessage());
            return false;
        }finally {
            if(!isNull(l_scanner)){
                l_scanner.close();
            }
        }
    }

}
