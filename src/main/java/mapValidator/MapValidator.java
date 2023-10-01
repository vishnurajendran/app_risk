package mapValidator;

import common.Logger;
import entity.Continent;
import entity.RiskMap;
import entity.Country;
import java.util.ArrayList;
import java.util.Map;

import static java.util.Objects.isNull;

public class MapValidator {

    static void isConnectedGraph(ArrayList<Integer> p_visited, Country p_country) {
        //dfs
        if(p_visited.contains(p_country.getDId()))return;
        p_visited.add(p_country.getDId());
        Map<Integer,Country> l_adjacentBorders = p_country.getBorders();

        for (Country l_adjCountry : l_adjacentBorders.values()){
            isConnectedGraph(p_visited, l_adjCountry);
        }

    }

    static void isContinentConnectedSubgraph(ArrayList<Integer> p_visited, Country p_country, Continent p_continent) {
        //visit only if country is part of the continent
        if(p_visited.contains(p_country.getDId()) || !p_continent.hasCountry(p_country.getDId()))return;
        p_visited.add(p_country.getDId());
        Map<Integer,Country> l_adjacentBorders = p_country.getBorders();

        for (Country l_adjCountry : l_adjacentBorders.values()){
            isContinentConnectedSubgraph(p_visited, l_adjCountry, p_continent);
        }
    }

    static boolean isCountryPartOfOnlyOneContinent(ArrayList<Continent> p_continents){
        ArrayList<Integer> l_visited = new ArrayList<Integer>();
        for(Continent l_continentOne : p_continents) {
            ArrayList<Country> l_countriesOfContinent = l_continentOne.getCountries();
            for(Country l_country: l_countriesOfContinent) {
                if(l_visited.contains(l_country.getDId())) return false;
                l_visited.add(l_country.getDId());
            }
        }
        return true;
    }

    public static boolean validateMap(RiskMap p_riskMap) {

        if(isNull(p_riskMap)){
            //add console log?
            Logger.log("Validate-map: map is null");
            return false;
        }

        boolean l_isConnected = false;
        ArrayList<Integer> l_visited = new ArrayList<Integer>();
        ArrayList<Country> l_countries = p_riskMap.getCountries();
        ArrayList<Continent> l_continents = p_riskMap.getContinents();

        if(l_countries.isEmpty() || l_continents.isEmpty()) {
            Logger.log("Validate-map: map is empty");
            return false;
        }

        //check if the map is a connected graph
        isConnectedGraph(l_visited, l_countries.get(0));
        if(l_visited.size() == l_countries.size()) {
            l_isConnected = true;
        }
        else {
            Logger.log("Validate-map: map not a connected graph");
            return false;
        }

        l_visited.clear();
        boolean l_isConnectedSubgraph = false;
        //check if each continent is a connected subgraph
        for(Continent l_continentOne : l_continents) {
            ArrayList<Country> l_countriesOfContinent = l_continentOne.getCountries();
            isContinentConnectedSubgraph(l_visited, l_countriesOfContinent.get(0), l_continentOne);
            if(l_visited.size() == l_countriesOfContinent.size()) {
                l_isConnectedSubgraph = true;
            }
            else {
                Logger.log("Validate map: Continent not a connected sub-graph:" + l_continentOne.toString());
                return false;
            }
            l_visited.clear();
        }

        //check if each country is part of only one continent
        boolean valid = isCountryPartOfOnlyOneContinent(l_continents);

        return l_isConnected ;
    }

}