package game.Actions;

import common.Command;
import common.Logging.Logger;
import entity.Player;
import entity.PlayerHandler;

/**
 * @author Soham
 */
public class DeployAction extends GameAction {

    @Override
    public void execute(Command p_command){
        int canIssueOrder = PlayerHandler.issueOrder(p_command);
        Logger.log(String.valueOf(canIssueOrder));
        if(canIssueOrder == PlayerHandler.ISSUEORDER_SUCCESS){
            int l_availableReinforcements;
            Player l_currentPlayer = PlayerHandler.getCurrentPlayer();
            l_availableReinforcements = l_currentPlayer.getAvailableReinforcements();
            if(l_availableReinforcements!=0){
                System.out.println(l_currentPlayer.getPlayerName()
                            + "'s turn, Reinforcements left: " + l_availableReinforcements);
                    PlayerHandler.displayGamePlayersCountries(l_currentPlayer);
            }
        }
    }

//    public void execute(Command p_command) {
//        int canIssueOrder = PlayerHandler.issueOrder(p_command);
//        Logger.log(String.valueOf(canIssueOrder));
//        if (canIssueOrder == PlayerHandler.ISSUEORDER_SUCCESS) {
//            int l_availableReinforcements;
//            // runs a loop through all the players to check if they have armies left
//            for (int i = 0; i < PlayerHandler.getGamePlayers().size(); i++) {
//                Player l_currentPlayer = PlayerHandler.getCurrentPlayer();
//                l_availableReinforcements = l_currentPlayer.getAvailableReinforcements();
//                // once it finds a player with armies>0, it stops and lets the player deploy
//                if (l_availableReinforcements != 0) {
//                    System.out.println(l_currentPlayer.getPlayerName()
//                            + "'s turn, Reinforcements left: " + l_availableReinforcements);
//                    PlayerHandler.displayGamePlayersCountries(l_currentPlayer);
//                    return;
//                } else {
//                    PlayerHandler.increasePlayerTurn(1);
//                }
//            }
//            // When everyone has depleted their armies, it executes all the orders
//            System.out.println("Everyone deployed their reinforcements");
//            //executeOrders();
//            PlayerHandler.reassignValuesForNextTurn();
//        } else {
//            System.out.println(GameCommands.DEPLOYERRORMESSAGE.get(canIssueOrder - 1));
//        }
//    }



    @Override
    public void postExecute() {
        // nothing here
    }
}
