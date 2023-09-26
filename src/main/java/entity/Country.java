package entity;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.isNull;

/**
 * This class hold information about a single country.
 * It uses a Java map to hold connection to other country connected to it.
 *
 * @author Weichen
 */
public class Country {
    int d_id;
    String d_name;

    int d_army;

    int d_continentId;
    //offered in the sample file and not sure if we need to use
    int d_xCoordinates;
    int d_yCoordinates;

    //LinkedList<Integer> d_borders;
    Map<Integer, Country> d_borders;

    /**
     * Constructor for the country.
     *
     * @param p_id           The number id for the country, should be unique.
     * @param p_name         The name of the country.
     * @param p_continentId  The continent ID that this country below to.
     * @param p_xCoordinates The X Coordinates of this country coming from standard domination map, might not be needed for this command line version.
     * @param p_yCoordinates The Y Coordinates of this country coming from standard domination map.
     */
    public Country(int p_id, String p_name, int p_continentId, int p_xCoordinates, int p_yCoordinates) {
        this.d_id = p_id;
        this.d_name = p_name;
        this.d_continentId = p_continentId;
        this.d_xCoordinates = p_xCoordinates;
        this.d_yCoordinates = p_yCoordinates;
        d_borders = new HashMap<>();
    }

    /**
     * Setter for ID
     *
     * @param p_id The number id for the country, should be unique.
     */
    public void setId(int p_id) {
        this.d_id = p_id;
    }

    /**
     * Setter for Continent ID.
     *
     * @param p_continentId The continent ID that this country below to.
     */
    public void setContinentId(int p_continentId) {
        this.d_continentId = p_continentId;
    }

    /**
     * Setter for XCoordinates.
     *
     * @param p_xCoordinates The X Coordinates of this country coming from standard domination map, might not be needed for this command line version.
     */
    public void setXCoordinates(int p_xCoordinates) {
        this.d_xCoordinates = p_xCoordinates;
    }

    /**
     * Setter for YCoordinates
     * @param p_yCoordinates The Y Coordinates of this country coming from standard domination map.
     */
    public void setYCoordinates(int p_yCoordinates) {
        this.d_yCoordinates = p_yCoordinates;
    }

    /**
     * Setter for name of the country.
     * @param d_name The name of the country.
     */
    public void setD_name(String d_name) {
        this.d_name = d_name;
    }

    /**
     * Setter for number of army in the country.
     * @param d_army The amount of armies inside the country as an int.
     */
    public void setArmy(int d_army) {
        this.d_army = d_army;
    }

    /**
     * Getter for armies in the country.
     * @return The amount of armies inside the country as an int.
     */
    public int getArmy() {
        return d_army;
    }


    /**
     * Getter for the name of the country.
     * @return The name of the country.
     */
    public String getD_name() {
        return d_name;
    }

    /**
     * Getter for the ID of the country.
     * @return The ID of the country.
     */
    public int getDId() {
        return d_id;
    }

    /**
     * Getter for continentId
     * @return The ID of continent that the country belong to.
     */
    public int getContinentId() {
        return d_continentId;
    }

    /**
     * Getter for X coordinates
     * @return X coordinates of the country
     */
    public int getXCoordinates() {
        return d_xCoordinates;
    }

    /**
     * Getter for Y coordinates
     * @return Y coordinates of the country
     */
    public int getYCoordinates() {
        return d_yCoordinates;
    }

    /**
     * Getter for borders
     * @return A java standard map represent other countries connected to the country.
     */
    public Map<Integer, Country> getBorders() {
        return d_borders;
    }

    /**
     * Add a country to connect to this country.
     * @param p_border The ID for the country that is connected to this country
     * @return Whether the border is successfully added.
     */
    public boolean addBorder(Country p_border) {
        if (isNull(d_borders.get(p_border.getDId()))) {
            d_borders.put(p_border.d_id, p_border);
            return true;
        }

        return false;
    }

    /**
     * Override the toString to show detailed info of the class
     * @return A string contain ID, name continentID and borders details.
     */
    public String toString() {
        return "Id: " + d_id + " Name: " + d_name + " continentId: " + d_continentId + d_borders.toString();
    }
}