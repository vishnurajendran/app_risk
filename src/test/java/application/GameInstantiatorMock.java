package application;

import common.ISubAppInstantiator;
import common.ISubApplication;

/**
 * a mock implementation for game instantiator for testing application class.
 * @author vishnurajendran
 */
public class GameInstantiatorMock implements ISubAppInstantiator {

    @Override
    public ISubApplication createInstance() {
        return new GameMock();
    }
}
