package Tournament;

import common.ISubAppInstantiator;
import common.ISubApplication;

/**
 * This class implements the ISubAppInstantiator
 * and serves as the instantiator for the tournament
 *
 * @author TaranjeetKaur
 */
public class TournamentInstantiator implements ISubAppInstantiator {

    /**
     * @return instance of tournament
     */
    @Override
    public ISubApplication createInstance() {
        return new Tournament();
    }
}

