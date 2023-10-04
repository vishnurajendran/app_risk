package application;

import common.ISubAppInstantiator;
import common.ISubApplication;

/**
 * a mock implementation for game instantiator for testing application class.
 *
 * @author vishnurajendran
 */
public class GameInstantiatorMock implements ISubAppInstantiator {

    /**
     * This method creates a new instance of GameMock class.
     * <b>FOR TESTING ONLY</b>
     *
     * @return instance of GameMock
     */
    @Override
    public ISubApplication createInstance() {
        return new GameMock();
    }
}
