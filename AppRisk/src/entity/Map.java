package entity;

import java.util.HashMap;
import java.util.Set;
import java.util.ArrayList;

import static java.util.Objects.isNull;

public class Map {
    String d_name;
    //used for store data for demo only, can change to some other data structure
    java.util.Map<Integer,Country> d_countries;

    ArrayList<Country> d_countriesList;
   // ArrayList<Continent> d_continents; //list of all continents for validating sub-graphs.
    int d_numOfCountries;

    public Map() {
        d_countries =new HashMap<Integer,Country>();
    }

    public Map(String p_name){
        d_countries =new HashMap<Integer,Country>();
        d_name=p_name;
    }

    public String getName() {
        return d_name;
    }

    public void setName(String d_name) {
        this.d_name = d_name;
    }

    public int getNumberOfCountries() {
    	return this.d_numOfCountries;
    }

    public ArrayList<Country> getCountries() {
        return this.d_countriesList;
    }

    public void addCountry(Country p_Country){
        d_countries.put(p_Country.getDId(),p_Country);
    }

    public void addBorders(int p_countryId,String[] p_otherCountriesId){
        Country l_country= d_countries.get(p_countryId);
        for(String l_oneCountry:p_otherCountriesId){
            l_country.addBorder(d_countries.get(l_oneCountry));
        }
    }
    
    public Country getCountryById(int p_countryId){
    	 return d_countries.get(p_countryId);
    }

    public Set getCountryIds(){
        return d_countries.keySet();
    }

    public int getCountryArmyById(int p_countryId){
        return d_countries.get(p_countryId).getArmy();
    }
    public boolean setCountryArmyById(int p_countryId,int p_army){
        Country l_country=d_countries.get(p_countryId);
        if(isNull(l_country)){
            l_country.setArmy(p_army);
            return true;
        }
        return false;
    }

    public String toString(){
        String l_countriesString="";
        for(Integer l_key: d_countries.keySet()){
            l_countriesString+=(d_countries.get(l_key)+"\n");
        }
        return "Map "+d_name+"\n"+l_countriesString;
    }
}
