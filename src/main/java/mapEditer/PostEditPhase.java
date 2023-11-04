package mapEditer;

import common.Command;
import common.Logging.Logger;

public class PostEditPhase extends Phase{
        public PostEditPhase(MapEditor p_mapEditor) {
            super(p_mapEditor);
        }

    @Override
    boolean isValidCommand(Command p_command) {
        InvalidCommandMessage();
        return false;
    }

    @Override
    boolean executeCommand(Command p_command) {
        InvalidCommandMessage();
        return false;
    }

    @Override
    boolean postExecute() {
        Logger.log("MapEditor Operation Successful");
        return true;
    }
}
