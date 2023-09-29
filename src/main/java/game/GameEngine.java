package game;
import common.Command;
import common.IMethod;
import common.ISubApplication;
import common.Logger;

import java.util.HashMap;


/**
 * This is the game class
 * Handles registering of commands and executing methods based on the commands.
 * @author Soham
 */
public class GameEngine implements ISubApplication {
    private final HashMap<String, IMethod> l_cmdtoGameAction;

    public GameEngine() {
        l_cmdtoGameAction = null;
    }


    private void registerGameCommands(){
        Logger.log("Registering game commands");
        l_cmdtoGameAction.put(GameCommands.LOAD_MAP, this::loadGameMap);
    }

    private void loadGameMap(Command p_cmd){

    }

    @Override
    public void initialise() {
        registerGameCommands();
    }

    @Override
    public boolean canProcess(String cmdName) {
        System.out.println(cmdName);
        return true;
    }

    @Override
    public void submitCommand(Command p_command) {

    }

    @Override
    public void shutdown() {

    }
}
