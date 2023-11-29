package application;

/**
 * This enum provides the states the application can
 * be in at any given point.
 *
 * @author vishnurajendran
 */
public enum AppState {
    //This the base state,
    //no sub-application should be active during this state
    Standard,
    //Only game sub-application is active during this state
    Game,
    //Only map-editor sub-application is active during this state
    MapEditor,
    Tournament
}
