package entity;

import java.util.HashMap;
import java.util.Set;

import static java.util.Objects.isNull;

/**
 * A class to hold map information, it uses a java.util.map to hold countries on the map, but it has no other relationship
 * with Java standard map.
 *
 */
public class Map {
    String d_name;
    //used for store data for demo only, can change to some other data structure
    java.util.Map<Integer,Country> d_countries;

    /**
     * Default Constructor that only initialize the array.
     *
     * @author Weichen
     */
    public Map() {
        d_countries =new HashMap<Integer,Country>();
    }

    /**
     * Constructor that takes the name of the mao and initialize it.
     * @param p_name The name of the map, space and other special chars are allowed.
     */
    public Map(String p_name){
        d_countries =new HashMap<Integer,Country>();
        d_name=p_name;
    }

    /**
     * Getter for name of the map.
     * @return The name of the map as a string.
     */
    public String getName() {
        return d_name;
    }

    /**
     * Setter for the name of the map.
     * @param p_name The name of the map, space and other special chars are allowed.
     */
    public void setName(String p_name) {
        this.d_name = p_name;
    }

    /**
     * Add a country to the map.
     * @param p_Country The country object that you want to add.
     */
    public void addCountry(Country p_Country){
        d_countries.put(p_Country.getDId(),p_Country);
    }

    /**
     * Add an array of other countries to a country.
     * @param p_countryId The id of country that you want to add connection.
     * @param p_otherCountriesId An array of countries that is connected to the country above.
     */
    public void addBorders(int p_countryId,String[] p_otherCountriesId){
        Country l_country= d_countries.get(p_countryId);
        for(String l_oneCountry:p_otherCountriesId){
            l_country.addBorder(d_countries.get(l_oneCountry));
        }
    }

    /**
     * Get a set of Ids of countries on the map.
     * @return A set of Ids of countries on the map.
     */
    public Set getCountryIds(){
        return d_countries.keySet();
    }

    /**
     * Get the number of army in a country.
     * @param p_countryId The id of country that these army belong to.
     * @return The number of armies in this country.
     */
    public int getCountryArmyById(int p_countryId){
        return d_countries.get(p_countryId).getArmy();
    }

    /**
     * Set the number of army in a country
     * @param p_countryId The id of country that these army belong to.
     * @param p_army The number of armies you want to set in this country.
     * @return True if the set is success; False if the set is failed.
     */
    public boolean setCountryArmyById(int p_countryId,int p_army){
        Country l_country=d_countries.get(p_countryId);
        if(!isNull(l_country)){
            l_country.setArmy(p_army);
            return true;
        }
        return false;
    }

    /**
     * Override the toString method to show country detail better
     * @return A string contain name of the country and the detail of the country
     */
    public String toString(){
        String l_countriesString="";
        for(Integer l_key: d_countries.keySet()){
            l_countriesString+=(d_countries.get(l_key)+"\n");
        }
        return "Map "+d_name+"\n"+l_countriesString;
    }
}
