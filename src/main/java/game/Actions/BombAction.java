package game.Actions;

import common.Command;
import entity.CardType;
import entity.Country;
import entity.Player;
import entity.RiskMap;
import game.Orders.BombOrder;

import java.util.ArrayList;

import static java.util.Objects.isNull;

/**
 * Functionality related to bomb a country owned by the current player,
 * @author Rachit
 */
public class BombAction extends GameAction {

    /**
     * Do checks before adding order and if all checks pass, add this order to player.
     * @param p_cmd command to run the action with.
     */
    @Override
    public void execute(Command p_cmd) {
        d_execStatus = ActionExecStatus.Success;
        Player l_player=d_context.getCurrentPlayer();
        RiskMap l_riskMap=d_context.getEngine().getMap();

        ArrayList<String> l_arguments=p_cmd.getCmdAttributes().get(0).getArguments();
        if(l_arguments.size()!=1){
            System.out.print("Incorrect amount of arguments for blockade action.");
            d_execStatus=ActionExecStatus.Fail;
            return;
        }
        if (l_player.isCardAvailable(CardType.Bomb){
            if (l_country.getD_NumberOfArmies() > 0) {
                ArrayList<String> l_countriesOwnedList = new ArrayList<>();
                ArrayList<String> l_adjacentList = new ArrayList<>();
                for (Country l_country : l_player.getCountriesOwned()) {
                    l_countriesOwnedList.add(l_country.getName());
                }

                for (Country l_country : l_player.getCountriesOwned()) {
                    for (Country l_adjCountry : l_country.getBorders().values()) {
                        l_adjacentList.add(l_adjCountry.getName());
                    }
                }
                if (!l_adjacentList.contains(get)) {
                    System.out.println(d_countryName + " not adjacent with " + d_player.getD_PlayerName() + "countries");
                    return false;
                }

                if (l_countriesOwnedList.contains(d_countryName)) {
                    System.out.println(d_player.getD_PlayerName() + " can not attack bomb on your own country");
                    return false;
                }
                return true;
            } else {
                System.out.println(d_countryName + " has a 0 army so you can not apply bomb order there");
                return false;
            }


        } else {
            System.out.println("Player does not contain bomb card");

            return false;
        }
    }