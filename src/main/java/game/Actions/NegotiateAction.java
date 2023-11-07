package game.Actions;

import common.Command;
import entity.CardType;
import entity.Player;
import entity.PlayerHandler;
import java.util.UUID;

import static java.util.Objects.isNull;

/**
 * This class executes the "diplomacy card" action.
 *
 * @author TaranjeetKaur
 */
public class NegotiateAction extends GameAction {

    @Override
    public void execute(Command p_cmd) {
        //validate if current player can perform this action.
        Player l_currentPlayer = d_context.getCurrentPlayer();
        if(!l_currentPlayer.isCardAvailable(CardType.Diplomat)){
            System.out.println("You don't have Diplomacy card to play!, try any other card or command");
            d_execStatus = ActionExecStatus.Fail;
            return;
        }
        //validate correct number of arguments
        if(p_cmd.getCmdAttributes().isEmpty() || p_cmd.getCmdAttributes().get(0).getArguments().isEmpty()){
            System.out.println("Incorrect arguments for negotiate action!");
            d_execStatus = ActionExecStatus.Fail;
            return;
        }
        if(p_cmd.getCmdAttributes().get(0).getArguments().size()>1){
            System.out.println("Diplomacy card can be played on only one player!");
            d_execStatus = ActionExecStatus.Fail;
            return;
        }

        //check if player is valid and in the game to play card on.
        UUID l_oppPlayerId = UUID.fromString(p_cmd.getCmdAttributes().get(0).getArguments().get(0));
        Player l_oppPlayer = PlayerHandler.getPlayerById(l_oppPlayerId);
        if(isNull(l_oppPlayer)){
            System.out.println("Player with ID:" + l_oppPlayerId + " not active in the game, enter valid id!");
            d_execStatus = ActionExecStatus.Fail;
            return;
        }
        l_currentPlayer.addNegotiatedPlayer(l_oppPlayerId);
        l_oppPlayer.addNegotiatedPlayer(l_currentPlayer.getPlayerId());
        l_currentPlayer.removeCard(CardType.Diplomat);
        //TO-DO: clear the negotiate list after this turn.
        d_execStatus = ActionExecStatus.Success;
    }

    @Override
    public void postExecute() {
        //do nothing
    }
}
