package entity;

/**
 * this enum defines the type of cards available
 * for use. each card provides a specific function
 * to the player.
 * @author vishnurajendran
 */
public enum CardType {
    //Bomb a territory
    Bomb,
    //Create a blockade
    Blockade,
    //Airlift army from a territory to another
    Airlift,
    //Negotiate with another player
    Diplomat
}
