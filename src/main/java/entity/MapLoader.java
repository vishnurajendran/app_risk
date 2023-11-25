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
        Scanner l_scanner=null;
        try {
            File l_mapFile = new File(p_mapName);
            l_scanner = new Scanner(l_mapFile);
            d_riskMap=new RiskMap();
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
                    d_riskMap.setName(l_line.substring(l_line.indexOf(" ") + 1));
                }
                // load continents
                if (l_linePieces[0].equals("[continents]")) {
                    int l_continentID = 1;
                    l_line = l_scanner.nextLine();
                    while (!l_line.isEmpty()) {
                        l_linePieces = l_line.split(" ");
                        Continent l_continent;
                        switch (l_linePieces.length) {
                            case 2:
                                l_continent = new Continent(l_continentID, l_linePieces[0], Integer.parseInt(l_linePieces[1]));
                                break;
                            case 3:
                                l_continent = new Continent(l_continentID, l_linePieces[0], Integer.parseInt(l_linePieces[1]), l_linePieces[2]);
                                break;
                            default:
                                throw new Exception("InCorrect amount of arguments");

                        }
                        d_riskMap.addContinent(l_continent);
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
                        Country l_country;
                        switch (l_linePieces.length) {
                            case 3:
                                l_country = new Country(Integer.parseInt(l_linePieces[0]), l_linePieces[1],
                                        Integer.parseInt(l_linePieces[2]));
                                break;
                            case 5:
                                l_country = new Country(Integer.parseInt(l_linePieces[0]), l_linePieces[1],
                                        Integer.parseInt(l_linePieces[2]), Integer.parseInt(l_linePieces[3]), Integer.parseInt(l_linePieces[4]));
                                break;
                            default:
                                throw new Exception("InCorrect amount of arguments");

                        }

                        //throw error if duplicate found
                        if (!isNull(d_riskMap.getCountryById(l_country.getDId()))) {
                            throw new Exception("Duplicated found");
                        }
                        d_riskMap.addCountry(l_country);
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
                        d_riskMap.addBorders(Integer.parseInt(l_linePieces[0]), Arrays.copyOfRange(l_linePieces, 1, l_linePieces.length));
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
            Logger.logError(e.getMessage());
            if(!isNull(l_scanner)){
                l_scanner.close();
            }
            cleanUp();
            return false;
        }
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

}
