package game;
import application.ApplicationConstants;
import common.*;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * This is the game class
 * Handles registering of commands and executing methods based on the commands.
 * @author Soham
 */
public class GameEngine implements ISubApplication {
    private final HashMap<String, IMethod> l_cmdtoGameAction;

    public GameEngine() {
        l_cmdtoGameAction = new HashMap<>();
    }


    private void registerGameCommands(){
        Logger.log("Registering game commands");
        l_cmdtoGameAction.put(GameCommands.CMD_LOAD_MAP, this::loadGameMap);
        l_cmdtoGameAction.put(GameCommands.CMD_ASSIGN_COUNTRIES_TO_PLAYER, this::assignCountries);
    }


    private void loadGameMap(Command p_cmd){
        ArrayList<CommandAttribute> l_getCommandAttributes = p_cmd.getCmdAttributes();
        System.out.println(l_getCommandAttributes.get(0).getArguments().toString());
    }

    private void updatePlayers(Command p_cmd){

    }

    private void assignCountries(Command p_cmd){

    }

    @Override
    public void initialise() {
        registerGameCommands();
    }

    // returns true/false based on if the command is present in GameCommands.java
    @Override
    public boolean canProcess(String cmdName) {
        return GameCommands.CHECKVALIDCOMMANDS.contains(cmdName);
    }

    @Override
    public void submitCommand(Command p_command) {
        Logger.log(p_command.getCmdName());
        if(l_cmdtoGameAction.containsKey(p_command.getCmdName())){
            l_cmdtoGameAction.get(p_command.getCmdName()).invoke(p_command);
        }
    }

    @Override
    public void shutdown() {

    }
}
