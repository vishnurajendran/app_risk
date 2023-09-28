package game;

import common.Command;
import common.ISubAppInstantiator;
import common.ISubApplication;

/**
 *  * This class implements the ISubAppInstantiator
 *  * and serves as the instantiator for the game
 * @author vishnurajendran
 * Dated 26-09-2023
 */
public class GameInstantiator implements ISubAppInstantiator {

    /**
     * @return instance of a game
     */
    @Override
    public ISubApplication createInstance() {

        ISubApplication gameInstance = new ISubApplication() {
            @Override
            public void initialise() {

            }

            @Override
            public boolean canProcess(String cmdName) {
                return false;
            }

            @Override
            public void submitCommand(Command p_command) {

            }

            @Override
            public void shutdown() {

            }
        };
        return gameInstance;
    }

}
