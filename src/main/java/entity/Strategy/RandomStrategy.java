package entity.Strategy;
import entity.CardType;
import game.Orders.*;
import entity.Country;
import entity.Player;
import entity.PlayerHandler;
import game.Data.StrategyData;

import java.util.*;

/**
 *  this class decides how a random strategy chooses an Order.
 *  @author Soham
 */
public class RandomStrategy extends Strategy{

    private Random d_rng;

    /**
     * sets the context for this strategy.
     * @param p_strategyData the context data.
     */
    @Override
    public void setContext(StrategyData p_strategyData) {
        super.setContext(p_strategyData);
        d_rng = new Random(UUID.randomUUID().hashCode());
    }

    /**
     * @return an order based on the strategy rules, if no good solution is found, returns null.
     */
    @Override
    public Order decide() {
        Player l_player = d_strategyData.getCurrentPlayer();

        // there is a 33.33% chance if a player has orders, we commit.
        if(l_player.hasOrders()){
            int l_commitProbability = d_rng.nextInt(1, 100);
            if(l_commitProbability % 3 == 0)
            {
                PlayerHandler.markComitted(l_player);
                return null;
            }
        }

        int l_probability = d_rng.nextInt(1, 100);
        // 20% chance.
        if(l_probability % 5 == 0){
            return randomAttack();
        }
        // 33.334% change.
        else if (l_probability % 3 == 0){
            return randomAdvance();
        }
        // 46.666% chance.
        else
            return randomDeploy();
    }

    /**
     * produces an Advance order that attempts to attack another player.
     * @return Order for attack, if no candidates are found, returns null.
     */
    private Order randomAttack(){
        Player l_myPlayer = d_strategyData.getCurrentPlayer();
        List<Player> l_playerCopy = new ArrayList<>(PlayerHandler.getGamePlayers());
        l_playerCopy.remove(l_myPlayer);
        Player l_targetPlayer = l_playerCopy.get(d_rng.nextInt(0, l_playerCopy.size()));

        // if we have a bomb card, just use that.
        if(l_myPlayer.isCardAvailable(CardType.Bomb)){
            l_myPlayer.removeCard(CardType.Bomb);
            List<Country> l_targetCountries = l_targetPlayer.getCountriesOwned();
            Country l_randTargetCountry = l_targetCountries
                    .get(d_rng.nextInt(0, l_targetCountries.size()));
            return new BombOrder(l_myPlayer,l_randTargetCountry.getDId(), d_strategyData.getEngine().getMap());
        }

        // try to advance units there.
        List<Player> visitedPlayer = new ArrayList<>();
        while(l_targetPlayer != null){
            visitedPlayer.add(l_targetPlayer);
            Country[] l_targetCountries = getCountryToAttack(l_targetPlayer);
            if(l_targetCountries[1] == null){
                l_targetPlayer = l_playerCopy.get(d_rng.nextInt(0, l_playerCopy.size()));
                l_playerCopy.remove(l_targetPlayer);
            }
            else{
                return new AdvanceOrder(
                        d_strategyData.getCurrentPlayer(),
                        l_targetCountries[0].getDId(),
                        l_targetCountries[1].getDId(),
                        d_rng.nextInt(1, l_targetCountries[0].getArmy()+1),
                        d_strategyData.getEngine().getMap());
            }
            if(visitedPlayer.size() >= l_playerCopy.size())
                break;
        }

        // fall-back to advance.
        return randomAdvance();
    }


    /**
     * get a country to attack that belongs to the target player.
     * if no good candidates are found we return a null set.
     * @param p_targetPlayer the plyer to target the attack towards.
     * @return an array in the format [source, target], can be null values
     */
    private Country[] getCountryToAttack(Player p_targetPlayer){
        Player l_myPlayer = d_strategyData.getCurrentPlayer();
        List<Country> l_ownedCountries = new ArrayList<>(l_myPlayer.getCountriesOwned());
        // randomize the list before we pick an attack target for this player.
        Collections.shuffle(l_ownedCountries);
        List<Country> visitedCountry = new ArrayList<>();
        for(Country l_country : l_ownedCountries){
            if(l_country.getArmy() <= 0)
                continue;

            visitedCountry.add(l_country);
            for(Country l_neighbour : l_country.getBorders().values()){
                if(visitedCountry.contains(l_neighbour))
                    continue;

                visitedCountry.add(l_neighbour);
                if(l_myPlayer.isCountryOwned(l_neighbour))
                    continue;

                if(!p_targetPlayer.isCountryOwned(l_neighbour))
                    continue;

                return new Country[]{l_country, l_neighbour};
            }
        }
        return new Country[]{null, null};
    }

    private Order randomAdvance(){
        Player l_myPlayer = d_strategyData.getCurrentPlayer();
        List<Country> l_ownedCountries = new ArrayList<>(l_myPlayer.getCountriesOwned());

        // this will only happen in the event of a cheater
        if(l_ownedCountries.size() <= 0){
            PlayerHandler.markComitted(l_myPlayer);
            return new EmptyOrder();
        }

        Country l_srcCountry = l_ownedCountries.get(d_rng.nextInt(0, l_ownedCountries.size()));
        if(l_srcCountry.getArmy() <= 0)
            return randomDeploy();
        else{
            l_ownedCountries.remove(l_srcCountry);
            List<Country> l_neighbours = new ArrayList<>(l_srcCountry.getBorders().values());
            Collections.shuffle(l_neighbours);
            for(Country l_neighbour : l_neighbours){
                if(l_myPlayer.isCountryOwned(l_neighbour))
                    return new AdvanceOrder(l_myPlayer, l_srcCountry.getDId(),
                            l_neighbour.getDId(), d_rng.nextInt(0, l_srcCountry.getArmy()+1),
                            d_strategyData.getEngine().getMap());
            }

            // fallback to deploy
            return randomDeploy();
        }
    }

    /**
     * generates a deploy-order, if no good candidates are found
     * we just commit.
     * @return Order for deploy, if no candidates are found, returns null.
     */
    private Order randomDeploy(){
        Player l_myPlayer = d_strategyData.getCurrentPlayer();
        if(l_myPlayer.getAvailableReinforcements() <= 0){
            PlayerHandler.markComitted(l_myPlayer);
            return null;
        }

        List<Country> l_ownedCountries = new ArrayList<>(l_myPlayer.getCountriesOwned());

        // this will only happen in the event of a cheater
        if(l_ownedCountries.size() <= 0){
            PlayerHandler.markComitted(l_myPlayer);
            return new EmptyOrder();
        }

        Country l_country = l_ownedCountries.get(d_rng.nextInt(0, l_ownedCountries.size()));
        return new DeployOrder(l_myPlayer, d_rng.nextInt(1,
                l_myPlayer.getAvailableReinforcements()+1),
                l_country.getDId(), d_strategyData.getEngine().getMap());
    }
}
