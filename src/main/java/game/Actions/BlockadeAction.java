package game.Actions;

import common.Command;
import entity.CardType;
import entity.Country;
import entity.Player;
import entity.RiskMap;

import java.util.ArrayList;

import static java.util.Objects.isNull;

/**
 * Functionality related to blockade a country owned by the current player,
 * @author Weichen
 */
public class BlockadeAction extends GameAction {

    @Override
    public void execute(Command p_cmd) {
        d_execStatus = ActionExecStatus.Success;
        Player l_player=d_context.getCurrentPlayer();
        RiskMap l_riskMap=d_context.getEngine().getMap();
        //not 100% sure if cmd is handled correctly here
        ArrayList<String> l_arguments=p_cmd.getCmdAttributes().get(0).getArguments();
        if(l_arguments.size()!=1){
            System.out.print("Incorrect amount of arguments for blockade action.");
            d_execStatus=ActionExecStatus.Fail;
            return;
        }
        int l_countryID=Integer.parseInt(l_arguments.get(0));
        if(l_player.isCardAvailable(CardType.Blockade)){
            System.out.println("You don't have blockade card to blockade.");
            d_execStatus=ActionExecStatus.Fail;
            return;
        }
        Country l_country=l_riskMap.getCountryById(l_countryID);
        if(isNull(l_country)){
            System.out.println("Country ID is invalid.");
            d_execStatus=ActionExecStatus.Fail;
            return;
        }

        if(l_player.isCountryOwned(l_country)){
            System.out.println("The country is not owned by you.");
            d_execStatus=ActionExecStatus.Fail;
            return;
        }

        l_riskMap.increaseCountryArmyById(l_countryID,l_riskMap.getCountryArmyById(l_countryID)*2);
        l_player.removeCountry(l_country);
        l_player.removeCard(CardType.Blockade);


    }

    @Override
    public void postExecute() {

    }


}
