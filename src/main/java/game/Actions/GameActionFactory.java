package game.Actions;

/**
 * @author vishnurajendran
 */
public class GameActionFactory {

    /**
     * private constructor to block instancing.
     */
    private GameActionFactory(){

    }

    /**
     * @return new instance of AddRemovePlayerAction
     */
    public static GameAction getUpdatePlayerAction(){
        return new AddRemovePlayerAction();
    }

    /**
     * @return new instance of AssignCountriesAction
     */
    public static GameAction getAssignCountriesAction(){
        return new AssignCountriesAction();
    }

    /**
     * @return new instance of AirliftAciton
     */
    public static GameAction getAirliftAction() {
        return new AirliftAction();
    }

    /**
     * @return new instance of AdvanceAction
     */
    public static GameAction getAdvanceAction() {
        return new AdvanceAction();
    }

    /**
     * @return new instance of BombAction
     */
    public static GameAction getBombAction() {
        return new BombAction();
    }

    /**
     * @return new instance of BlockadeAction
     */
    public static GameAction getBlockadeAction() {
        return new BlockadeAction();
    }

    /**
     * @return new instance of CommitAction
     */
    public static GameAction getCommitAction() {
        return new CommitAction();
    }

    /**
     * @return new instance of DeployAction
     */
    public static GameAction getDeployAction() {
        return new DeployAction();
    }

    /**
     * @return new instance of NegotiateAction
     */
    public static GameAction getNegotiateAction() {
        return new NegotiateAction();
    }
}