package mapEditer;

import common.Command;

public class PostEditPhase extends Phase{
        public PostEditPhase(MapEditor p_mapEditor) {
            super();
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
        System.out.println("Operation Successful");
        return true;
    }
}
