package mapEditer;

import entity.Country;
import entity.Map;

import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

public class MapLoader {
    Map d_map;

    /**
     * Default constructor that initialize an empty map.
     */
    public MapLoader() {
        d_map = new Map();
    }

    /**
     * Constructor that initialize a empty map with the given name.
     * @param p_mapName
     */
    public MapLoader(String p_mapName) {
        d_map = new Map();
        loadMap(p_mapName);
    }

    /**
     * Load a map from a file with the given name.
     * @param p_mapName The file name of the map.
     * @return A boolean of whether the load is succeeded.
     */
    public boolean loadMap(String p_mapName) {
        try {
            File l_mapFile = new File(p_mapName);
            Scanner l_scanner = new Scanner(l_mapFile);
            while (l_scanner.hasNextLine()) {
                String l_line = l_scanner.nextLine();
                if (l_line.isEmpty()) {
                    continue;
                }
                String[] l_linePieces = l_line.split(" ");
                if (l_linePieces[0].equals(";")) {
                    continue;
                }
                if (l_linePieces[0].equals("name")) {
                    d_map.setName(l_line.substring(l_line.indexOf(" ") + 1));
                }
                if (l_linePieces[0].equals("[countries]")) {
                    l_line = l_scanner.nextLine();
                    while (!l_line.isEmpty()) {
                        l_linePieces = l_line.split(" ");
                        d_map.addCountry(new Country(Integer.parseInt(l_linePieces[0]), l_linePieces[1],
                                Integer.parseInt(l_linePieces[2]), Integer.parseInt(l_linePieces[3]), Integer.parseInt(l_linePieces[4])));
                        if(l_scanner.hasNextLine()){
                            l_line = l_scanner.nextLine();
                        }else{
                            break;
                        }
                    }

                }
                if (l_linePieces[0].equals("[borders]")) {
                    l_line = l_scanner.nextLine();
                    while (!l_line.isEmpty()) {
                        l_linePieces = l_line.split(" ");
                        d_map.addBorders(Integer.parseInt(l_linePieces[0]), Arrays.copyOfRange(l_linePieces,1,l_linePieces.length));
                        if(l_scanner.hasNextLine()){
                            l_line = l_scanner.nextLine();
                        }else{
                            break;
                        }
                    }

                }
            }


            l_scanner.close();
            return true;
        } catch (Exception e) {

            return false;
        }
    }

    /**
     * Getter for the map.
     * @return The map that is loaded.
     */
    public Map getMap() {
        return d_map;
    }


}
