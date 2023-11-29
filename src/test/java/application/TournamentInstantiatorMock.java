package application;

import common.ISubAppInstantiator;
import common.ISubApplication;

/**
 * a mock implementation for tournament instantiator for testing application class.
 *
 * @author TaranjeetKaur
 */
public class TournamentInstantiatorMock implements ISubAppInstantiator {

    /**
     * This method creates a new instance of TournamentMock class.
     *
     * @return instance of TournamentMock
     */
    @Override
    public ISubApplication createInstance() {
        return new TournamentMock();
    }

}
