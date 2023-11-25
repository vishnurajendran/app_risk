package entity;

import common.Logging.Logger;

import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

import static java.util.Objects.isNull;

/**
 * Map reader for domination map files
 */
public class DominationMapReader implements MapReader{

    /**
     * Read domination map
     * @param p_mapName name of file/ path of the file
     * @return Loaded map or null
     */
    @Override
    public RiskMap readMap(String p_mapName) {
        Scanner l_scanner=null;
        RiskMap l_riskMap;

        try {
            File l_mapFile = new File(p_mapName);
            l_scanner = new Scanner(l_mapFile);
            l_riskMap=new RiskMap();
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
                    l_riskMap.setName(l_line.substring(l_line.indexOf(" ") + 1));
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
                        l_riskMap.addContinent(l_continent);
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
                        if (!isNull(l_riskMap.getCountryById(l_country.getDId()))) {
                            throw new Exception("Duplicated found");
                        }
                        l_riskMap.addCountry(l_country);
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
                        l_riskMap.addBorders(Integer.parseInt(l_linePieces[0]), Arrays.copyOfRange(l_linePieces, 1, l_linePieces.length));
                        if (l_scanner.hasNextLine()) {
                            l_line = l_scanner.nextLine();
                        } else {
                            break;
                        }
                    }

                }
            }


            l_scanner.close();
            return l_riskMap;
        } catch (Exception e) {
            Logger.logError(e.getMessage());
            if(!isNull(l_scanner)){
                l_scanner.close();
            }

            return null;
        }
    }
}
