package mapSave;
import entity.Continent;
import entity.Country;
import entity.RiskMap;

import java.io.PrintWriter;
import java.util.Set;
import java.util.List;



/**
 * The class  will save the data in map file when the user creates a map
 *
 * @author Rachit
 */

public class SaveMap {

    private RiskMap riskMap;
    /**
     * Create a neighbor list as a string for the countries
     *
     * @param p_Neighbors the country to add to neighbor list
     * @return String result of neighbor list
     */
    public String createANeighborList(Set<Country> p_Neighbors) {
        String l_result = "";
        for (Country l_Neighbor : p_Neighbors) {
            l_result += l_Neighbor.getName() + " ";
        }
        return l_result.length() > 0 ? l_result.substring(0, l_result.length() - 1) : "";
    }
    /**
     * Save map into file as continent and country
     *
     * @param name name of file
     * @param p_GameMap parameter GameMap class
     * @return boolean true if written
     */

    public boolean saveMapIntoFile(RiskMap p_GameMap, String name) {
        String mapData = "[Map]\n\n[Continents]\n";
        List<Continent> continents = p_GameMap.getContinentIds();
        int numberOfContinents = continents.size();

        for (int i = 0; i < numberOfContinents; i++) {
            Continent continent = continents.get(i);
            mapData += continent.getName() + " " + continent.getControlValue();
            mapData += "\n";
        }

        mapData += "[Territories]\n";
        for (Continent continent : p_GameMap.getContinentIds()) {
            for (Country country : p_GameMap.getCountryIds()) {
                mapData += country.getName() + " " + country.getDId() + " " + (country.getBorders()) + "\n";
            }

            PrintWriter writeData = null;
            try {
                writeData = new PrintWriter("testMap.map/" + name + ".map");
                writeData.println(mapData);
                return true;
            } catch (Exception ex) {
                return false;
            } finally {
                writeData.close();
            }

        }
        return true;

    }
}
