package mapEditer;

import common.FileIO.FileIO;
import entity.Continent;
import entity.Country;
import entity.RiskMap;

import java.io.File;

public class ConquestMapWriter implements MapWriter {

    /**
     * Write using conquest file format
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
     * The method to process the map by calling two different methods and to make a string to be written in map file.
     *
     * @param p_Map object of the map which is processed
     * @return String to be written in the map file
     */
    private static String parseMapAndReturnString(RiskMap p_Map) {

        return "[Map]\n\n" + String.valueOf(processContinent(p_Map)) + "\n" +
                processCountries(p_Map);
    }

    /**
     * The method to process continents.
     *
     * @param p_Map object of the map which is being processed.
     * @return string that contains details of the continents that will eventually be written in the map file.
     */
    private static String processContinent(RiskMap p_Map) {
        StringBuilder l_continentData = new StringBuilder();
        l_continentData.append("[Continents]");
        l_continentData.append("\n");

        for (Continent l_continent : p_Map.getContinents()) {
            l_continentData.append(l_continent.getName()).append("=").append(l_continent.getControlValue());
            l_continentData.append("\n");
        }
        return l_continentData.toString();


    }

    /**
     * The method to process countries and borders.
     *
     * @param p_Map object of the map that is being processed.
     * @return a string that contains details of countries that will ultimately be written in the map file.
     */
    private static String processCountries(RiskMap p_Map) {
        StringBuilder l_countryData = new StringBuilder();
        l_countryData.append("\n");
        l_countryData.append("[Territories]");
        l_countryData.append("\n");

        for (Continent l_continent : p_Map.getContinents()) {
            for (Country l_country : l_continent.getCountries()) {
                l_countryData.append(l_country.getName()).append(",").append(l_country.getXCoordinates()).append(",").append(l_country.getYCoordinates())
                        .append(",").append(l_continent.getName());
                // add border in contries as required for conquest map
                for (Country l_neightBour : l_country.getBorders().values()) {
                    l_countryData.append(",").append(l_neightBour.getName());
                }
                l_countryData.append("\n");
            }
        }
        return l_countryData.toString();
    }


}
