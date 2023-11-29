package game;

import entity.RiskMap;

/**
 * This interface is implemented by GameEngine and Tournament
 *
 * @author TaranjeetKaur
 */
public interface IEngine {

    /**
     * This mehtod returns the map on which game is played.
     * @return
     */
    public RiskMap getMap();

}
