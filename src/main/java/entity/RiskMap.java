package entity;

import common.Logger;

import java.util.*;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * A class to hold map information, it uses a java.util.map to hold countries and continents on the map, but it has
 * no other relationship with Java standard map.
 *
 * @author Weichen
 */
public class RiskMap {
    private String d_name;
    private Map<Integer, Country> d_countries;
    private Map<Integer, Continent> d_continents;

    /**
     * Default Constructor that only initialize the map.
     */
    public RiskMap() {
        d_countries = new HashMap<Integer, Country>();
        d_continents = new HashMap<Integer, Continent>();
    }

    /**
     * Constructor that takes the name of the map and initialize it.
     *
     * @param p_name The name of the map, space and other special chars are allowed.
     */
    public RiskMap(String p_name) {
        d_countries = new HashMap<Integer, Country>();
        d_continents = new HashMap<Integer, Continent>();
        d_name = p_name;
    }

    /**
     * Clone method for returning a clone of the object Riskmap
     *
     * @return Riskmap object
     */
    public RiskMap clone() {
        RiskMap l_riskMapClone = new RiskMap(this.d_name);
        for (Map.Entry<Integer, Country> l_entryCountry : this.d_countries.entrySet()) {
            l_riskMapClone.d_countries.put(l_entryCountry.getKey(), l_entryCountry.getValue().clone());
        }
        for (Map.Entry<Integer, Continent> l_entryContinent : this.d_continents.entrySet()) {
            l_riskMapClone.d_continents.put(l_entryContinent.getKey(), l_entryContinent.getValue().clone());
        }
        return l_riskMapClone;
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
     * Checks if the map has the continent with given continentId.
     *
     * @param p_continentId id of continent.
     * @return true if continent present, false otherwise.
     */
    public boolean hasContinent(Integer p_continentId) {
        return d_continents.containsKey(p_continentId);
    }

    /**
     * Checks if the map has the continent with given countryId.
     *
     * @param p_countryId id of country as an integer
     * @return true if continent present, false otherwise.
     */
    public boolean hasCountry(Integer p_countryId) {
        return d_countries.containsKey(p_countryId);
    }

    /**
     * Add a country to the map.
     *
     * @param p_country The country object that you want to add.
     */
    public void addCountry(Country p_country) {
        addCountryToContinent(p_country);
        d_countries.put(p_country.getDId(), p_country);
    }

    /**
     * This method adds the country added to the map to Continent object.
     *
     * @param p_country Country object of the country to be added.
     */
    void addCountryToContinent(Country p_country) {
        Continent l_continent = getContinentById(p_country.getContinentId());
        if (nonNull(l_continent)) {
            l_continent.addCountry(p_country);
        } else {
            Logger.logError("Riskmap loader: country added to null continent");
        }
    }

    /**
     * This method removes the country from continent
     *
     * @param p_country country object to be removed
     */
    void removeCountryFromContinent(Country p_country) {
        Continent l_continent = getContinentById(p_country.getContinentId());
        if (nonNull(l_continent)) {
            l_continent.removeCountry(p_country);
        } else {
            Logger.logError("Riskmap loader: country removed from null continent");
        }
    }

    /**
     * This method removes the country from map
     *
     * @param p_country country object to be removed
     */
    public void removeCountry(Country p_country) {
        removeCountryFromContinent(p_country);
        d_countries.remove(p_country.getDId());
    }

    /**
     * This method removes continent from the map.
     *
     * @param p_continent Object continent to be removed.
     */
    public void removeContinent(Continent p_continent) {
        //remove all countries of the continent
        if (hasContinent(p_continent.getId())) {
            ArrayList<Country> l_countries = getCountries();
            for (Country l_country : l_countries) {
                removeCountry(l_country);
            }
            d_continents.remove(p_continent.getId());
        }
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
     * This behaviour will only add a one way direction from first country to the rest countries in the list
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
     * Add a single connection between these two countries. This behaviour will be done to both country.
     *
     * @param p_countryId        The first country ID
     * @param p_anotherCountryId The second country ID
     * @return true if border was added successfully, else false
     */
    public boolean addBorder(int p_countryId, int p_anotherCountryId) {
        Country l_country = d_countries.get(p_countryId);
        Country l_anotherCountry = d_countries.get(p_anotherCountryId);
        if (isNull(l_country) || isNull(l_anotherCountry)) {
            return false;
        }
        l_country.addBorder(l_anotherCountry);
        l_anotherCountry.addBorder(l_country);
        return true;
    }

    /**
     * Get a set of Ids of countries on the map.
     *
     * @return A set of Ids of countries on the map.
     */
    public Set<Integer> getCountryIds() {
        return d_countries.keySet();
    }

    /**
     * This method returns a list of countries present in map.
     *
     * @return ArrayList of country Object.
     */
    public ArrayList<Country> getCountries() {
        return new ArrayList<Country>(d_countries.values());
    }

    /**
     * Get a set of Ids of continents on the map
     *
     * @return A set of Ids of continents on the map.
     */
    public Set<Integer> getContinentIds() {
        return d_continents.keySet();
    }


    /**
     * This method returns a list of continents present in map.
     *
     * @return ArrayList of continent Object.
     */
    public ArrayList<Continent> getContinents() {
        return new ArrayList<Continent>(d_continents.values());
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
     * Increase the army value of the country
     *
     * @param p_countryId The id that we got from the player deployment
     * @param p_army      The number of armies that the player wants to deploy on this country
     */
    public void increaseCountryArmyById(int p_countryId, int p_army) {
        Country l_country = d_countries.get(p_countryId);
        l_country.incrementArmy(p_army);

    }

    /**
     * Get country from country id
     *
     * @param p_countryId unique country id as an Integer
     * @return Country object
     */
    public Country getCountryById(int p_countryId) {
        return d_countries.get(p_countryId);
    }

    /**
     * Get continent from continent id
     *
     * @param p_continentId unique continent id as an integer
     * @return Continent object
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
        String l_countriesString = "";
        for (Integer l_key : d_countries.keySet()) {
            l_countriesString += (d_countries.get(l_key) + "\n");
        }
        return "Map " + d_name + "\n" + l_countriesString;
    }
}
