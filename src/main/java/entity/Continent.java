package entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This class hold information about a single continent.
 * It also stores a map of countries of the continent.
 *
 * @author TaranjeetKaur
 */
public class Continent {

    private int d_id;
    private String d_name;
    private int d_controlValue;
    private Map<Integer, Country> d_countries;
    private String d_color;

    /**
     * Parameterised Constructor for the Continent
     *
     * @param p_id           the number id for the continent, should be unique.
     * @param p_name         the name of the continent
     * @param p_controlValue the control value depicts the number of armies per turn
     *                       that is given to the player that control all of it
     * @param p_color        the color of the continent (useful for UI)
     */
    public Continent(int p_id, String p_name, int p_controlValue, String p_color) {
        this.d_id = p_id;
        this.d_name = p_name;
        this.d_controlValue = p_controlValue;
        this.d_color = p_color;
        this.d_countries = new HashMap<Integer, Country>();
    }

    /**
     * Parameterised Constructor for the Continent without color
     *
     * @param p_id           the number id for the continent, should be unique.
     * @param p_name         the name of the continent
     * @param p_controlValue the control value depicts the number of armies per turn
     *                       that is given to the player that control all of it
     */
    public Continent(int p_id, String p_name, int p_controlValue) {
        this.d_id = p_id;
        this.d_name = p_name;
        this.d_controlValue = p_controlValue;
        this.d_color = null;
        this.d_countries = new HashMap<Integer, Country>();
    }

    /**
     * Clone method for returning a clone of the object Continent
     * This methods take a clone map object and adds the cloned countries of the map in the continent
     *
     * @param p_riskMap     the number id for the continent, should be unique.
     * @return Continent object
     */
    public Continent clone(RiskMap p_riskMap) {
        Continent l_continent = new Continent(this.d_id, this.d_name, this.d_controlValue, this.d_color);
        for (Map.Entry<Integer, Country> l_entry : this.d_countries.entrySet()) {
            l_continent.d_countries.put(l_entry.getKey(), p_riskMap.getCountryById(l_entry.getKey()));
        }
        return l_continent;
    }

    /**
     * This method adds new country in the continent object.
     *
     * @param p_country Country object to be added
     */
    public void addCountry(Country p_country) {
        this.d_countries.put(p_country.getDId(), p_country);
    }

    /**
     * This method removes an existing country from the continent object.
     *
     * @param p_country country to be removed
     */
    public void removeCountry(Country p_country) {
        this.d_countries.remove(p_country.getDId());
    }

    /**
     * This method checks if a country is present in the continent
     *
     * @param p_countryId countryId of the Country object as an Integer
     * @return true if country is present, false otherwise.
     */
    public boolean hasCountry(Integer p_countryId) {
        return this.d_countries.containsKey(p_countryId);
    }

    /**
     * Getter for id
     *
     * @return the unique id for the continent as an Integer
     */
    public int getId() {
        return this.d_id;
    }

    /**
     * Setter for id
     *
     * @param p_id the unique id for the continent
     */
    public void setId(int p_id) {
        this.d_id = p_id;
    }

    /**
     * Getter for Name
     *
     * @return the name of the continent as a String
     */
    public String getName() {
        return this.d_name;
    }

    /**
     * Setter for name
     *
     * @param p_name the name of the continent
     */
    public void setName(String p_name) {
        this.d_name = p_name;
    }

    /**
     * Getter for ControlValue
     *
     * @return the number of armies per turn that is given to the player
     * that control all of it as an Integer
     */
    public int getControlValue() {
        return this.d_controlValue;
    }

    /**
     * Setter for Control Value
     *
     * @param p_controlValue the control value depicts the number of armies per turn
     *                       that is given to the player that control all of it
     */
    public void setControlValue(int p_controlValue) {
        this.d_controlValue = p_controlValue;
    }

    /**
     * Getter for color
     *
     * @return the color of the continent as a String
     */
    public String getColor() {
        return this.d_color;
    }

    /**
     * Setter for color
     *
     * @param p_color the color of the continent (useful for UI)
     */
    public void setColor(String p_color) {
        this.d_color = p_color;
    }

    /**
     * This method returns a List of Countries present in the map.
     *
     * @return ArrayList of Country Object.
     */
    public ArrayList<Country> getCountries() {
        return new ArrayList<Country>(d_countries.values());
    }

    /**
     * Override the toString to show detailed info of the class
     *
     * @return A string contain ID, name and Control value.
     */
    public String toString() {
       return "Id: " + d_id + " Name: " + d_name + " ControlValue: " + d_controlValue + " Color:" + d_color + " Countries: " + this.d_countries.keySet().toString();
    }

}
