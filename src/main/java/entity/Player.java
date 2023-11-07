package entity;

import game.GameEngine;
import game.Orders.Order;

import java.util.*;

/**
 * Class PlayerDetails contains details of the player
 *
 * @author Soham
 */
public class Player {

    private int d_availableReinforcements;
    private final String d_playerName;
    // @param listOfCountriesOwned contains the name of the country and the number of armies present.
    private final ArrayList<Country> d_listOfCountriesOwned;
    private final ArrayList<Order> d_orders = new ArrayList<>();
    private int bonusForOwningContinent = 0;
    private final RiskMap d_map;
    private final ArrayList<CardType> d_ownedCards;
    private final ArrayList<UUID> d_negotiatedPlayers;
    private final UUID d_playerId;
    private final Random d_randGen;

    Player() {
       this("", null);
    }

    Player(String p_playerName, RiskMap p_map) {
        d_playerId = UUID.randomUUID();
        this.d_availableReinforcements = 5;
        this.d_playerName = p_playerName;
        d_listOfCountriesOwned = new ArrayList<>();
        d_ownedCards = new ArrayList<>();
        d_negotiatedPlayers = new ArrayList<>();
        d_map = p_map;
        d_randGen = new Random(d_playerId.hashCode());
    }

    /**
     * Issues an order and stores it in the d_orders variable
     * the variable will be used once it has to be executed
     */
    public void issueOrder(Order p_newOrder) {
        if (p_newOrder == null) {
            return;
        }
        d_orders.add(p_newOrder);
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
        ArrayList<Country> continentsWithCountries = new ArrayList<>();
        if (d_map == null) {
            bonusForOwningContinent = 0;
            return;
        }

        var continent = d_map.getContinents();
        for (Continent value : continent) {
            continentsWithCountries.addAll(value.getCountries());
            if (d_listOfCountriesOwned.contains(continentsWithCountries)) {
                bonusForOwningContinent = value.getControlValue();
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

    /**
     * @return UUID of player.
     */
    public UUID getPlayerId(){
        return d_playerId;
    }

    /**
     * Adds a random card to the card list of player
     */
    public void addRandomCard(){
        CardType[] l_cards = CardType.values();
        addCard(l_cards[d_randGen.nextInt(0,l_cards.length)]);
    }

    /**
     * Add a card of specific type to player cards list
     * @param p_cardType the type of card to add.
     */
    public void addCard(CardType p_cardType){
        d_ownedCards.add(p_cardType);
    }

    /**
     * check if a card is available to the player
     * @param p_cardType type of card to check
     * @return true if card found, else false
     */
    public boolean isCardAvailable(CardType p_cardType){
        return d_ownedCards.contains(p_cardType);
    }

    /**
     * removes a card from the player card list.
     * @param p_cardType the type of card to remove
     */
    public void removeCard(CardType p_cardType){
        d_ownedCards.remove(p_cardType);
    }

    /**
     * @return no.of cards available to player.
     */
    public int totalCardsInPossesion(){
        return d_ownedCards.size();
    }

    /**
     * prints the player object details.
     * @return string with player details.
     */
    @Override
    public String toString() {
        String l_nameStr = "Name: " + d_playerName;
        String l_reinforcementsStr = "\n\tRe-inforcements: " + d_availableReinforcements;
        String l_cards = "\n\tAvailable Cards: ";
        Map<CardType, Integer> cardMap = new HashMap<>();
        for(CardType card : d_ownedCards){
            if(cardMap.containsKey(card)) {
                cardMap.put(card, cardMap.get(card) + 1);
            }
            else
                cardMap.put(card, 1);
        }

        for(CardType card : CardType.values()){
            l_cards += "\n\t\t"+card + ": " + (cardMap.containsKey(card) ? cardMap.get(card) : 0);
        }

        return l_nameStr + l_reinforcementsStr + l_cards;
    }

    /**
     * @return true if player has orders.
     */
    public boolean hasOrders(){
        return !d_orders.isEmpty();
    }
}
