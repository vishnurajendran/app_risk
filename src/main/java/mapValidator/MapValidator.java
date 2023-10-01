package mapValidator;

import common.Logger;
import entity.RiskMap;
import entity.Country;
import java.util.ArrayList;
import java.util.Map;

import static java.util.Objects.isNull;

public class MapValidator {

    static void isConnectedGraph(ArrayList<Integer> p_visited, Integer p_country, RiskMap p_riskMap) {
        //dfs
        if(p_visited.contains(p_country))return;
        p_visited.add(p_country);
        Map<Integer,Country> l_adjacentBorders = p_riskMap.getCountryById(p_country).getBorders();

        for (int l_adjCountryId : l_adjacentBorders.keySet()){
            isConnectedGraph(p_visited, l_adjCountryId, p_riskMap);
        }

    }

    public static boolean validateMap(RiskMap p_riskMap) {

        if(isNull(p_riskMap)){
            //console log?
            Logger.log("Validate-map: map is null");
            return false;
        }

        boolean l_isConnected = false;
        ArrayList<Integer> l_visited = new ArrayList<Integer>();
        ArrayList<Integer> l_countries = new ArrayList<Integer>(p_riskMap.getCountryIds());

        if(l_countries.isEmpty()) {
            Logger.log("Validate-map: map is empty");
            return false;
        }

        //check if the map is a connected graph
        isConnectedGraph(l_visited, l_countries.get(0), p_riskMap);

        if(l_visited.size() == p_riskMap.getNumberOfCountries()) {
            return true;
        }
        else {
            return false;
        }
    }

}