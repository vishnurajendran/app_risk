package entity;
import common.Logging.Logger;
import common.Serialisation.ExcludeSerialisation;
import entity.Strategy.*;
import game.Data.StrategyData;
import game.IEngine;
import game.Orders.Order;
import game.Orders.Serailisation.OrderSaveData;
import game.Orders.Serailisation.OrderSaveConverter;

import java.util.*;

import static java.util.Objects.isNull;

/**
 * Class PlayerDetails contains details of the player
 * @author Soham
 */
public class Player {

    private int d_availableReinforcements;
    private final String d_playerName;
    private final ArrayList<Integer> d_listOfCountriesOwned;

    //we will add these back manually when loading saves.
    @ExcludeSerialisation
    private ArrayList<Order> d_orders = new ArrayList<>();
    private int bonusForOwningContinent = 0;

    //we will exclude the map reference from any serialisations.
    @ExcludeSerialisation
    private RiskMap d_map;
    private ArrayList<CardType> d_ownedCards;
    private ArrayList<Integer> d_negotiatedPlayers;
    private final int d_playerId;

    @ExcludeSerialisation
    private Strategy d_playerStrategy;

    public Player() {
        this(0,"", null);
    }

    /**
     * This constructor is used to add new players
     * The number of armies are automatically assigned at 5
     * @param p_playerName name of player
     * @param p_map current map
     */
    public Player(int p_id, String p_playerName, RiskMap p_map) {
        d_playerId = p_id;
        this.d_availableReinforcements = 0;
        this.d_playerName = p_playerName;
        d_listOfCountriesOwned = new ArrayList<>();
        d_ownedCards = new ArrayList<>();
        d_negotiatedPlayers = new ArrayList<>();
        d_map = p_map;
        d_playerStrategy = new HumanStrategy();
    }

