package game.Orders;

/**
 * this enum holds all the available order types,
 * mainly used for order serialisation and de-serialisation.
 * @author vishnurajendran
 */
public enum OrderType {
    Advance,
    Airlift,
    Blockade,
    Bomb,
    Deploy,
    Negotiate,
    Skip
}
