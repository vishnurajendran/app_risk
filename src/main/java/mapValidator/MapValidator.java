package mapValidator;

import entity.Map;
import entity.Country;
import java.util.ArrayList;

import static java.util.Objects.isNull;

public class MapValidator {

    static void isConnectedGraph(ArrayList<Integer> p_visited, Integer p_country, Map p_map) {
        //dfs
        if(p_visited.contains(p_country))return;
        p_visited.add(p_country);
        java.util.Map<Integer,Country> l_adjacentBorders = p_map.getCountryById(p_country).getBorders();

        for (int l_adjCountryId : l_adjacentBorders.keySet()){
            isConnectedGraph(p_visited, l_adjCountryId, p_map);
        }

    }

    public static boolean validateMap(Map p_map) {

        //empty map check;
        if(isNull(p_map)){
            return false;
            //throw exception-map null?
        }

        boolean l_isConnected = false;
        ArrayList<Integer> l_visited = new ArrayList<Integer>();
        ArrayList<Integer> l_countries = new ArrayList<Integer>(p_map.getCountryIds());

        //check if the map is a connected graph
        isConnectedGraph(l_visited, l_countries.get(0), p_map);

        if(l_visited.size() == p_map.getNumberOfCountries()) {
            return true;
        }
        else {
            return false;
        }
    }

}