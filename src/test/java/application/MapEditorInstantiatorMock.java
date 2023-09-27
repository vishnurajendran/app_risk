package application;

import common.ISubAppInstantiator;
import common.ISubApplication;

/**
 * a mock implementation for map editor instantiator for testing application class.
 * @author vishnurajendran
 */
public class MapEditorInstantiatorMock implements ISubAppInstantiator {

    @Override
    public ISubApplication createInstance() {
        return new MapEditorMock();
    }

}
