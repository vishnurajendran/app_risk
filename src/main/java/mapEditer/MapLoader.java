package mapEditer;

import entity.Continent;
import entity.Country;
import entity.RiskMap;

import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

public class MapLoader {
    RiskMap d_Risk_map;

    /**
     * Default constructor that initialize an empty map.
     */
    public MapLoader() {
        d_Risk_map = new RiskMap();
    }

    /**
     * Constructor that initialize an empty map with the given name.
     *
     * @param p_mapName
     */
    public MapLoader(String p_mapName) {
        d_Risk_map = new RiskMap();
        loadMap(p_mapName);
    }

    /**
     * Load a map from a file with the given name.
     *
     * @param p_mapName The file name of the map.
     * @return A boolean of whether the load is succeeded.
     */
    public boolean loadMap(String p_mapName) {
        try {
            File l_mapFile = new File(p_mapName);
            Scanner l_scanner = new Scanner(l_mapFile);
            while (l_scanner.hasNextLine()) {
                String l_line = l_scanner.nextLine();
                // skip empty lines
                if (l_line.isEmpty()) {
                    continue;
                }
                String[] l_linePieces = l_line.split(" ");
                //skip comments
                if (l_linePieces[0].equals(";")) {
                    continue;
                }

                // load the name
                if (l_linePieces[0].equals("name")) {
                    d_Risk_map.setName(l_line.substring(l_line.indexOf(" ") + 1));
                }
                // load continents
                if (l_linePieces[0].equals("[continents]")) {
                    int l_continentID = 1;
                    l_line = l_scanner.nextLine();
                    while (!l_line.isEmpty()) {
                        l_linePieces = l_line.split(" ");
                        d_Risk_map.addContinent(new Continent(l_continentID, l_linePieces[0], Integer.parseInt(l_linePieces[1]), l_linePieces[2]));
                        l_continentID++;
                        if (l_scanner.hasNextLine()) {
                            l_line = l_scanner.nextLine();
                        } else {
                            break;
                        }
                    }
                }

                // load countries
                if (l_linePieces[0].equals("[countries]")) {
                    l_line = l_scanner.nextLine();
                    while (!l_line.isEmpty()) {
                        l_linePieces = l_line.split(" ");
                        d_Risk_map.addCountry(new Country(Integer.parseInt(l_linePieces[0]), l_linePieces[1],
                                Integer.parseInt(l_linePieces[2]), Integer.parseInt(l_linePieces[3]), Integer.parseInt(l_linePieces[4])));
                        if (l_scanner.hasNextLine()) {
                            l_line = l_scanner.nextLine();
                        } else {
                            break;
                        }
                    }

                }
                // load borders
                if (l_linePieces[0].equals("[borders]")) {
                    l_line = l_scanner.nextLine();
                    while (!l_line.isEmpty()) {
                        l_linePieces = l_line.split(" ");
                        d_Risk_map.addBorders(Integer.parseInt(l_linePieces[0]), Arrays.copyOfRange(l_linePieces, 1, l_linePieces.length));
                        if (l_scanner.hasNextLine()) {
                            l_line = l_scanner.nextLine();
                        } else {
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
     *
     * @return The map that is loaded.
     */
    public RiskMap getMap() {
        return d_Risk_map;
    }


}
