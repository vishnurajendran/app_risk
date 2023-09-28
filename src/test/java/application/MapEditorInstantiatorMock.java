package application;

import common.ISubAppInstantiator;
import common.ISubApplication;

/**
 * a mock implementation for map editor instantiator for testing application class.
 * @author vishnurajendran
 */
public class MapEditorInstantiatorMock implements ISubAppInstantiator {

    /**
     * This method creates a new instance of MapEditorMock class.
     * @return instance of MapEditorMock
     */
    @Override
    public ISubApplication createInstance() {
        return new MapEditorMock();
    }

}
