package game.Actions;

import common.Command;
import entity.CardType;
import entity.Country;
import entity.Player;
import entity.RiskMap;

import java.util.ArrayList;

/**
 * Functionality related to blockade a country owned by the current player,
 * @author Weichen
 */
public class BlockadeAction extends GameAction {

    @Override
    public void execute(Command p_cmd) {
        Player l_player=d_context.getCurrentPlayer();
        RiskMap l_riskMap=d_context.getEngine().getMap();
        //not sure if cmd is handled correctly here
        ArrayList<String> l_arguments=p_cmd.getCmdAttributes().get(0).getArguments();
        if(l_arguments.size()!=1){
            System.out.print("Incorrect amount of arguments for blockade action.");
        }
        int l_countryID=Integer.parseInt(l_arguments.get(0));
        if(l_player.isCardAvailable(CardType.Blockade)){
            System.out.println("You don't have blockade card to blockade.");
            return;
        }
        //check if the player own the country, can be refactored if a such method is added to the player class
        boolean isOwned=false;
        for(Country l_country:l_player.getCountriesOwned()){
            if(l_country.getDId()==l_countryID){
                isOwned=true;
                break;
            }
        }
        if(!isOwned){
            System.out.println("The country is not owned by you.");
            return;
        }

        l_riskMap.increaseCountryArmyById(l_countryID,l_riskMap.getCountryArmyById(l_countryID)*2);


    }

    @Override
    public void postExecute() {

    }


}