    public Player(int p_id, String p_playerName, RiskMap p_map, Strategies p_playerStrategy) {
        d_playerId = p_id;
        this.d_availableReinforcements = 0;
        this.d_playerName = p_playerName;
        d_listOfCountriesOwned = new ArrayList<>();
        d_ownedCards = new ArrayList<>();
        d_negotiatedPlayers = new ArrayList<>();
        d_map = p_map;
        setPlayerStrategy(p_playerStrategy);
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

    public void issueOrder(){
        issueOrder(d_playerStrategy.decide());
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
        if (d_map == null) {
            bonusForOwningContinent = 0;
            return;
        }

        var continents = d_map.getContinents();
        ArrayList<Country> l_listOfCountriesOwned = getCountriesOwned();
        for (Continent l_continent : continents) {
            ArrayList<Country> l_countriesInContinent = new ArrayList<>(l_continent.getCountries());
            //check if all countries of this

            while(!l_listOfCountriesOwned.isEmpty()){
                Country l_country = l_listOfCountriesOwned.get(0);

                //remove from continent country list, if match found.
                if(l_countriesInContinent.contains(l_country)){
                    l_countriesInContinent.remove(l_country);
                }
                l_listOfCountriesOwned.remove(l_country);
            }

            if (l_countriesInContinent.isEmpty()) {
                bonusForOwningContinent = l_continent.getControlValue();
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
     * Assign country to a player with the given amount of player
     * @param p_country The country what to assign
     * @param p_noOfArmies The number of Armies on the land
     */
    public void assignCountry(Country p_country, int p_noOfArmies) {
        d_listOfCountriesOwned.add(p_country.getDId());
        p_country.setArmy(p_noOfArmies);
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
        if(d_map == null)
            return new ArrayList<>();

        ArrayList<Country> l_countryList = new ArrayList<>();
        for(Integer l_id : d_listOfCountriesOwned){
            l_countryList.add(d_map.getCountryById(l_id));
        }
        return l_countryList;
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
    public int getPlayerId(){
        return d_playerId;
    }

    /**
     * Adds a random card to the card list of player
     */
    public void addRandomCard(){
        Random l_randGen = new Random();
        CardType[] l_cards = CardType.values();
        addCard(l_cards[l_randGen.nextInt(0,l_cards.length)]);
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
        String l_cardsStr = "\n\tAvailable Cards: ";
        Map<CardType, Integer> cardMap = new HashMap<>();
        for(CardType card : d_ownedCards){
            if(cardMap.containsKey(card)) {
                cardMap.put(card, cardMap.get(card) + 1);
            }
            else
                cardMap.put(card, 1);
        }

        for(CardType card : CardType.values()){
            l_cardsStr += "\n\t\t"+card + ": " + (cardMap.containsKey(card) ? cardMap.get(card) : 0);
        }

        String l_ordersStr = "\n[Issued Orders]";
        if(d_orders.isEmpty())
            l_ordersStr += "\n -- none --";
        else
            for(Order l_order : d_orders){
                l_ordersStr +="\n\t"+l_order;
            }

        l_ordersStr += "\n\n";
        return l_nameStr + l_reinforcementsStr + l_cardsStr + l_ordersStr;
    }

    /**
     * @return true if player has orders.
     */
    public boolean hasOrders(){
        return !d_orders.isEmpty();
    }

    /**
     * clear the negotiation list for this place.
     */
    public void clearNegotiations(){
        d_negotiatedPlayers.clear();
    }

    /**
     * Remove a country from the player,only remove the ownership of the country, does not change the number of army
     * @param p_country The country need to be removed.
     *
     */
    public void removeCountry(Country p_country){
        Logger.log("Removing " + p_country.getDId() + " from " + getPlayerId());
        if(!d_listOfCountriesOwned.contains(p_country.getDId())){
            System.out.println("The player does not own the country");
            return;
        }
        Integer l_val = null;
        for(Integer l_int : d_listOfCountriesOwned){
            if(l_int == p_country.getDId()){
                l_val = l_int;
                break;
            }
        }
        if(l_val != null)
            d_listOfCountriesOwned.remove(l_val);
    }

    /**
     * Check if the country provided is owned by the current player
     * @param p_country The country that need to check if is owned by the player
     * @return True if it's owned by the player. False if is not
     */
    public boolean isCountryOwned(Country p_country){
        if(isNull(p_country)) return false;
        return d_listOfCountriesOwned.contains(p_country.getDId());
    }

    /**
     * This method checks whether the current player is negotiated with provided player
     * @param p_playerToCheck player that caller wants to check
     * @return true if player is negotiated, false if not
     */
    public boolean isPlayerNegotiated(Player p_playerToCheck){
        return d_negotiatedPlayers.contains(p_playerToCheck.getPlayerId());
    }


    /**
     * This method adds the player with given id to negotiated players list.
     * @param p_playerId id of player to be negotiated as an integer.
     */
    public void addNegotiatedPlayer(int p_playerId){
        d_negotiatedPlayers.add(p_playerId);
    }

    /**
     * Set the map reference for player.
     * @param p_mapReference map reference to set.
     */
    public void setMapReference(RiskMap p_mapReference){
        this.d_map = p_mapReference;
    }

    public List<OrderSaveData> getOrderSaveData(){
        List<OrderSaveData> l_saveData = new ArrayList<>();
        for(Order l_order : d_orders){
            l_saveData.add(OrderSaveConverter.createOrderSaveData(l_order));
        }
        return l_saveData;
    }

    /**
     * @return if player is human or not
     */

    public boolean isPlayerHuman(){
        return d_playerStrategy instanceof HumanStrategy;
    }

    /**
     * set the strategy context to be used in strategy classes
     * @param p_ctxEngine instance of the Iengine
     */
    public void setStrategyContext(IEngine p_ctxEngine){
        d_playerStrategy.setContext(new StrategyData(this, p_ctxEngine));
    }

    /**
     * This method sets the player strategy
     * @param p_playerStrategy enum to set the strategy value
     */
    public void setPlayerStrategy(Strategies p_playerStrategy){
        d_playerStrategy = switch (p_playerStrategy){
            case Cheater -> new CheaterStrategy();
            case Random -> new RandomStrategy();
            case Benevolent -> new BenevolentStrategy();
            case Aggressive -> new AggressiveStrategy();
            default -> new HumanStrategy();
        };
    }

    public Strategies getPlayerStrategy(){
        if(d_playerStrategy instanceof AggressiveStrategy){
            return Strategies.Aggressive;
        } else if(d_playerStrategy instanceof CheaterStrategy){
            return Strategies.Cheater;
        } else if(d_playerStrategy instanceof BenevolentStrategy){
            return Strategies.Benevolent;
        } else if(d_playerStrategy instanceof RandomStrategy){
            return Strategies.Random;
        }
        return Strategies.Human;
    }
}