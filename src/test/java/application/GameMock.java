package application;

import common.Command;
import common.ISubApplication;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A mock implementation for game for testing application class.
 * @author vishnurajendran
 */
public class GameMock implements ISubApplication {

    private ArrayList<String> supportedCmd = new ArrayList<>(Arrays.asList(ApplicationTestConstants.GAME_TEST_CMD));

    /**
     * NO functionality is here. Application Test does not require this
     */
    @Override
    public void initialise() {
        //nothing here
    }

    @Override
    public boolean hasQuit() {
        return false;
    }

    /**
     * @return true if p_cmdName is a valid command, else false.
     */
    @Override
    public boolean canProcess(String p_cmdName) {
        return supportedCmd.contains(p_cmdName);
    }

    /**
     * NO functionality is here. Application Test does not require this
     */
    @Override
    public void submitCommand(Command p_command) {
        //nothing here
    }

    /**
     * NO functionality is here. Application Test does not require this
     */
    @Override
    public void shutdown() {
        //nothing here
    }
}
