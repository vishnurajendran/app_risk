package entity;

import game.GameEngine;
import game.Orders.Order;

import java.util.ArrayList;

/**
 * Class PlayerDetails contains details of the player
 *
 * @author Soham
 */
public class Player {

    private int d_availableReinforcements;
    private String d_playerName;
    // @param listOfCountriesOwned contains the name of the country and the number of armies present.
    private ArrayList<Country> d_listOfCountriesOwned;

    private ArrayList<Order> d_orders = new ArrayList<>();

    private Order d_tempOrder;

    private int bonusForOwningContinent = 0;

    private RiskMap d_map;

    Player() {
        d_playerName = "";
        d_availableReinforcements = 0;
        d_listOfCountriesOwned = new ArrayList<>();
        d_orders = new ArrayList<>();
        d_map = null;
    }

    Player(String p_playerName, RiskMap p_map) {
        this.d_availableReinforcements = 5;
        this.d_playerName = p_playerName;
        d_listOfCountriesOwned = new ArrayList<>();
        d_map = p_map;
    }

    /**
     * Issues an order and stores it in the d_orders variable
     * the variable will be used once it has to be executed
     */
    public void issueOrder() {
        if (d_tempOrder == null) {
            return;
        }
        d_orders.add(d_tempOrder);
    }

    public void setTempOrder(Order p_newOrder) {
        d_tempOrder = p_newOrder;
        //d_orders.add(p_newOrder);
    }

    /**
     * This method assigns armies to the player according to their country owned
     * Also processes if a player owns a whole continent and adds a bonus value if so
     */
    public void assignReinforcementsToPlayer() {
        calculateBonusReinforcements();
        double l_calculateReinforcements = Math.max(3, Math.floor((double) d_listOfCountriesOwned.size() / 3)) + bonusForOwningContinent;
        d_availableReinforcements = (int) l_calculateReinforcements;
    }

    /**
     * Calculates the bonus armies given to the player
     * if they own every country in a particular continent
     */
    public void calculateBonusReinforcements() {
        ArrayList<Country> continentsWithContries = new ArrayList<>();
        if (d_map == null) {
            bonusForOwningContinent = 0;
            return;
        }

        var continent = d_map.getContinents();
        for (int i = 0; i < continent.size(); i++) {
            continentsWithContries.addAll(continent.get(i).getCountries());
            if (d_listOfCountriesOwned.contains(continentsWithContries)) {
                bonusForOwningContinent = continent.get(i).getControlValue();
            }
        }
    }

    /**
     * @return next order in queue
     */
    public Order nextOrder() {
        if (d_orders.isEmpty()) {
            return null;
        }
        Order l_order = d_orders.get(0);
        d_orders.remove(0);
        return l_order;
    }

    /**
     * This constructor is used to add new players
     * The number of armies are automatically assigned at 5
     */

    public void assignCountry(Country p_country, int p_noOfArmies) {
        d_listOfCountriesOwned.add(p_country);
    }

    /**
     * assigns re-inforcements to a country owned by player.
     *
     * @param p_countryId      Id of the country
     * @param p_reinforcements no. of re-inforcements.
     */
    public void assignReinforcementsToCountry(int p_countryId, int p_reinforcements) {
        d_map.increaseCountryArmyById(p_countryId, p_reinforcements);
    }

    /**
     * @return name of player
     */
    public String getPlayerName() {
        return d_playerName;
    }

    /**
     * @return re-inforcements held by player
     */
    public int getAvailableReinforcements() {
        return d_availableReinforcements;
    }

    /**
     * updates the re-inforcements held by player
     *
     * @param p_reinforcements value to update to
     */
    public void setAvailableReinforcements(int p_reinforcements) {
        d_availableReinforcements = p_reinforcements;
    }

    /**
     * @return list of Country owned by player
     */
    public ArrayList<Country> getCountriesOwned() {
        return d_listOfCountriesOwned;
    }

    /**
     * @return order queue length
     */
    public int getOrderSize() {
        return d_orders.size();
    }
}
