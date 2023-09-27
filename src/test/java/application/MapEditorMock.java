package application;

import common.Command;
import common.ISubApplication;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * a mock implementation for map-editor for testing application class.
 * @author vishnurajendran
 */
public class MapEditorMock implements ISubApplication {
    private ArrayList<String> d_supportedCmd = new ArrayList<>(Arrays.asList(ApplicationTestConstants.MAPEDITOR_TEST_CMD));
    @Override
    public void initialise() {
        //nothing here
    }

    @Override
    public boolean canProcess(String p_cmdName) {
        return d_supportedCmd.contains(p_cmdName);
    }

    @Override
    public void submitCommand(Command p_command) {
        //nothing here
    }

    @Override
    public void shutdown() {
        //nothing here
    }
}
