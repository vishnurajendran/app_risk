package game.Actions;

import common.Command;
import entity.CardType;
import entity.Player;
import entity.PlayerHandler;
import game.GameCommands;
import game.Orders.NegotiateOrder;

import java.util.UUID;

import static java.util.Objects.isNull;

/**
 * This class validates the "diplomacy card" command and creates an order to be issued.
 *
 * @author TaranjeetKaur
 */
public class NegotiateAction extends GameAction {
    private Player d_currentPlayer;

    private final int NEGOTIATE_ORDER_CARD_NOT_FOUND = 0;
    private final int NEGOTIATE_ORDER_INVALID_ARGUMENTS = 1;
    private final int NEGOTIATE_ORDER_CARD_MORE_THAN_ONE_PLAYER = 2;
    private final int NEGOTIATE_ORDER_CARD_INVALID_PLAYER = 3;
    private final int NEGOTIATE_ORDER_CARD_ERROR = 4;

    public NegotiateAction(){

    }


    /**
     * This method creates a negotiate Order object to be issued if command is valid.
     *
     * @param p_cmd command to run the action with.
     */
    @Override
    public void execute(Command p_cmd) {

        //validate if current player can perform this action.
        try{
            d_currentPlayer = d_context.getCurrentPlayer();
           if(!d_currentPlayer.isCardAvailable(CardType.Diplomat)){
               System.out.println(GameCommands.NEGOTIATE_ERROR_MESSAGE.get(NEGOTIATE_ORDER_CARD_NOT_FOUND));
               d_execStatus = ActionExecStatus.Fail;
               return;
           }
        }catch (Exception e){
            System.out.println(GameCommands.NEGOTIATE_ERROR_MESSAGE.get(NEGOTIATE_ORDER_CARD_ERROR));
            d_execStatus = ActionExecStatus.Fail;
            return;
        }

        if(!checkCommandValidity(p_cmd)){
            d_execStatus = ActionExecStatus.Fail;
            return;
        }

        UUID l_oppPlayerId = null;
        //check if player is valid and in the game to play card on.
        try {
            l_oppPlayerId = UUID.fromString(p_cmd.getCmdAttributes().get(0).getArguments().get(0));
        }catch (IllegalArgumentException e){
            System.out.println(GameCommands.NEGOTIATE_ERROR_MESSAGE.get(NEGOTIATE_ORDER_CARD_INVALID_PLAYER));
            d_execStatus = ActionExecStatus.Fail;
            return;
        }
        Player l_oppPlayer = PlayerHandler.getPlayerById(l_oppPlayerId);
        if(isNull(l_oppPlayer)){
            System.out.println(GameCommands.NEGOTIATE_ERROR_MESSAGE.get(NEGOTIATE_ORDER_CARD_INVALID_PLAYER));
            d_execStatus = ActionExecStatus.Fail;
            return;
        }

        d_currentPlayer.issueOrder(new NegotiateOrder(d_currentPlayer, l_oppPlayer));
    }

    /**
     * This method checks the validity of negotiate command
     * @param p_cmd command object passed down by application
     * @return  true, if command is valid, false otherwise.
     */
    private boolean checkCommandValidity(Command p_cmd){
        //validate correct number of arguments
        if(p_cmd.getCmdAttributes().isEmpty() || p_cmd.getCmdAttributes().get(0).getArguments().isEmpty()){
            System.out.println(GameCommands.NEGOTIATE_ERROR_MESSAGE.get(NEGOTIATE_ORDER_INVALID_ARGUMENTS));
            return false;
        }
        if(p_cmd.getCmdAttributes().get(0).getArguments().size()>1){
            System.out.println(GameCommands.NEGOTIATE_ERROR_MESSAGE.get(NEGOTIATE_ORDER_CARD_MORE_THAN_ONE_PLAYER));
            return false;
        }
        return true;
    }

    /**
     * cleanup if any.
     */
    @Override
    public void postExecute() {
        //do nothing
    }
}
