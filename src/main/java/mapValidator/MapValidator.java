package mapValidator;

import entity.Map;
import entity.Country;
import java.util.ArrayList;

import static java.util.Objects.isNull;

public class MapValidator{

    static void isConnectedGraph(ArrayList<Country> p_visited, Country p_country, Map p_map){
        //dfs
        if(p_visited.contains(p_country))return;
        p_visited.add(p_country);
        java.util.Map<Integer,Country> l_adjacentBorders = p_country.getBorders();
        //can change this map to a list for easy traversal.

        for (int l_adjCountryId : l_adjacentBorders.keySet()){
            isConnectedGraph(p_visited, p_map.getCountryById(l_adjCountryId), p_map);
        }

    }

    public static boolean isMapValid(Map p_map){

        if(isNull(p_map)){
            return false;
            //throw exception-map null?
        }

        boolean l_isConnected = false;
        ArrayList<Country> l_visited = new ArrayList<Country>();
        ArrayList<Country> l_countries = p_map.getCountries();
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