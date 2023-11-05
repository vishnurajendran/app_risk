package game.Actions;

/**
 * @author vishnurajendran
 */
public class GameActionFactory {

    private GameActionFactory(){

    }

    public static GameAction getUpdatePlayerAction(){
        return new AddRemovePlayerAction();
    }

    public static GameAction getAssignCountriesAction(){
        return new AssignCountriesAction();
    }

    public static GameAction getDeployAction() {
        return new DeployAction();
    }
}
