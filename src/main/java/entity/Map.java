package entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import static java.util.Objects.isNull;

/**
 * A class to hold map information, it uses a java.util.map to hold countries and continents on the map, but it has
 * no other relationship with Java standard map.
 */
public class Map {
    String d_name;
    java.util.Map<Integer, Country> d_countries;
    java.util.Map<Integer, Continent> d_continents;

    /**
     * Default Constructor that only initialize the map.
     *
     * @author Weichen
     */
    public Map() {
        d_countries = new HashMap<Integer, Country>();
        d_continents = new HashMap<Integer, Continent>();
    }

    /**
     * Constructor that takes the name of the map and initialize it.
     *
     * @param p_name The name of the map, space and other special chars are allowed.
     */
    public Map(String p_name) {
        d_countries = new HashMap<Integer, Country>();
        d_continents = new HashMap<Integer, Continent>();
        d_name = p_name;
    }

    /**
     * Getter for name of the map.
     *
     * @return The name of the map as a string.
     */
    public String getName() {
        return d_name;
    }

    /**
     * Setter for the name of the map.
     *
     * @param p_name The name of the map, space and other special chars are allowed.
     */
    public void setName(String p_name) {
        this.d_name = p_name;
    }

    /**
     * Add a country to the map.
     *
     * @param p_country The country object that you want to add.
     */
    public void addCountry(Country p_country) {
        d_countries.put(p_country.getDId(), p_country);
    }

    /**
     * Add a continent to the map.
     *
     * @param p_continent The continent object that you want to add.
     */
    public void addContinent(Continent p_continent) {
        d_continents.put(p_continent.getId(), p_continent);
    }

    /**
     * Add an array of other countries to a country.
     *
     * @param p_countryId        The id of country that you want to add connection.
     * @param p_otherCountriesId An array of countries that is connected to the country above.
     */
    public void addBorders(int p_countryId, String[] p_otherCountriesId) {
        Country l_country = d_countries.get(p_countryId);
        for (String l_oneCountry : p_otherCountriesId) {
            l_country.addBorder(d_countries.get(Integer.parseInt(l_oneCountry)));
        }
    }

    /**
     * Getter for number of countries in the map, used for validation
     *
     * @return      number of countries as an Integer.
     */
    public int getNumberOfCountries() {
        return this.d_countries.size();
    }

    /**
     * Getter for number of continents in the map, used for validation
     *
     * @return      number of continents as an Integer.
     */
    public int getNumberOfContinents() {
        return this.d_continents.size();
    }

    /**
     * Get a set of Ids of countries on the map.
     *
     * @return A set of Ids of countries on the map.
     */
    public Set getCountryIds() {
        return d_countries.keySet();
    }

    /**
     * Get a set of Ids of continents on the map
     *
     * @return A set of Ids of continents on the map.
     */
    public Set getContinentIds() {
        return d_continents.keySet();
    }

    /**
     * Get the number of army in a country.
     *
     * @param p_countryId The id of country that these army belong to.
     * @return The number of armies in this country.
     */
    public int getCountryArmyById(int p_countryId) {
        return d_countries.get(p_countryId).getArmy();
    }

    /**
     * Set the number of army in a country
     *
     * @param p_countryId The id of country that these army belong to.
     * @param p_army      The number of armies you want to set in this country.
     * @return True if the set is success; False if the set is failed.
     */
    public boolean setCountryArmyById(int p_countryId, int p_army) {
        Country l_country = d_countries.get(p_countryId);
        if (!isNull(l_country)) {
            l_country.setArmy(p_army);
            return true;
        }
        return false;
    }

    /**
     * Get country from country id
     *
     * @param p_countryId   unique country id as an Integer
     * @return              Country object
     */
    public Country getCountryById(int p_countryId) {
        return d_countries.get(p_countryId);
    }

    /**
     * Get continent from continent id
     *
     * @param p_continentId     unique continent id as an integer
     * @return                  Continent object
     */
    public Continent getContinentById(int p_continentId) {
        return d_continents.get(p_continentId);
    }

    /**
     * Override the toString method to show country detail better
     *
     * @return A string contain name of the country and the detail of the country
     */
    public String toString() {
        //TODO: modify to include continents in a legible way.
        String l_countriesString = "";
        for (Integer l_key : d_countries.keySet()) {
            l_countriesString += (d_countries.get(l_key) + "\n");
        }
        return "Map " + d_name + "\n" + l_countriesString;
    }
}
