package game.Actions;

import common.Command;
import common.Logging.Logger;
import entity.Player;
import entity.PlayerHandler;
import game.GameCommands;
import game.Orders.DeployOrder;

/**
 * This action handles deploy command exectution
 * @author Soham
 */
public class DeployAction extends GameAction {

    /**
     * int code for issue order invalid command
     */
    public final int ISSUEODER_INVALID_CMD = 1;
    /**
     * int code for issue order deploy to invalid country
     */
    public final int ISSUEORDER_PLAYER_DOESNT_OWN_COUNTRY = 2;
    /**
     * int code for issue order deploy more than available error
     */
    public final int ISSUEORDER_TRYING_DEPLOY_MORE_THAN_AVAIALBLE = 3;
    /**
     * int code for issue order success
     */
    public static final int ISSUEORDER_SUCCESS = 4;

    @Override
    public void execute(Command p_command){
        int l_issueOrderResult = issueOrder(p_command);
        if(l_issueOrderResult != ISSUEORDER_SUCCESS){
            d_execStatus = ActionExecStatus.Fail;
            System.out.println(GameCommands.DEPLOYERRORMESSAGE.get(l_issueOrderResult-1));
        }
    }

    /**
     * This function returns integer based on the ability to process the deploy order
     *
     * @param p_cmd includes the countryID and the numberOfReinforcements
     * @return 1 means the command given isn't valid (ISSUEODER_INVALID_CMD),
     * 2 means the player doesn't possess the country (ISSUEORDER_PLAYER_DOESNT_OWN_COUNTRY),
     * 3 means the player deployed more armies than they had (ISSUEORDER_TRYING_DEPLOY_MORE_THAN_AVAIALBLE).
     * 4 means the command is valid and can proceed to deploy order (ISSUEORDER_SUCCESS)
     */
    private int issueOrder(Command p_cmd) {
        Player l_currentPlayer = d_context.getCurrentPlayer();
        if (!p_cmd.getCmdAttributes().isEmpty()) {
            int l_countryId;

            int l_deployReinforcements;
            try {
                l_countryId = Integer.parseInt(p_cmd.getCmdAttributes().get(0).getArguments().get(0));
                l_deployReinforcements = Integer.parseInt(p_cmd.getCmdAttributes().get(0).getArguments().get(1));
            } catch (Exception e) {
                Logger.log("Number exception");
                return ISSUEODER_INVALID_CMD;
            }
            if (!l_currentPlayer.getCountriesOwned().stream().anyMatch((a) -> a.getDId() == l_countryId)) {
                Logger.log("The player doesn't have this country");
                return ISSUEORDER_PLAYER_DOESNT_OWN_COUNTRY;
            } else if (l_currentPlayer.getAvailableReinforcements() < l_deployReinforcements) {
                Logger.log("The armies requested to deploy are more than what the player has");
                return ISSUEORDER_TRYING_DEPLOY_MORE_THAN_AVAIALBLE;
            } else {

                l_currentPlayer.setAvailableReinforcements(l_currentPlayer.getAvailableReinforcements() - l_deployReinforcements);
                l_currentPlayer.issueOrder(new DeployOrder(l_currentPlayer, l_deployReinforcements, l_countryId));
                Logger.log("Orders for player " + l_currentPlayer + " = " + l_currentPlayer.getOrderSize());
            }
        } else {
            Logger.log(String.valueOf(p_cmd.getCmdAttributes().size() + " Isn't valid"));
            return ISSUEODER_INVALID_CMD;
        }
        return 4;
    }

    @Override
    public void postExecute() {
        // nothing here
    }
}
