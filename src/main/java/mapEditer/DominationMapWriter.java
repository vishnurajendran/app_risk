package mapEditer;

import common.FileIO.FileIO;
import entity.Continent;
import entity.Country;
import entity.RiskMap;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Domination map writer that write in domination map format
 */
public class DominationMapWriter implements MapWriter {

    /**
     * Write using domination file format
     *
     * @param p_map  Risk map object
     * @param p_file The file needed to write
     */
    @Override
    public void writeMap(RiskMap p_map, String p_file) {


        if (p_map == null) {
            System.out.println("Map object is NULL! ");
        }

        String l_content = parseMapAndReturnString(p_map);
        FileIO.writeTextFile(p_file, l_content);

    }

    /**
     * The method to process the map by calling three different methods and to make a string to be written in map file.
     *
     * @param p_Map object of the map which is processed
     * @return String to be written in the map file
     */
    private static String parseMapAndReturnString(RiskMap p_Map) {

        return "\nname " + p_Map.getName() + "\n\n" + String.valueOf(processContinent(p_Map)) +
                processCountries(p_Map) +
                processAdjacentCountries(p_Map);
    }

    /**
     * The method to process continents.
     *
     * @param p_Map object of the map which is being processed.
     * @return string that contains details of the continents that will eventually be written in the map file.
     */
    private static StringBuilder processContinent(RiskMap p_Map) {
        StringBuilder l_continentData = new StringBuilder();
        l_continentData.append("[continents]");
        l_continentData.append("\n");
        for (Continent l_continent : p_Map.getContinents()) {
            l_continentData.append(l_continent.getName()).append(" ").append(l_continent.getControlValue());
            l_continentData.append("\n");
        }
        return l_continentData;
    }

    /**
     * The method to process countries.
     *
     * @param p_Map object of the map that is being processed.
     * @return a string that contains details of countries that will ultimately be written in the map file.
     */
    private static StringBuilder processCountries(RiskMap p_Map) {
        StringBuilder l_countryData = new StringBuilder();
        l_countryData.append("\n");
        l_countryData.append("[countries]");
        l_countryData.append("\n");

        for (Continent l_continent : p_Map.getContinents()) {
            for (Country l_country : l_continent.getCountries()) {
                l_countryData.append(l_country.getDId()).append(" ").append(l_country.getName()).append(" ").append(l_continent.getId())
                        .append(" ").append(l_country.getXCoordinates()).append(" ").append(l_country.getYCoordinates());
                l_countryData.append("\n");
            }
        }
        return l_countryData;
    }

    /**
     * The method to process adjacent countries.
     *
     * @param p_Map object of map that is being processed.
     * @return a string that contains adjacent countries and that will be written in map file.
     */
    private static StringBuilder processAdjacentCountries(RiskMap p_Map) {
        StringBuilder l_countryData = new StringBuilder();
        l_countryData.append("\n");
        l_countryData.append("[borders]");
        l_countryData.append("\n");
        for (Continent l_continent : p_Map.getContinents()) {
            List<Country> l_countryList = l_continent.getCountries();
            if (l_countryList != null) {
                for (Country l_country : l_countryList) {
                    l_countryData.append(l_country.getDId());
                    for (Country l_adjacentCountries : l_country.getBorders().values()) {
                        l_countryData.append(" ");
                        l_countryData.append(l_adjacentCountries.getDId());
                    }
                    l_countryData.append("\n");
                }
            }
        }
        return l_countryData;
    }
}
